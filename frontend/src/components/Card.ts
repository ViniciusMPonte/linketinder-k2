import {CandidateConfig} from "../entities/Candidate";

export default class Card {

    private _name: string = '';
    private _email: string = '';
    private _country: string = '';
    private _state: string = '';
    private _cep: string = '';
    private _skills: string[] = [];
    private _description: string = '';
    private _cpf: string = '';
    private _age: number = 0;

    constructor(config: CandidateConfig) {
        if (config.name) this._name = config.name
        if (config.country) this._country = config.country
        if (config.state) this._state = config.state
        if (config.skills) this._skills = config.skills
        if (config.description) this._description = config.description
        if (config.email) this._email = config.email;
        if (config.cep) this._cep = config.cep;
        if (config.cpf) this._cpf = config.cpf;
        if (config.age) this._age = config.age;
    }

    getCard(): string {

        let name: string = this._name
        let state: string = this._state
        let country: string = this._country
        let skills: string[] = this._skills
        let description: string = this._description

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


