//EntityFactories
import {Candidate, CandidateConfig} from "./entities/Candidate"
import {Enterprise, EnterpriseConfig} from "./entities/Enterprise"
import {Employment, EmploymentConfig} from "./entities/Employment"

//CoreServices
import DatabaseManager from "./services/DatabaseManager"
import LoginManager from "./services/LoginManager"

//ValidationServices
import CandidateValidation from "./services/validation/CandidateValidation"
import EnterpriseValidation from "./services/validation/EnterpriseValidation"
import EmploymentValidation from "./services/validation/EmploymentValidation"
import DatabaseValidation from "./services/validation/DatabaseValidation"

//UIServices
import DOMQuery from "./view/DOMQuery"
import Notification from "./view/Notification"
import Redirect from "./view/Redirect"

//Components
import Card from "./components/Card"
import Chart from "./components/Chart"
import Nav from "./components/Nav"
import {ProfileEnterprise, ProfileCandidate} from "./components/Profile"

//Dependencies
import EntityFactories from "./dependencies/EntityFactories"
import CoreServices from "./dependencies/CoreServices"
import ValidationServices from "./dependencies/ValidationServices"
import UIServices from "./dependencies/UIServices"
import Components from "./dependencies/Components"

//------------------------------
const dbManager = new DatabaseManager()
const loginManager = new LoginManager()

const domQuery = new DOMQuery()
const notification = new Notification()
const redirect = new Redirect(loginManager)

const candidateValidation = new CandidateValidation()
const enterpriseValidation = new EnterpriseValidation()
const employmentValidation = new EmploymentValidation()
const databaseValidation = new DatabaseValidation({dbManager, notification})

const card = new Card(dbManager)



const entityFactories = new EntityFactories()
const coreServices = new CoreServices({
    dbManager: dbManager,
    loginManager: loginManager
})
const validationServices = new ValidationServices({
    candidate: candidateValidation,
    enterprise: enterpriseValidation,
    employment: employmentValidation,
    database: databaseValidation
})
const uiServices = new UIServices({
    domQuery: domQuery,
    notification: notification,
    redirect: redirect
})
const components = new Components(card)

//------------------------------
export {entityFactories, coreServices, validationServices, uiServices, components}

//------------------------------
import NavigationManager from "./services/NavigationManager"

const navigationManager = new NavigationManager()
navigationManager.router()