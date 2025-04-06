import {Candidate, CandidateConfig} from "../../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../../entities/Enterprise"
import Notification from "../../view/Notification"
import DatabaseManager from "../DatabaseManager"

interface DatabaseValidationDependencies {
    dbManager: DatabaseManager
    notification: Notification
}

export default class DatabaseValidation {

    private dbManager: DatabaseManager
    private notification: Notification

    constructor({
                    dbManager,
                    notification,
                }:DatabaseValidationDependencies) {
        this.dbManager = dbManager
        this.notification = notification
    }

    checkDuplicateCandidateEmail(candidates: Candidate[] | null, newCandidateData: CandidateConfig) {

        let candidatesWithSameEmail = candidates?.filter(candidate =>
            candidate.email == newCandidateData.email
        )

        const isDuplicated = candidatesWithSameEmail != undefined && candidatesWithSameEmail.length > 0

        if (isDuplicated) {
            this.notification.repeatedEmailResgistrationError()
        }

        return isDuplicated
    }

    checkDuplicateEnterpriseEmail(enterprises: Enterprise[] | null, newEnterpriseData: EnterpriseConfig) {

        let enterprisesWithSameEmail = enterprises?.filter(enterprise =>
            enterprise.email == newEnterpriseData.email
        )

        const isDuplicated = enterprisesWithSameEmail != undefined && enterprisesWithSameEmail.length > 0

        if (isDuplicated) {
            this.notification.repeatedEmailResgistrationError()
        }

        return isDuplicated
    }

    tryGetCandidate(input:CandidateConfig){
        let candidatesFiltered = this.dbManager.candidates?.filter(candidate =>
            candidate.email == input.email
        )

        if (candidatesFiltered == undefined || candidatesFiltered.length == 0) {
            this.notification.userNotFound()
            return undefined
        } else if (candidatesFiltered.length > 1) {
            this.notification.repeatedUser()
            return undefined
        } else if (candidatesFiltered[0].password != input.password){
            this.notification.wrongPassword()
            return undefined
        }
        return candidatesFiltered[0]
    }

    tryGetEnterprise(input:EnterpriseConfig){
        let enterprisesFiltered = this.dbManager.enterprises?.filter(enterprise =>
            enterprise.email == input.email
        )

        if (enterprisesFiltered == undefined || enterprisesFiltered.length == 0) {
            this.notification.userNotFound()
            return undefined
        } else if (enterprisesFiltered.length > 1) {
            this.notification.repeatedUser()
            return undefined
        } else if (enterprisesFiltered[0].password != input.password){
            this.notification.wrongPassword()
            return undefined
        }
        return enterprisesFiltered[0]
    }
}