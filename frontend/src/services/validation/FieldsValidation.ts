import ValidationErrorMessage from "./ValidationErrorMessage";

const validSkills: string[] = [
    "TypeScript",
    "JavaScript",
    "Java",
    "Groovy",
    "GitHub",
    "Git",
    "SQL",
    "NoSQL",
    "Angular",
    "Spring",
    "Docker"
]

export default class FieldsValidation extends ValidationErrorMessage {

    entry(key: string, value: string):boolean {
        switch (key) {
            case "name": return this.name(value)
            case "email": return this.email(value)
            case "description": return this.description(value)
            case "skills": return this.skills(value)
            case "password": return this.password(value)
            case "age": return this.age(value)
            case "cpf": return this.cpf(value)
            case "cnpj": return this.cnpj(value)
            case "country": return this.country(value)
            case "state": return this.state(value)
            case "cep": return this.cep(value)
            default: return false
        }
    }

    // @ts-ignore
    name(nome: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)+[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+$/;
        return regex.test(nome);
    }

    email(email: string): boolean {
        const regex = /^[a-z][a-z0-9._-]+@[a-z]+(\.[a-z]+)+$/;
        return regex.test(email);
    }

    password(password: string): boolean {
        const regex = /^\S{6,}$/;
        return regex.test(password);
    }

    description(description: string): boolean {
        const regex = /^([a-záàâãéêíóôõúç,&._"!-]+)( [a-záàâãéêíóôõúç,&._"!-]+)+$/i;
        return regex.test(description);
    }

    skills(skills: string): boolean {
        const regex = this.createRegexFromKeywords(validSkills);

        const skillsArray:string[] = skills.split(",").map(skill => skill.trim());

        let result = true
        skillsArray.forEach((skill) =>{
            if(!regex.test(skill)){
                result = false
            }
        })
        return result;
    }

    age(age: string): boolean {
        const regex = /^(1[8-9]|[2-9]\d)$/;
        return regex.test(age);
    }

    cpf(cpf: string): boolean {
        const regex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
        return regex.test(cpf);
    }

    cnpj(cnpj: string): boolean {
        const regex = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/;
        return regex.test(cnpj);
    }

    country(country: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)*[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]*$/;
        return regex.test(country);
    }

    state(state: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)*[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]*$/;
        return regex.test(state);
    }

    cep(cep: string): boolean {
        const regex = /^\d{5}-\d{3}$/;
        return regex.test(cep);
    }

    createRegexFromKeywords(keywords: string[]): RegExp {
        const escapedKeywords = keywords.map(keyword =>
            keyword.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')
        );

        const pattern = `(${escapedKeywords.join('|')})`;

        return new RegExp(pattern, '');
    }
}