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
import ContentBuilder from "./view/Dynamic/ContentBuilder"

const dbManager = new DatabaseManager()
const loginManager = new LoginManager()

const domQuery = new DOMQuery()
const notification = new Notification()
const redirect = new Redirect({loginManager})

const candidateValidation = new CandidateValidation()
const enterpriseValidation = new EnterpriseValidation()
const employmentValidation = new EmploymentValidation()
const databaseValidation = new DatabaseValidation({dbManager, notification})

const card = new Card(dbManager)
const chart = new Chart()
const nav = new Nav()
const profileEnterprise = new ProfileEnterprise()
const profileCandidate = new ProfileCandidate()

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
const components = new Components({
    card: card,
    chart: chart,
    nav: nav,
    profileEnterprise: profileEnterprise,
    profileCandidate: profileCandidate
})
const contentBuilder = new ContentBuilder({entityFactories, coreServices, validationServices, uiServices, components})

export {entityFactories, coreServices, validationServices, uiServices, components, contentBuilder}

import NavigationManager from "./services/NavigationManager"
const navigationManager = new NavigationManager({uiServices, contentBuilder})
navigationManager.router()