import EntityFactories from "../../dependencies/EntityFactories"
import CoreServices from "../../dependencies/CoreServices"
import ValidationServices from "../../dependencies/ValidationServices"
import UIServices from "../../dependencies/UIServices"

import {EmploymentConfig} from "../../entities/Employment"
import {Enterprise} from "../../entities/Enterprise"

interface EmploymentFormsManagerConfig {
    entityFactories: EntityFactories,
    coreServices: CoreServices,
    validationServices: ValidationServices,
    uiServices: UIServices
}

export default class EmploymentFormsManager {
    private readonly entityFactories
    private readonly coreServices
    private readonly validationServices
    private readonly uiServices

    constructor({
                    entityFactories,
                    coreServices,
                    validationServices,
                    uiServices,
                }: EmploymentFormsManagerConfig) {
        this.entityFactories = entityFactories
        this.coreServices = coreServices
        this.validationServices = validationServices
        this.uiServices = uiServices
    }

    public activeEmploymentCreateFormListener() {
        if (!this.coreServices.loginManager.isEnterprise) {
            this.uiServices.notification.unauthorizedAccess()
            this.uiServices.redirect.loginEnterprise()
            return
        }

        const createButton = this.uiServices.domQuery.getCreateEmploymentButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleCreateEmployment.bind(this))
    }

    private handleCreateEmployment(event: Event) {
        event.preventDefault()

        try {
            let input = this.uiServices.domQuery.getInputForCreateEmployment()

            if (!this.isValidEntries(input)) return

            input.enterpriseId = (this.coreServices.loginManager.loggedIn as Enterprise).id

            this.coreServices.dbManager.addEmployment(this.entityFactories.createEmployment(input))

            this.uiServices.notification.successRegistration()
            this.uiServices.redirect.enterpriseCandidatesList()
        } catch (error) {
            console.log(error)
        }
    }

    private isValidEntries(data: EmploymentConfig): boolean {
        const isValid = this.validationServices.employment.check(data)
        if (!isValid) return false

        return true
    }
}