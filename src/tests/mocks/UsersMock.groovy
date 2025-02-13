package tests.mocks

import entities.Candidate
import entities.Enterprise

class UsersMock {

    static getCorrectEntriesToCreateOneEnterprise() {
        return [
                "TechCorp Solutions",
                "contato@techcorp.com",
                "Brasil",
                "SP",
                "01000-000",
                "Empresa especializada em tecnologia e soluções digitais.",
                "12.345.678/0001-99",
                "Java, Groovy, JavaScript"
        ]
    }

    static getWrongEntriesToCreateOneEnterprise() {
        return [
                "TechCorp Solutions",
                "contato@techcorp.com",
                "Brasil",
                "SP",
                "01000-000",
                "Empresa especializada em tecnologia e soluções digitais.",
                "12.345.678/0001-99",
                "",
                "Wrong Skills, Validate",
                "Java, Groovy, JavaScript"
        ]
    }

    static getCorrectEntriesToCreateOneCandidate() {
        return [
                "Alice",
                "alice@example.com",
                "20",
                "Brasil",
                "SP",
                "10000-000",
                "Desenvolvedora back-end apaixonada por código limpo e boas práticas.",
                "111.222.333-44",
                "Java, Groovy, JavaScript"
        ]
    }

    static getWrongEntriesToCreateOneCandidate() {
        return [
                "Alice",
                "alice@example.com",
                "20",
                "Brasil",
                "SP",
                "10000-000",
                "Desenvolvedora back-end apaixonada por código limpo e boas práticas.",
                "111.222.333-44",
                "",
                "Wrong Skills, Validate",
                "Java, Groovy, JavaScript"
        ]
    }

    static getCandidatesList() {
        return [
                new Candidate(
                        name: "Alice",
                        email: "alice@example.com",
                        country: "Brasil",
                        state: "SP",
                        cep: "10000-000",
                        description: "Desenvolvedora back-end apaixonada por código limpo e boas práticas.",
                        age: 20,
                        cpf: "111.222.333-44",
                        skills: ["Java", "Groovy", "JavaScript"]
                )
        ]
    }

    static getEnterpriseList() {
        return [
                new Enterprise(
                        name: "TechCorp Solutions",
                        email: "contato@techcorp.com",
                        country: "Brasil",
                        state: "SP",
                        cep: "01000-000",
                        description: "Empresa especializada em tecnologia e soluções digitais.",
                        cnpj: "12.345.678/0001-99",
                        skills: ["Java", "Groovy", "JavaScript"]
                )
        ]
    }

    static getCandidateValidParameters(){
        return [
                name       : "Alice",
                email      : "alice@example.com",
                country    : "Brasil",
                state      : "SP",
                cep        : "10000-000",
                description: "Desenvolvedora back-end apaixonada por código limpo e boas práticas.",
                age        : 20,
                cpf        : "111.222.333-44",
                skills     : ["Java", "Groovy", "JavaScript"]
        ]
    }
}