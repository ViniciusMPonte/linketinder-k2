export interface CandidateConfig {
    id?: number,
    name?: string,
    email?: string,
    country?: string,
    state?: string,
    cep?: string,
    skills?: string[],
    description?: string,
    cpf?: string,
    age?: number,
}

export class Candidate implements CandidateConfig {
    public readonly id: number
    name: string = '';
    email: string = '';
    country: string = '';
    state: string = '';
    cep: string = '';
    skills: string[] = [];
    description: string = '';
    cpf: string = '';
    age: number = 0;


    constructor(config: CandidateConfig) {

        this.id = config.id ? config.id : Math.floor(Math.random() * 1000);

        if (config.name) this.name = config.name;
        if (config.email) this.email = config.email;
        if (config.country) this.country = config.country;
        if (config.state) this.state = config.state;
        if (config.cep) this.cep = config.cep;
        if (config.skills) this.skills = config.skills;
        if (config.description) this.description = config.description;
        if (config.cpf) this.cpf = config.cpf;
        if (config.age) this.age = config.age;
    }


    get params(): CandidateConfig {
        return {
            name: this.name,
            email: this.email,
            country: this.country,
            state: this.state,
            cep: this.cep,
            skills: this.skills,
            description: this.description,
            cpf: this.cpf,
            age: this.age
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
            case "skills":
                if (Array.isArray(value) && value.every(item => typeof item === 'string')) this.skills = value
                break
            case "description":
                if (typeof value === 'string') this.description = value
                break
            case "cpf":
                if (typeof value === 'string') this.cpf = value
                break
            case "age":
                if (typeof value === 'number') this.age = value
                break
            default:
                return
        }
    }
}