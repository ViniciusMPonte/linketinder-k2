import EntityFactories from "../../dependencies/EntityFactories"
import CoreServices from "../../dependencies/CoreServices"
import ValidationServices from "../../dependencies/ValidationServices"
import UIServices from "../../dependencies/UIServices"

import {EnterpriseConfig} from "../../entities/Enterprise"

interface EnterpriseFormsManagerConfig {
    entityFactories: EntityFactories,
    coreServices: CoreServices,
    validationServices: ValidationServices,
    uiServices: UIServices
}

export default class EnterpriseFormsManager {
    private readonly entityFactories
    private readonly coreServices
    private readonly validationServices
    private readonly uiServices

    constructor({
                    entityFactories,
                    coreServices,
                    validationServices,
                    uiServices,
                }: EnterpriseFormsManagerConfig) {
        this.entityFactories = entityFactories
        this.coreServices = coreServices
        this.validationServices = validationServices
        this.uiServices = uiServices
    }

    public activeEnterpriseCreateFormListener() {
        const createButton = this.uiServices.domQuery.getCreateEnterpriseButton()
        if (!createButton) return
        createButton.addEventListener("click", this.handleEnterpriseCreation.bind(this))
    }

    private handleEnterpriseCreation(event: Event) {
        event.preventDefault()

        try {
            const data = this.uiServices.domQuery.getInputForNewEnterprise()

            if (!this.isValidEnterprise(data)) {
                return
            }

            this.coreServices.dbManager.addEnterprise(this.entityFactories.createEnterprise(data))

            this.uiServices.notification.successRegistration()
            this.uiServices.redirect.loginEnterprise()
        } catch (error) {
            console.log(error)
        }
    }

    private isValidEnterprise(data: EnterpriseConfig): boolean {
        const isValid = this.validationServices.enterprise.checkRegistrationData(data)
        if (!isValid) return false

        const isDuplicatedEmail = this.validationServices.database.checkDuplicateEnterpriseEmail(this.coreServices.dbManager.enterprises, data)
        if (isDuplicatedEmail) return false

        return true
    }

    public activeEnterpriseLoginFormListener() {
        const loginButton = this.uiServices.domQuery.getLoginEnterpriseButton()
        if (!loginButton) return
        loginButton.addEventListener("click", this.handleLoginEnterprise.bind(this))
    }

    private handleLoginEnterprise(event: Event) {
        event.preventDefault()

        try {
            const input = this.uiServices.domQuery.getInputForLoginEnterprise()
            if (!this.isValidLoginEnterprise(input)) {
                this.uiServices.redirect.loginEnterprise()
                return
            }
            this.loginEnterprise(input)
            this.uiServices.redirect.enterpriseCandidatesList()
        } catch (error) {
            console.log(error)
        }
    }

    private isValidLoginEnterprise(input: EnterpriseConfig): boolean {
        const isValid = this.validationServices.enterprise.checkLoginData(input)
        if (!isValid) return false

        const enterprise = this.validationServices.database.tryGetEnterprise(input)
        if (enterprise === undefined) return false

        return true
    }

    private loginEnterprise(input: EnterpriseConfig) {

        let enterprise = this.validationServices.database.tryGetEnterprise(input)
        if (!enterprise) return

        this.coreServices.loginManager.logIn(enterprise)
    }
}