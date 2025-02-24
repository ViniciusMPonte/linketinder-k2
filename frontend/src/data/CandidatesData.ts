import {Candidate} from "../entities/Candidate";

export default class CandidatesData {
    static removeByName(name: string, candidatesList: Candidate[]): boolean {
        const originalLength = candidatesList.length;
        candidatesList.splice(
            0,
            candidatesList.length,
            ...candidatesList.filter(
                candidate => candidate.name !== name
            )
        );
        return candidatesList.length < originalLength;
    }

    static getInfos(): Candidate[] {
        return [
            new Candidate(
                {                        name: "Alice",
                    email: "alice@example.com",
                    country: "Brasil",
                    state: "SP",
                    cep: "10000-000",
                    description: "Desenvolvedora back-end apaixonada por código limpo e boas práticas.",
                    age: 20,
                    cpf: "111.222.333-44",
                    skills: ["Java", "Groovy", "JavaScript"]}
            ),
            new Candidate(
                {
                    name: "Bob",
                    email: "bob@example.com",
                    country: "Brasil",
                    state: "RJ",
                    cep: "20000-000",
                    description: "Engenheiro de software com experiência em DevOps e automação.",
                    age: 25,
                    cpf: "222.333.444-55",
                    skills: ["Git", "GitHub", "Docker"]
                }
            ),
            new Candidate(
                {
                    name: "Carlos",
                    email: "carlos@example.com",
                    country: "Brasil",
                    state: "MG",
                    cep: "30000-000",
                    description: "Especialista em bancos de dados, entusiasta de Big Data e análise de dados.",
                    age: 30,
                    cpf: "333.444.555-66",
                    skills: ["SQL", "NoSQL", "Spring"]
                }
            ),
            new Candidate(
                {
                    name: "Diana",
                    email: "diana@example.com",
                    country: "Brasil",
                    state: "RS",
                    cep: "40000-000",
                    description: "Desenvolvedora full-stack com experiência em frameworks modernos.",
                    age: 28,
                    cpf: "444.555.666-77",
                    skills: ["Angular", "JavaScript", "Groovy"]
                }
            ),
            new Candidate(
                {
                    name: "Eduardo",
                    email: "eduardo@example.com",
                    country: "Brasil",
                    state: "BA",
                    cep: "50000-000",
                    description: "Arquiteto de software com foco em soluções escaláveis e robustas.",
                    age: 35,
                    cpf: "555.666.777-88",
                    skills: ["Docker", "Git", "Spring"]
                }
            ),
        ];
    }
}
