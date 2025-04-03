import {Candidate, CandidateConfig} from "../../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../../entities/Enterprise"

export default class DatabaseValidation {

    checkDuplicateCandidateEmail(candidates: Candidate[] | null, newCandidateData: CandidateConfig) {

        let candidatesWithSameEmail = candidates?.filter(candidate =>
            candidate.email == newCandidateData.email
        )

        const isDuplicated = candidatesWithSameEmail != undefined && candidatesWithSameEmail.length > 0

        if (isDuplicated) {
            this.showValidationError("Já existe um usuário com mesmo e-mail.")
        }

        return isDuplicated
    }

    checkDuplicateEnterpriseEmail(enterprises: Enterprise[] | null, newEnterpriseData: EnterpriseConfig) {

        let enterprisesWithSameEmail = enterprises?.filter(enterprise =>
            enterprise.email == newEnterpriseData.email
        )

        const isDuplicated = enterprisesWithSameEmail != undefined && enterprisesWithSameEmail.length > 0

        if (isDuplicated) {
            this.showValidationError("Já existe um usuário com mesmo e-mail.")
        }

        return isDuplicated
    }

    private showValidationError(message: string): void {
        alert(message)
    }
}