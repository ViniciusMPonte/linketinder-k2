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

    static entry(key: string, value: string):boolean {
        switch (key) {
            case "name": return FieldsValidation.name(value)
            case "email": return FieldsValidation.email(value)
            case "description": return FieldsValidation.description(value)
            case "skills": return FieldsValidation.skills(value)
            case "password": return FieldsValidation.password(value)
            case "age": return FieldsValidation.age(value)
            case "cpf": return FieldsValidation.cpf(value)
            case "cnpj": return FieldsValidation.cnpj(value)
            case "country": return FieldsValidation.country(value)
            case "state": return FieldsValidation.state(value)
            case "cep": return FieldsValidation.cep(value)
            default: return false
        }
    }

    // @ts-ignore
    static name(nome: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)+[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+$/;
        return regex.test(nome);
    }

    static email(email: string): boolean {
        const regex = /^[a-z][a-z0-9._-]+@[a-z]+(\.[a-z]+)+$/;
        return regex.test(email);
    }

    static password(password: string): boolean {
        const regex = /^\S{6,}$/;
        return regex.test(password);
    }

    static description(description: string): boolean {
        const regex = /^([a-záàâãéêíóôõúç,&._"!-]+)( [a-záàâãéêíóôõúç,&._"!-]+)+$/i;
        return regex.test(description);
    }

    static skills(skills: string): boolean {
        const regex = FieldsValidation.createRegexFromKeywords(validSkills);

        const skillsArray:string[] = skills.split(",").map(skill => skill.trim());

        let result = true
        skillsArray.forEach((skill) =>{
            if(!regex.test(skill)){
                result = false
            }
        })
        return result;
    }

    static age(age: string): boolean {
        const regex = /^(1[8-9]|[2-9]\d)$/;
        return regex.test(age);
    }

    static cpf(cpf: string): boolean {
        const regex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
        return regex.test(cpf);
    }

    static cnpj(cnpj: string): boolean {
        const regex = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/;
        return regex.test(cnpj);
    }

    static country(country: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)*[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]*$/;
        return regex.test(country);
    }

    static state(state: string): boolean {
        const regex = /^([A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]+ ([a-záàâãéêíóôõúç]+ )?)*[A-ZÁÀÂÃÉÊÍÓÔÕÚ][a-záàâãéêíóôõúç]*$/;
        return regex.test(state);
    }

    static cep(cep: string): boolean {
        const regex = /^\d{5}-\d{3}$/;
        return regex.test(cep);
    }

    static createRegexFromKeywords(keywords: string[]): RegExp {
        const escapedKeywords = keywords.map(keyword =>
            keyword.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')
        );

        const pattern = `(${escapedKeywords.join('|')})`;

        return new RegExp(pattern, '');
    }
}