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

export default class ValidationErrorMessage {

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