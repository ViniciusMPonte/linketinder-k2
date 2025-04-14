import CoreServices from "../dependencies/CoreServices"
import Components from "../dependencies/Components"
import Chart from "../components/Chart"
import {Enterprise} from "../entities/Enterprise"
import {ProfileCandidate, ProfileEnterprise} from "../components/Profile"
import UIServices from "../dependencies/UIServices"
import {Candidate} from "../entities/Candidate"

interface ContentBuilderConfig {
    components: Components,
    coreServices: CoreServices
    uiServices: UIServices
}


export default class ContentBuilder {

    private readonly components
    private readonly coreServices
    private readonly uiServices

    constructor({components, coreServices, uiServices}: ContentBuilderConfig) {
        this.components = components
        this.coreServices = coreServices
        this.uiServices = uiServices
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
            window.location.href = "/candidate/register-candidate.html"
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

}