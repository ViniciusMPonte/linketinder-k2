import {Candidate, CandidateConfig} from "../../entities/Candidate";

export default class DatabaseValidation {

    checkDuplicateCandidateEmail(candidates: Candidate[] | null, newCandidateData:CandidateConfig){

        let candidatesWithSameEmail = candidates?.filter(candidate =>
            candidate.email == newCandidateData.email
        )

        const isDuplicated = candidatesWithSameEmail != undefined && candidatesWithSameEmail.length > 0

        if(isDuplicated){
            this.showValidationError('Já existe um usuário com mesmo e-mail.')
        }

        return isDuplicated;
    }

    private showValidationError(message: string): void {
        alert(message);
    }
}