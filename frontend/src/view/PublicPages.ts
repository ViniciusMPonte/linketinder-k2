import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import CandidateValidation from "../services/validation/CandidateValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"
import DatabaseManager from "../services/DatabaseManager"
import DOMQuery from "./DOMQuery"
import EnterpriseValidation from "../services/validation/EnterpriseValidation"
import Redirect from "./Redirect"
import LoginManager from "../services/LoginManager"
import Notification from "./Notification"

interface PublicPagesDependencies {
    dbManager: DatabaseManager
    loginManager: LoginManager
    domQuery: DOMQuery
    notification: Notification
    redirect: Redirect
    candidateValidation: CandidateValidation
    enterpriseValidation: EnterpriseValidation
    dbValidation: DatabaseValidation
    createCandidate: (data: CandidateConfig) => Candidate
    createEnterprise: (data: EnterpriseConfig) => Enterprise
}

export default class PublicPages {
    private dbManager: DatabaseManager
    private loginManager: LoginManager
    private domQuery: DOMQuery
    private notification: Notification
    private redirect: Redirect
    private dbValidation: DatabaseValidation
    private candidateValidation: CandidateValidation
    private enterpriseValidation: EnterpriseValidation
    private readonly createCandidate: (data: CandidateConfig) => Candidate
    private readonly createEnterprise: (data: EnterpriseConfig) => Enterprise

    constructor({
                    dbManager,
                    loginManager,
                    domQuery,
                    notification,
                    redirect,
                    candidateValidation,
                    enterpriseValidation,
                    dbValidation,
                    createCandidate,
                    createEnterprise
                }: PublicPagesDependencies) {
        this.dbManager = dbManager
        this.loginManager = loginManager
        this.domQuery = domQuery
        this.notification = notification
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

            this.notification.successRegistration()
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

            this.notification.successRegistration()
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

    activeCandidateLoginFormListener() {
        const loginButton = this.domQuery.getLoginCandidateButton()
        if (!loginButton) return
        loginButton.addEventListener("click", this.handleLoginCandidate.bind(this))
    }

    handleLoginCandidate(event: Event) {
        event.preventDefault()

        try {
            const input = this.domQuery.getInputForLoginCandidate()
            if (!this.isValidLoginCandidate(input)) return
            this.loginCandidate(input)
            this.redirect.candidateEmploymentsList()
        } catch (error) {
            console.log(error)
        }
    }

    isValidLoginCandidate(data: CandidateConfig): boolean {
        const isValid = this.candidateValidation.checkLoginData(data)
        if (!isValid) return false

        return true
    }

    loginCandidate(input: CandidateConfig) {

        let candidate = this.dbValidation.tryGetCandidate(input)
        if (!candidate) return

        this.loginManager.logIn(candidate)
    }

    activeEnterpriseLoginFormListener() {
        const loginButton = this.domQuery.getLoginEnterpriseButton()
        if (!loginButton) return
        loginButton.addEventListener("click", this.handleLoginEnterprise.bind(this))
    }

    handleLoginEnterprise(event: Event) {
        event.preventDefault()

        try {
            const input = this.domQuery.getInputForLoginEnterprise()
            if (!this.isValidLoginEnterprise(input)) return
            this.loginEnterprise(input)
            this.redirect.enterpriseCandidatesList()
        } catch (error) {
            console.log(error)
        }
    }

    isValidLoginEnterprise(data: EnterpriseConfig): boolean {
        const isValid = this.enterpriseValidation.checkLoginData(data)
        if (!isValid) return false

        return true
    }

    loginEnterprise(input: EnterpriseConfig) {

        let enterprise = this.dbValidation.tryGetEnterprise(input)
        if (!enterprise) return

        this.loginManager.logIn(enterprise)
    }
}