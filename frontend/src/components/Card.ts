import {CandidateConfig} from "../entities/Candidate";

export default class Card {

    private name: string = '';
    private email: string = '';
    private country: string = '';
    private state: string = '';
    private cep: string = '';
    private skills: string[] = [];
    private description: string = '';
    private cpf: string = '';
    private age: number = 0;

    constructor(config: CandidateConfig) {
        if (config.name) this.name = config.name
        if (config.country) this.country = config.country
        if (config.state) this.state = config.state
        if (config.skills) this.skills = config.skills
        if (config.description) this.description = config.description
        if (config.email) this.email = config.email;
        if (config.cep) this.cep = config.cep;
        if (config.cpf) this.cpf = config.cpf;
        if (config.age) this.age = config.age;
    }

    getCard(): string {

        let name: string = this.name
        let state: string = this.state
        let country: string = this.country
        let skills: string[] = this.skills
        let description: string = this.description

        return `
        <div class="card">
            <div class="card-body">
                <div class="d-flex align-items-center">
                    <i class="bi bi-person-fill fs-1 me-2"></i>
                    <h5 class="card-title">${name}</h5>
                </div>
                <div class="d-flex align-items-center">
                    <i class="bi bi-pin-map-fill  fs-6 me-2"></i>
                    <p class="m-0">${state} - ${country}</p>
                </div>
                ${
            skills.map(skill => {
                return `<span class="badge text-bg-secondary">${skill}</span>`
            }).join('')
        }
                <p class="card-text">${description}</p>
                <div class="d-grid">
                    <button class="btn btn-primary" type="button">Curtir candidato</button>
                </div>
            </div>
        </div>
        `
    }
}


