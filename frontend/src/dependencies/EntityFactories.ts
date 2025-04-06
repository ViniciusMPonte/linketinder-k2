import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import {Employment, EmploymentConfig} from "../entities/Employment"

export default class EntityFactories {

    createCandidate(data: CandidateConfig): Candidate {
        return new Candidate(data)
    }

    createEnterprise(data: EnterpriseConfig): Enterprise {
        return new Enterprise(data)
    }

    createEmployment(data: EmploymentConfig): Employment {
        return new Employment(data)
    }
}