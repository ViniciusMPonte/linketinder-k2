import {CandidateConfig} from "../entities/Candidate";
import {EmploymentConfig} from "../entities/Employment";
import DatabaseManager from "../data/DatabaseManager";
import {Enterprise} from "../entities/Enterprise";

const dbManager = new DatabaseManager()

export default class Card {

    name: string = '';
    email: string = '';
    country: string = '';
    state: string = '';
    cep: string = '';
    skills: string[] = [];
    description: string = '';
    cpf: string = '';
    age: number = 0;

    enterpriseId?: number | null = null

    forEntity: string = 'candidate'

    constructor(config: CandidateConfig, forEntity: string, enterpriseId?:number) {
        if (forEntity) this.forEntity = forEntity;

        if (config.name) this.name = config.name
        if (config.country) this.country = config.country
        if (config.state) this.state = config.state
        if (config.skills) this.skills = config.skills
        if (config.description) this.description = config.description
        if (config.email) this.email = config.email;
        if (config.cep) this.cep = config.cep;
        if (config.cpf) this.cpf = config.cpf;
        if (config.age) this.age = config.age;

        if (enterpriseId) this.enterpriseId = enterpriseId;

        if (this.forEntity === 'employment' && enterpriseId) {

            let filteredEnterprises = dbManager.enterprises?.filter(enterprise => enterprise.id === enterpriseId)
            if(filteredEnterprises != undefined){
                this.state = filteredEnterprises[0].state
                this.country = filteredEnterprises[0].country
            }
        }
    }

    getCard(viewBtn:boolean = true): string {

        let name: string = this.name
        let state: string = this.state
        let country: string = this.country
        let skills: string[] = this.skills
        let description: string = this.description

        return `
        <div class="card">
            <div class="card-body">
                <div class="d-flex align-items-center">
                    ${this.forEntity === 'candidate' ? '<i class="bi bi-person-fill fs-1 me-2"></i>' : ''}
                    ${this.forEntity === 'employment' ? '<i class="bi bi-building-fill fs-1 me-2"></i>' : ''}
                    <h5 class="card-title">${name}</h5>
                </div>
                <div class="d-flex align-items-center">
                    <i class="bi bi-pin-map-fill  fs-6 me-2"></i>
                    <p class="m-0">${state} - ${country}</p>
                </div>
                ${
            skills.map(skill => {
                return `<span class="badge text-bg-secondary me-2">${skill}</span>`
            }).join('')
        }
                <p class="card-text">${description}</p>
                <div class="d-grid">
                    ${this.forEntity === 'candidate' && viewBtn ? '<button class="btn btn-primary" type="button">Curtir candidato</button>' : ''}
                    ${this.forEntity === 'employment' && viewBtn ? '<button class="btn btn-primary" type="button">Curtir Vaga</button>' : ''}
                </div>
            </div>
        </div>
        `
    }
}


