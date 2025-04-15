import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import DatabaseManager from "../services/DatabaseManager"
import LoginManager from "../services/LoginManager"
import DOMQuery from "../view/DOMQuery"
import Notification from "../view/Notification"
import Redirect from "../view/Redirect"
import CandidateValidation from "../services/validation/CandidateValidation"
import EnterpriseValidation from "../services/validation/EnterpriseValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"
import EmploymentValidation from "../services/validation/EmploymentValidation"
import {Employment, EmploymentConfig} from "../entities/Employment"


class Section {
    readonly dbManager = new DatabaseManager()
    readonly loginManager = new LoginManager()
    readonly domQuery = new DOMQuery()
    readonly notification = new Notification()
    //readonly redirect = new Redirect(new LoginManager())
    readonly candidateValidation = new CandidateValidation()
    readonly enterpriseValidation = new EnterpriseValidation()
    readonly employmentValidation = new EmploymentValidation()
    readonly dbValidation = new DatabaseValidation({
        notification: this.notification,
        dbManager: this.dbManager
    })

    createCandidate = (data: CandidateConfig) => new Candidate(data)
    createEnterprise = (data: EnterpriseConfig) => new Enterprise(data)
    createEmployment = (data: EmploymentConfig) => new Employment(data)
}

const section = new Section()
export default section