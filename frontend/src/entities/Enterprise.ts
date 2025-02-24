export interface EnterpriseConfig {
    id?: number,
    name?: string,
    email?: string,
    country?: string,
    state?: string,
    cep?: string,
    employmentsId?: Number[],
    description?: string,
    cnpj?: string,
}

export class Enterprise implements EnterpriseConfig {
    public readonly id: number
    name: string = '';
    email: string = '';
    country: string = '';
    state: string = '';
    cep: string = '';
    employmentsId: Number[] = [];
    description: string = '';
    cnpj: string = '';


    constructor(config: EnterpriseConfig) {

        this.id = config.id ? config.id : Math.floor(Math.random() * 1000);

        if (config.name) this.name = config.name;
        if (config.email) this.email = config.email;
        if (config.country) this.country = config.country;
        if (config.state) this.state = config.state;
        if (config.cep) this.cep = config.cep;
        if (config.employmentsId) this.employmentsId = config.employmentsId;
        if (config.description) this.description = config.description;
        if (config.cnpj) this.cnpj = config.cnpj;
    }


    get params(): EnterpriseConfig {
        return {
            name: this.name,
            email: this.email,
            country: this.country,
            state: this.state,
            cep: this.cep,
            employmentsId: this.employmentsId,
            description: this.description,
            cnpj: this.cnpj,
        }
    }

    updateWithKeyString(key: string, value: any) {
        switch (key) {
            case "name":
                if (typeof value === 'string') this.name = value
                break
            case "email":
                if (typeof value === 'string') this.email = value
                break
            case "country":
                if (typeof value === 'string') this.country = value
                break
            case "state":
                if (typeof value === 'string') this.state = value
                break
            case "cep":
                if (typeof value === 'string') this.cep = value
                break
            case "employmentsId":
                if (Array.isArray(value) && value.every(item => typeof item === 'number')) this.employmentsId = value
                break
            case "description":
                if (typeof value === 'string') this.description = value
                break
            case "cnpj":
                if (typeof value === 'string') this.cnpj = value
                break
            default:
                return
        }
    }
}