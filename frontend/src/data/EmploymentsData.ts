import {Employment} from "../entities/Employment";

export default class EmploymentsData {
    static getInfos(enterpriseIds: number[]): Employment[] {
        return [
            new Employment({
                    id: 1,
                    name: "Desenvolvedor Front-end",
                    description: "Vaga para desenvolvimento de interfaces web modernas",
                    skills: ["React", "TypeScript", "HTML5", "CSS3", "Jest"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 2,
                    name: "Engenheiro de Dados",
                    description: "Vaga para construção de pipelines de dados",
                    skills: ["Python", "SQL", "Apache Spark", "Hadoop", "AWS Glue"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 3,
                    name: "DevOps Engineer",
                    description: "Vaga para automação e infraestrutura cloud",
                    skills: ["AWS", "Docker", "Kubernetes", "Terraform", "CI/CD"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 4,
                    name: "UX Designer",
                    description: "Vaga para design de experiências de usuário",
                    skills: ["Figma", "Adobe XD", "User Research", "Prototipagem", "UI Patterns"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 5,
                    name: "Cientista de Dados",
                    description: "Vaga para análise e modelagem preditiva",
                    skills: ["Python", "Machine Learning", "Pandas", "TensorFlow", "Estatística"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 6,
                    name: "Desenvolvedor Mobile",
                    description: "Vaga para desenvolvimento de aplicativos Android/iOS",
                    skills: ["React Native", "Kotlin", "Swift", "Firebase", "GraphQL"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 7,
                    name: "Arquiteto de Cloud",
                    description: "Vaga para desenho de soluções em nuvem",
                    skills: ["AWS", "Azure", "Infraestrutura como Código", "Segurança", "Microserviços"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 8,
                    name: "Especialista em Cybersecurity",
                    description: "Vaga para proteção de sistemas corporativos",
                    skills: ["SIEM", "Pentesting", "GDPR", "Firewalls", "ISO 27001"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 9,
                    name: "Product Manager",
                    description: "Vaga para gestão de ciclo de vida de produtos",
                    skills: ["Roadmapping", "OKRs", "Jira", "Design Thinking", "Metodologias Ágeis"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            ),
            new Employment({
                    id: 10,
                    name: "Desenvolvedor Full-Stack",
                    description: "Vaga para desenvolvimento end-to-end",
                    skills: ["Node.js", "React", "PostgreSQL", "REST APIs", "MongoDB"],
                },
                enterpriseIds[Math.floor(Math.random() * enterpriseIds.length)]
            )
        ];
    }
}
