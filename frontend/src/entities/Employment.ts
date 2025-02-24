export interface EmploymentConfig {
    id?: number,
    name?: string,
    description?: string,
    skills?: string[],
    enterpriseId?: number,
}

export class Employment implements EmploymentConfig {
    public readonly id: number
    name: string = '';
    skills: string[] = [];
    description: string = '';
    enterpriseId?: number;

    constructor(config: EmploymentConfig, enterpriseId?:number) {

        this.id = config.id ? config.id : Math.floor(Math.random() * 1000);

        if (config.name) this.name = config.name;
        if (config.skills) this.skills = config.skills;
        if (config.description) this.description = config.description;
        if (config.enterpriseId) this.enterpriseId = config.enterpriseId
        if (enterpriseId) this.enterpriseId = enterpriseId
    }


    get params(): EmploymentConfig {
        return {
            name: this.name,
            skills: this.skills,
            description: this.description,
        }
    }

    updateWithKeyString(key: string, value: any) {
        switch (key) {
            case "name":
                if (typeof value === 'string') this.name = value
                break
            case "skills":
                if (Array.isArray(value) && value.every(item => typeof item === 'string')) this.skills = value
                break
            case "description":
                if (typeof value === 'string') this.description = value
                break
            default:
                return
        }
    }
}