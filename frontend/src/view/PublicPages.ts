import {Candidate, CandidateConfig} from "../entities/Candidate"
import CandidateValidation from "../services/validation/CandidateValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"
import DatabaseManager from "../services/DatabaseManager"
import DOMQuery from "./DOMQuery"

interface PublicPagesDependencies {
    dbManager: DatabaseManager
    domQuery: DOMQuery
    candidateValidation: CandidateValidation
    dbValidation: DatabaseValidation
    createCandidate: (data: CandidateConfig) => Candidate
}

export default class PublicPages {
    private dbManager: DatabaseManager
    private domQuery: DOMQuery
    private dbValidation: DatabaseValidation
    private candidateValidation: CandidateValidation
    private readonly createCandidate: (data: CandidateConfig) => Candidate

    constructor({
                    dbManager,
                    domQuery,
                    candidateValidation,
                    dbValidation,
                    createCandidate
                }: PublicPagesDependencies) {
        this.dbManager = dbManager
        this.domQuery = domQuery
        this.dbValidation = dbValidation
        this.candidateValidation = candidateValidation
        this.createCandidate = createCandidate
    }

    activeCandidateCreateFormListener() {
        const createButton = this.domQuery.getCreateButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleCandidateCreation.bind(this))
    }

    private handleCandidateCreation(event: Event) {
        event.preventDefault()

        try {
            const candidateData = this.domQuery.getInputForNewCandidate()

            if (!this.isValidCandidate(candidateData)) {
                return
            }

            this.dbManager.addCandidate(this.createCandidate(candidateData))

            this.notifySuccessAndRedirect()
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

    private notifySuccessAndRedirect() {
        alert("Cadastro realizado com sucesso!")
        window.location.href = "/candidate/login-candidate.html"
    }
}