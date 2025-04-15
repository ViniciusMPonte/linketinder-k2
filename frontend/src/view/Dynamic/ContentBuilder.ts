import UIServices from "../../dependencies/UIServices"
import EntityFactories from "../../dependencies/EntityFactories"
import ValidationServices from "../../dependencies/ValidationServices"
import CoreServices from "../../dependencies/CoreServices"
import Components from "../../dependencies/Components"
import {Candidate} from "../../entities/Candidate"
import {Enterprise} from "../../entities/Enterprise"
import CandidateFormsManager from "../forms/CandidateFormsManager"
import EnterpriseFormsManager from "../forms/EnterpriseFormsManager"
import EmploymentFormsManager from "../forms/EmploymentFormsManager"
import {ProfileCandidate, ProfileEnterprise} from "../../components/Profile"
import Chart from "../../components/Chart"

interface ContentBuilderConfig {
    components: Components
    coreServices: CoreServices
    uiServices: UIServices
    entityFactories: EntityFactories
    validationServices: ValidationServices
}

interface builderConfig {
    action?: () => void;
}

export default class ContentBuilder {

    private readonly components
    private readonly coreServices
    private readonly uiServices

    candidateFormsManager
    enterpriseFormsManager
    employmentFormsManager

    constructor({entityFactories, coreServices, validationServices, uiServices, components}: ContentBuilderConfig) {
        this.components = components
        this.coreServices = coreServices
        this.uiServices = uiServices

        const dependencies = {entityFactories, coreServices, validationServices, uiServices, components}
        this.candidateFormsManager = new CandidateFormsManager(dependencies)
        this.enterpriseFormsManager = new EnterpriseFormsManager(dependencies)
        this.employmentFormsManager = new EmploymentFormsManager(dependencies)
    }

    //Enterprise
    enterpriseCandidatesList() {
        this.createChart()
        this.cardsCandidatesForEnterprise()
    }

    createChart(){
        const chartTag = this.uiServices.domQuery.getChart()
        const candidates = this.coreServices.dbManager.candidates

        if (!chartTag) return
        if (!candidates) return

        const skillCounts = Chart.countCandidateSkills(candidates)
        Chart.build(chartTag, Object.keys(skillCounts), Object.values(skillCounts))
    }

    cardsCandidatesForEnterprise(){

        const candidates = this.coreServices.dbManager.candidates
        if (candidates == null) return

        candidates.forEach(candidate => {
            if (true) candidate.name = "<span class=\"blur\">Hidden Name<\span>"

            const card = this.components.card.candidatesForEnterprise(candidate.id)
            if (card == undefined) return

            this.innerHTMLInject(this.uiServices.domQuery.getCandidateslist(), card)
        })
    }

    enterpriseProfile() {
        if (!this.coreServices.loginManager.isEnterprise) {
            this.uiServices.redirect.loginEnterprise()
            return
        }

        let enterpriseLogged: Enterprise = this.coreServices.loginManager.loggedIn as Enterprise
        let profileEnterprise = new ProfileEnterprise(enterpriseLogged)
        this.innerHTMLInject(this.uiServices.domQuery.getEnterpriseProfile(), profileEnterprise.get())
    }

    enterpriseMyEmploymentsList() {

        if (!this.coreServices.loginManager.isEnterprise) {
            this.uiServices.redirect.loginEnterprise()
            return
        }

        if (this.coreServices.dbManager.employments == null) {
            this.innerHTMLInject(this.uiServices.domQuery.getEmploymentsList(), "<h1>Nenhuma vaga encontrada, crie uma vaga antes!</h1>")
            return
        }

        const enterpriseLogged: Enterprise = this.coreServices.loginManager.loggedIn as Enterprise
        let isEmptyEmployment = true

        this.coreServices.dbManager.employments.forEach(employment => {
            if (employment.enterpriseId == enterpriseLogged.id) {
                isEmptyEmployment = false
                const card = this.components.card.employmentForEnterprise(employment.id, false)
                if (card == undefined) return
                this.innerHTMLInject(this.uiServices.domQuery.getEmploymentsList(), card)
            }
        })

        if (isEmptyEmployment) {
            this.innerHTMLInject(this.uiServices.domQuery.getEmploymentsList(), "<h1>Nenhuma vaga encontrada, crie uma vaga antes!</h1>")
        }
    }

    //Candidate
    candidateEmploymentsList() {
        if (this.coreServices.dbManager.employments == null) return
        this.coreServices.dbManager.employments.forEach(employment => {
            if (true) employment.name = "<span class=\"blur\">Hidden Name<\span>"
            const card = this.components.card.employmentForCandidate(employment.id, false)
            if (card == undefined) return
            this.innerHTMLInject(this.uiServices.domQuery.getEmploymentsList(), card)
        })
    }

    candidateProfile() {
        if (!this.coreServices.loginManager.isCandidate) {
            window.location.href = "../../../public/candidate/register-candidate.html"
            return
        }

        let candidateLogged: Candidate = this.coreServices.loginManager.loggedIn as Candidate
        let profileCandidate = new ProfileCandidate(candidateLogged)
        this.innerHTMLInject(this.uiServices.domQuery.getCandidateProfile(), profileCandidate.get())
    }

    //utils
    innerHTMLInject(tag: HTMLElement | null, output: string): void {
        if (tag) {
            tag.innerHTML += output
        }
    }

    routes: Record<string, builderConfig> = {
        "/": {
        },
        "/candidate/register-candidate.html": {
            action: () => this.candidateFormsManager.activeCandidateCreateFormListener(),
        },
        "/enterprise/register-enterprise.html": {
            action: () => this.enterpriseFormsManager.activeEnterpriseCreateFormListener(),
        },
        "/candidate/login-candidate.html": {
            action: () => this.candidateFormsManager.activeCandidateLoginFormListener(),
        },
        "/enterprise/login-enterprise.html": {
            action: () => this.enterpriseFormsManager.activeEnterpriseLoginFormListener(),
        },
        "/candidate/profile.html": {
            action: () => this.candidateProfile(),
        },
        "/enterprise/profile.html": {
            action: () => this.enterpriseProfile(),
        },
        "/candidate/employments-list.html": {
            action: () => this.candidateEmploymentsList(),
        },
        "/enterprise/candidates-list.html": {
            action: () => this.enterpriseCandidatesList(),
        },
        "/enterprise/my-employments.html": {
            action: () => this.enterpriseMyEmploymentsList(),
        },
        "/enterprise/register-employment.html": {
            action: () => this.employmentFormsManager.activeEmploymentCreateFormListener(),
        },
    }


}