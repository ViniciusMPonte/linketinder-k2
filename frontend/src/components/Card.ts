import {Employment} from "../entities/Employment"
import {Enterprise} from "../entities/Enterprise"
import DatabaseManager from "../services/DatabaseManager"
import {Candidate} from "../entities/Candidate"

interface ParamsConfig {
    employment?: Employment
    enterprise?: Enterprise
    candidate?: Candidate
    forEntity: string
    viewBtn: boolean
    hideDetails: boolean
}

export default class Card {

    private readonly dbManager

    constructor(dbManager: DatabaseManager) {
        this.dbManager = dbManager
    }

    public employmentForCandidate(id: number, hideDetails: boolean = true) {
        let params: Object | undefined = this.getParamsEmployment(id)
        if (params == undefined) return

        const forEntity = "candidate"
        const viewBtn = true
        params = {...params, viewBtn, forEntity, hideDetails}

        return this.build({...params} as ParamsConfig)
    }


    public employmentForEnterprise(id: number, hideDetails: boolean = true) {
        let params: Object | undefined = this.getParamsEmployment(id)
        if (params == undefined) return

        const forEntity = "enterprise"
        const viewBtn = false
        params = {...params, viewBtn, forEntity, hideDetails}

        return this.build({...params} as ParamsConfig)
    }

    public candidatesForEnterprise(id: number, hideDetails: boolean = true) {
        let params: Object | undefined = this.getParamsCandidate(id)
        if (params == undefined) return

        const forEntity = "enterprise"
        const viewBtn = true
        params = {...params, viewBtn, forEntity, hideDetails}

        return this.build({...params} as ParamsConfig)
    }

    private getParamsEmployment(id: number) {
        const employment = this.dbManager.getEmployment(id)

        if (employment == undefined) return
        if (employment.enterpriseId == undefined) return

        const enterprise = this.dbManager.getEnterprise(employment.enterpriseId)

        return {employment, enterprise}
    }

    private getParamsCandidate(id: number) {
        const candidate = this.dbManager.getCandidate(id)

        if (candidate == undefined) return

        return {candidate}
    }

    private buildInfo(params: ParamsConfig) {
        const info = {
            name: "",
            description: "",
            state: "",
            country: "",
            skills: [""]
        }

        if (params.candidate) {
            info.name = params.candidate.name
            info.description = params.candidate.description
            info.state = params.candidate.state
            info.country = params.candidate.country
            info.skills = params.candidate.skills
        } else if (params.enterprise && params.employment) {
            info.name = params.employment.name
            info.description = params.employment.description
            info.state = params.enterprise.state
            info.country = params.enterprise.country
            info.skills = params.employment.skills
        }

        return info
    }

    private build(params: ParamsConfig) {
        let info = this.buildInfo(params)
        if (params.hideDetails) info.name = "<span class=\"blur\">Hidden Name</span>"

        return `
        <div class="card">
            <div class="card-body">
                <div class="d-flex align-items-center">
                    ${params.forEntity === "candidate" ? "<i class=\"bi bi-person-fill fs-1 me-2\"></i>" : ""}
                    ${params.forEntity === "employment" ? "<i class=\"bi bi-building-fill fs-1 me-2\"></i>" : ""}
                    <h5 class="card-title">${info.name}</h5>
                </div>
                <div class="d-flex align-items-center">
                    <i class="bi bi-pin-map-fill  fs-6 me-2"></i>
                    <p class="m-0">${info.state} - ${info.country}</p>
                </div>
                ${
            info.skills.map(skill => {
                return `<span class="badge text-bg-secondary me-2">${skill}</span>`
            }).join("")
        }
                <p class="card-text">${info.description}</p>
                <div class="d-grid">
                    ${params.forEntity === "candidate" && params.viewBtn ? "<button class=\"btn btn-primary\" type=\"button\">Curtir candidato</button>" : ""}
                    ${params.forEntity === "employment" && params.viewBtn ? "<button class=\"btn btn-primary\" type=\"button\">Curtir Vaga</button>" : ""}
                </div>
            </div>
        </div>
        `
    }
}