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

export default class ValidationForms {

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
        const regex = ValidationForms.createRegexFromKeywords(validSkills);

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

    static validate(key: string, value: string):boolean {
        switch (key) {
            case "name": return ValidationForms.name(value)
            case "email": return ValidationForms.email(value)
            case "description": return ValidationForms.description(value)
            case "skills": return ValidationForms.skills(value)
            case "password": return ValidationForms.password(value)
            case "age": return ValidationForms.age(value)
            case "cpf": return ValidationForms.cpf(value)
            case "cnpj": return ValidationForms.cnpj(value)
            case "country": return ValidationForms.country(value)
            case "state": return ValidationForms.state(value)
            case "cep": return ValidationForms.cep(value)
            default: return false
        }
    }

    static validationFailMessageCandidate(key: string):string {
        switch (key) {
            case "name": return "Nome inválido, tente algo como \"João da Silva\""
            case "email": return "Email inválido, tente algo como \"joao.da.silva96@email.com\""
            case "description": return "Descrição inválida, tente algo como \"Desenvolvedora full-stack com experiência em frameworks modernos.\""
            case "skills": return `Habilidades inválidas. Opções disponíveis: ${validSkills.join(', ')}`
            case "password": return "Senha inválida, a senha precisa ter 6 ou mais caracteres, nem espaços."
            case "age": return "Idade inválida, precisa ser entre 18 a 99."
            case "cpf": return "CPF inválido, tente algo como \"111.222.333-44\""
            case "cnpj": return "CNPJ inválido, tente algo como \"12.345.678/0001-99\""
            case "country": return "País inválido, tente algo como \"Brasil\""
            case "state": return "País inválido, tente algo como \"São Paulo\""
            case "cep": return "CEP inválido, tente algo como \"10000-000\""
            default: return ""
        }
    }

    static validationFailMessageEnterprise(key: string):string {
        switch (key) {
            case "name": return "Nome inválido, tente algo como \"TechCorp Solutions\""
            case "email": return "Email inválido, tente algo como \"contato@techcorp.com\""
            case "description": return "Descrição inválida, tente algo como \"Empresa especializada em tecnologia e soluções digitais.\""
            case "skills": return `Habilidades inválidas. Opções disponíveis: ${validSkills.join(', ')}`
            case "password": return "Senha inválida, a senha precisa ter 6 ou mais caracteres, nem espaços."
            case "age": return "Idade inválida, precisa ser entre 18 a 99."
            case "cpf": return "CPF inválido, tente algo como \"111.222.333-44\""
            case "cnpj": return "CNPJ inválido, tente algo como \"12.345.678/0001-99\""
            case "country": return "País inválido, tente algo como \"Brasil\""
            case "state": return "País inválido, tente algo como \"São Paulo\""
            case "cep": return "CEP inválido, tente algo como \"10000-000\""
            default: return ""
        }
    }

    static validationFailMessageEmployment(key: string):string {
        switch (key) {
            case "name": return "Nome inválido, tente algo como \"Engenheiro de Dados\""
            case "description": return "Descrição inválida, tente algo como \"Vaga para construção de pipelines de dados.\""
            case "skills": return `Habilidades inválidas. Opções disponíveis: ${validSkills.join(', ')}`
            default: return ""
        }
    }

}