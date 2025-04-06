import EntityFactories from "../../dependencies/EntityFactories"
import CoreServices from "../../dependencies/CoreServices"
import ValidationServices from "../../dependencies/ValidationServices"
import UIServices from "../../dependencies/UIServices"

import {CandidateConfig} from "../../entities/Candidate"

interface CandidateFormsManagerConfig {
    entityFactories: EntityFactories,
    coreServices: CoreServices,
    validationServices: ValidationServices,
    uiServices: UIServices
}

export default class CandidateFormsManager {
    private readonly entityFactories
    private readonly coreServices
    private readonly validationServices
    private readonly uiServices

    constructor({
                    entityFactories,
                    coreServices,
                    validationServices,
                    uiServices,
                }: CandidateFormsManagerConfig) {
        this.entityFactories = entityFactories
        this.coreServices = coreServices
        this.validationServices = validationServices
        this.uiServices = uiServices
    }

    public activeCandidateCreateFormListener() {
        const createButton = this.uiServices.domQuery.getCreateCandidateButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleCandidateCreation.bind(this))
    }

    private handleCandidateCreation(event: Event) {
        event.preventDefault()

        try {
            const data = this.uiServices.domQuery.getInputForNewCandidate()

            if (!this.isValidCandidate(data)) {
                return
            }

            this.coreServices.dbManager.addCandidate(this.entityFactories.createCandidate(data))

            this.uiServices.notification.successRegistration()
            this.uiServices.redirect.loginCandidate()
        } catch (error) {
            console.log(error)
        }
    }

    private isValidCandidate(data: CandidateConfig): boolean {
        const isValid = this.validationServices.candidate.checkRegistrationData(data)
        if (!isValid) return false

        const isDuplicatedEmail = this.validationServices.database.checkDuplicateCandidateEmail(this.coreServices.dbManager.candidates, data)
        if (isDuplicatedEmail) return false

        return true
    }

    public activeCandidateLoginFormListener() {
        const loginButton = this.uiServices.domQuery.getLoginCandidateButton()
        if (!loginButton) return
        loginButton.addEventListener("click", this.handleLoginCandidate.bind(this))
    }

    private handleLoginCandidate(event: Event) {
        event.preventDefault()

        try {
            const input = this.uiServices.domQuery.getInputForLoginCandidate()
            if (!this.isValidLoginCandidate(input)) {
                this.uiServices.redirect.loginCandidate()
                return
            }
            this.loginCandidate(input)
            this.uiServices.redirect.candidateEmploymentsList()
        } catch (error) {
            console.log(error)
        }
    }

    private isValidLoginCandidate(input: CandidateConfig): boolean {
        const isValid = this.validationServices.candidate.checkLoginData(input)
        if (!isValid) return false

        const candidate = this.validationServices.database.tryGetCandidate(input)
        if (candidate === undefined) return false

        return true
    }

    private loginCandidate(input: CandidateConfig) {

        let candidate = this.validationServices.database.tryGetCandidate(input)
        if (!candidate) return

        this.coreServices.loginManager.logIn(candidate)
    }
}