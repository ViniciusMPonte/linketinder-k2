import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import CandidateValidation from "../services/validation/CandidateValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"
import DatabaseManager from "../services/DatabaseManager"
import DOMQuery from "./DOMQuery"
import EnterpriseValidation from "../services/validation/EnterpriseValidation"
import Redirect from "./Redirect"

interface PublicPagesDependencies {
    dbManager: DatabaseManager
    domQuery: DOMQuery
    redirect: Redirect
    candidateValidation: CandidateValidation
    enterpriseValidation: EnterpriseValidation
    dbValidation: DatabaseValidation
    createCandidate: (data: CandidateConfig) => Candidate
    createEnterprise: (data: EnterpriseConfig) => Enterprise
}

export default class PublicPages {
    private dbManager: DatabaseManager
    private domQuery: DOMQuery
    private redirect: Redirect
    private dbValidation: DatabaseValidation
    private candidateValidation: CandidateValidation
    private enterpriseValidation: EnterpriseValidation
    private readonly createCandidate: (data: CandidateConfig) => Candidate
    private readonly createEnterprise: (data: EnterpriseConfig) => Enterprise

    constructor({
                    dbManager,
                    domQuery,
                    redirect,
                    candidateValidation,
                    enterpriseValidation,
                    dbValidation,
                    createCandidate,
                    createEnterprise
                }: PublicPagesDependencies) {
        this.dbManager = dbManager
        this.domQuery = domQuery
        this.redirect = redirect
        this.dbValidation = dbValidation
        this.candidateValidation = candidateValidation
        this.enterpriseValidation = enterpriseValidation
        this.createCandidate = createCandidate
        this.createEnterprise = createEnterprise
    }

    activeCandidateCreateFormListener() {
        const createButton = this.domQuery.getCreateCandidateButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleCandidateCreation.bind(this))
    }

    private handleCandidateCreation(event: Event) {
        event.preventDefault()

        try {
            const data = this.domQuery.getInputForNewCandidate()

            if (!this.isValidCandidate(data)) {
                return
            }

            this.dbManager.addCandidate(this.createCandidate(data))

            this.notifySuccess()
            this.redirect.loginCandidate()
        } catch (error) {
            console.log(error)
        }
    }

    isValidCandidate(data: CandidateConfig): boolean {
        const isValid = this.candidateValidation.checkRegistrationData(data)
        if (!isValid) return false

        const isDuplicatedEmail = this.dbValidation.checkDuplicateCandidateEmail(this.dbManager.candidates, data)
        if (isDuplicatedEmail) return false

        return true
    }

    private notifySuccess() {
        alert("Cadastro realizado com sucesso!")
    }

    activeEnterpriseCreateFormListener() {
        const createButton = this.domQuery.getCreateEnterpriseButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleEnterpriseCreation.bind(this))
    }

    private handleEnterpriseCreation(event: Event) {
        event.preventDefault()

        try {
            const data = this.domQuery.getInputForNewEnterprise()

            if (!this.isValidEnterprise(data)) {
                return
            }

            this.dbManager.addEnterprise(this.createEnterprise(data))

            this.notifySuccess()
            this.redirect.loginEnterprise()
        } catch (error) {
            console.log(error)
        }
    }

    isValidEnterprise(data: EnterpriseConfig): boolean {
        const isValid = this.enterpriseValidation.checkRegistrationData(data)
        if (!isValid) return false

        const isDuplicatedEmail = this.dbValidation.checkDuplicateEnterpriseEmail(this.dbManager.enterprises, data)
        if (isDuplicatedEmail) return false

        return true
    }
}