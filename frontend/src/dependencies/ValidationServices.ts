import CandidateValidation from "../services/validation/CandidateValidation"
import EnterpriseValidation from "../services/validation/EnterpriseValidation"
import EmploymentValidation from "../services/validation/EmploymentValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"

interface ValidationServicesConfig {
    candidate: CandidateValidation
    enterprise: EnterpriseValidation
    employment: EmploymentValidation
    database: DatabaseValidation
}

export default class ValidationServices {

    public readonly candidate
    public readonly enterprise
    public readonly employment
    public readonly database

    constructor({
                candidate,
                enterprise,
                employment,
                database
                }: ValidationServicesConfig) {
        this.candidate = candidate
        this.enterprise = enterprise
        this.employment = employment
        this.database = database
    }
}




























