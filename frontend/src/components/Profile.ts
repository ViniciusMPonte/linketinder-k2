import {CandidateConfig} from "../entities/Candidate";
import {EnterpriseConfig} from "../entities/Enterprise";

export class ProfileEnterprise {

    name: string = '';
    email: string = '';
    country: string = '';
    state: string = '';
    cep: string = '';
    description: string = '';
    cnpj: string = '';

    constructor(config: EnterpriseConfig) {
        if (config.name) this.name = config.name
        if (config.email) this.email = config.email;
        if (config.description) this.description = config.description
        if (config.country) this.country = config.country
        if (config.state) this.state = config.state
        if (config.cep) this.cep = config.cep;
        if (config.cnpj) this.cnpj = config.cnpj;
    }

    get(): string {

        return `
                    <div class="row g-0">
                        <div class="col-xl-6 d-none d-xl-block bg-info">

                            <div class="d-flex flex-column align-items-center justify-content-center align-items-center" style="height: 100vh;">

                                <div class="rounded-circle d-flex align-items-center justify-content-center align-items-center"  style="width: 150px; height: 150px; border: 3px solid white">
                                    <i class="bi bi-building-fill fs-1 text-light"></i>
                                </div>
                                <h2 class="card-title text-light m-5">${this.name}</h2>
                            </div>

                        </div>
                        <div class="col-xl-6">
                            <div class="card-body p-md-5 text-black">
                                <h3 class="mb-5">Informações do Perfil</h3>

                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="text" id="enterprise-name-input" class="form-control form-control-lg" readonly value="${this.name}"/>
                                    <label class="form-label" for="enterprise-name-input">Nome</label>
                                </div>

                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="text" id="enterprise-email-input" class="form-control form-control-lg" readonly value="${this.email}"/>
                                    <label class="form-label" for="enterprise-email-input">Email</label>
                                </div>

                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="text" id="enterprise-description-input" class="form-control form-control-lg" readonly value="${this.description}"/>
                                    <label class="form-label" for="enterprise-description-input">Descrição</label>
                                </div>


                                <div class="row">

                                    <div class="col-md-6 mb-4">
                                        <div data-mdb-input-init class="form-outline">
                                            <input type="text" id="enterprise-country-input"
                                                   class="form-control form-control-lg" readonly value="${this.country}"/>
                                            <label class="form-label" for="enterprise-country-input">País</label>
                                        </div>
                                    </div>

                                    <div class="col-md-6 mb-4">
                                        <div data-mdb-input-init class="form-outline">
                                            <input type="text" id="enterprise-state-input"
                                                   class="form-control form-control-lg" readonly value="${this.state}"/>
                                            <label class="form-label" for="enterprise-state-input">Estado</label>
                                        </div>

                                    </div>
                                </div>

                                <div class="row">

                                    <div class="col-md-6 mb-4">
                                        <div data-mdb-input-init class="form-outline">
                                            <input type="text" id="enterprise-cep-input"
                                                   class="form-control form-control-lg" readonly value="${this.cep}"/>
                                            <label class="form-label" for="enterprise-cep-input">CEP</label>
                                        </div>
                                    </div>

                                    <div class="col-md-6 mb-4">
                                        <div data-mdb-input-init class="form-outline">
                                            <input type="text" id="enterprise-cnpj-input"
                                                   class="form-control form-control-lg" readonly value="${this.cnpj}"/>
                                            <label class="form-label" for="enterprise-cnpj-input">CNPJ</label>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
        `
    }
}

export class ProfileCandidate {

    name: string = '';
    email: string = '';
    country: string = '';
    state: string = '';
    cep: string = '';
    description: string = '';
    cpf: string = '';
    age: number = 0;
    skills: string[] = [];

    constructor(config: CandidateConfig) {
        if (config.name) this.name = config.name
        if (config.email) this.email = config.email;
        if (config.description) this.description = config.description
        if (config.country) this.country = config.country
        if (config.state) this.state = config.state
        if (config.cep) this.cep = config.cep;
        if (config.cpf) this.cpf = config.cpf;
        if (config.age) this.age = config.age;
        if (config.skills) this.skills = config.skills;

    }

    get(): string {

        return `
            <div class="row g-0">
                <div class="col-xl-6 d-none d-xl-block bg-info">
            
                    <div class="d-flex flex-column align-items-center justify-content-center align-items-center"
                         style="height: 100vh;">
            
                        <div class="rounded-circle d-flex align-items-center justify-content-center align-items-center"
                             style="width: 150px; height: 150px; border: 3px solid white">
                            <i class="bi bi-person-fill fs-1 text-light"></i>
                        </div>
                        <h2 class="card-title text-light m-5">${this.name}</h2>
                    </div>
                </div>
                <div class="col-xl-6">
                    <div class="card-body p-md-5 text-black">
                        <h3 class="mb-5">Informações do Perfil</h3>
            
                        <div data-mdb-input-init class="form-outline mb-4">
                            <input type="text" id="candidate-name-input" class="form-control form-control-lg" readonly value="${this.name}"/>
                            <label class="form-label" for="candidate-name-input">Nome</label>
                        </div>
            
                        <div data-mdb-input-init class="form-outline mb-4">
                            <input type="text" id="candidate-email-input" class="form-control form-control-lg" readonly value="${this.email}"/>
                            <label class="form-label" for="candidate-email-input">Email</label>
                        </div>
            
                        <div data-mdb-input-init class="form-outline mb-4">
                            <input type="text" id="candidate-description-input" class="form-control form-control-lg" readonly value="${this.description}"/>
                            <label class="form-label" for="candidate-description-input">Descrição</label>
                        </div>
            
                        <div data-mdb-input-init class="form-outline mb-4">
                            <input type="text" id="candidate-skills-input" class="form-control form-control-lg" readonly value="${this.skills.join(', ')}"/>
                            <label class="form-label" for="candidate-skills-input">Habilidades</label>
                        </div>
            
                        <div class="row">
            
                            <div class="col-md-6 mb-4">
                                <div data-mdb-input-init class="form-outline">
                                    <input type="text" id="candidate-age-input"
                                           class="form-control form-control-lg" readonly value="${this.age}"/>
                                    <label class="form-label" for="candidate-age-input">Idade</label>
                                </div>
                            </div>
            
                            <div class="col-md-6 mb-4">
                                <div data-mdb-input-init class="form-outline">
                                    <input type="text" id="candidate-cpf-input"
                                           class="form-control form-control-lg" readonly value="${this.cpf}"/>
                                    <label class="form-label" for="candidate-cpf-input">CPF</label>
                                </div>
                            </div>
            
                        </div>
            
                        <div class="row">
            
                            <div class="col-md-6 mb-4">
                                <div data-mdb-input-init class="form-outline">
                                    <input type="text" id="candidate-country-input"
                                           class="form-control form-control-lg" readonly value="${this.country}"/>
                                    <label class="form-label" for="candidate-country-input">País</label>
                                </div>
                            </div>
            
                            <div class="col-md-6 mb-4">
                                <div data-mdb-input-init class="form-outline">
                                    <input type="text" id="candidate-state-input"
                                           class="form-control form-control-lg" readonly value="${this.state}"/>
                                    <label class="form-label" for="candidate-state-input">Estado</label>
                                </div>
            
                            </div>
                        </div>
            
            
                        <div data-mdb-input-init class="form-outline mb-4">
                            <input type="text" id="candidate-cep-input" class="form-control form-control-lg" readonly value="${this.cep}"/>
                            <label class="form-label" for="candidate-cep-input">CEP</label>
                        </div>
                    </div>
                </div>
            </div>
        `
    }
}


