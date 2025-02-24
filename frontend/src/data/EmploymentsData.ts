import {Employment} from "../entities/Employment";

export default class EmploymentsData {
    static removeByName(name: string, employmentList: Employment[]): boolean {
        const originalLength = employmentList.length;
        employmentList.splice(
            0,
            employmentList.length,
            ...employmentList.filter(
                employment => employment.name !== name
            )
        );
        return employmentList.length < originalLength;
    }

    static getInfos(): Employment[] {
        return [
            new Employment({
                id: 1,
                name: "Desenvolvedor Front-end",
                description: "Vaga para desenvolvimento de interfaces web modernas",
                skills: ["React", "TypeScript", "HTML5", "CSS3", "Jest"]
            }),
            new Employment({
                id: 2,
                name: "Engenheiro de Dados",
                description: "Vaga para construção de pipelines de dados",
                skills: ["Python", "SQL", "Apache Spark", "Hadoop", "AWS Glue"]
            }),
            new Employment({
                id: 3,
                name: "DevOps Engineer",
                description: "Vaga para automação e infraestrutura cloud",
                skills: ["AWS", "Docker", "Kubernetes", "Terraform", "CI/CD"]
            }),
            new Employment({
                id: 4,
                name: "UX Designer",
                description: "Vaga para design de experiências de usuário",
                skills: ["Figma", "Adobe XD", "User Research", "Prototipagem", "UI Patterns"]
            }),
            new Employment({
                id: 5,
                name: "Cientista de Dados",
                description: "Vaga para análise e modelagem preditiva",
                skills: ["Python", "Machine Learning", "Pandas", "TensorFlow", "Estatística"]
            }),
            new Employment({
                id: 6,
                name: "Desenvolvedor Mobile",
                description: "Vaga para desenvolvimento de aplicativos Android/iOS",
                skills: ["React Native", "Kotlin", "Swift", "Firebase", "GraphQL"]
            }),
            new Employment({
                id: 7,
                name: "Arquiteto de Cloud",
                description: "Vaga para desenho de soluções em nuvem",
                skills: ["AWS", "Azure", "Infraestrutura como Código", "Segurança", "Microserviços"]
            }),
            new Employment({
                id: 8,
                name: "Especialista em Cybersecurity",
                description: "Vaga para proteção de sistemas corporativos",
                skills: ["SIEM", "Pentesting", "GDPR", "Firewalls", "ISO 27001"]
            }),
            new Employment({
                id: 9,
                name: "Product Manager",
                description: "Vaga para gestão de ciclo de vida de produtos",
                skills: ["Roadmapping", "OKRs", "Jira", "Design Thinking", "Metodologias Ágeis"]
            }),
            new Employment({
                id: 10,
                name: "Desenvolvedor Full-Stack",
                description: "Vaga para desenvolvimento end-to-end",
                skills: ["Node.js", "React", "PostgreSQL", "REST APIs", "MongoDB"]
            }),
            new Employment({
                id: 11,
                name: "Analista de QA",
                description: "Vaga para garantia de qualidade de software",
                skills: ["Testes Automatizados", "Selenium", "Cypress", "JMeter", "TestRail"]
            }),
            new Employment({
                id: 12,
                name: "Engenheiro de Machine Learning",
                description: "Vaga para desenvolvimento de modelos de IA",
                skills: ["Python", "PyTorch", "Computer Vision", "NLP", "Scikit-learn"]
            }),
            new Employment({
                id: 13,
                name: "Tech Lead",
                description: "Vaga para liderança técnica de equipes",
                skills: ["Arquitetura de Software", "Mentoria", "Gestão de Projetos", "Cloud Computing", "DevOps"]
            }),
            new Employment({
                id: 14,
                name: "Analista de Business Intelligence",
                description: "Vaga para análise de dados estratégicos",
                skills: ["Power BI", "SQL", "ETL", "Data Warehousing", "Tableau"]
            }),
            new Employment({
                id: 15,
                name: "Desenvolvedor Java",
                description: "Vaga para sistemas enterprise",
                skills: ["Java 17", "Spring Boot", "Hibernate", "Microservices", "Kafka"]
            }),
            new Employment({
                id: 16,
                name: "Especialista em Blockchain",
                description: "Vaga para desenvolvimento DApps",
                skills: ["Solidity", "Ethereum", "Smart Contracts", "Web3.js", "DeFi"]
            }),
            new Employment({
                id: 17,
                name: "Engenheiro de IoT",
                description: "Vaga para soluções de Internet das Coisas",
                skills: ["Arduino", "Raspberry Pi", "MQTT", "Python", "AWS IoT Core"]
            }),
            new Employment({
                id: 18,
                name: "Scrum Master",
                description: "Vaga para facilitação de processos ágeis",
                skills: ["Cerimônias Scrum", "SAFe", "Kanban", "Gestão de Conflitos", "OKRs"]
            }),
            new Employment({
                id: 19,
                name: "Desenvolvedor Python",
                description: "Vaga para backend e automações",
                skills: ["Django", "Flask", "APIs REST", "Pandas", "FastAPI"]
            }),
            new Employment({
                id: 20,
                name: "Especialista em WordPress",
                description: "Vaga para desenvolvimento de sites CMS",
                skills: ["PHP", "Elementor", "WooCommerce", "SEO", "Plugins Customizados"]
            }),
            new Employment({
                id: 21,
                name: "Analista de Cloud Security",
                description: "Vaga para segurança em ambientes cloud",
                skills: ["AWS Security", "CIS Benchmarks", "IAM", "Encryption", "Compliance"]
            }),
            new Employment({
                id: 22,
                name: "Engenheiro de Observabilidade",
                description: "Vaga para monitoramento de sistemas",
                skills: ["Prometheus", "Grafana", "ELK Stack", "OpenTelemetry", "SRE"]
            }),
            new Employment({
                id: 23,
                name: "Desenvolvedor Golang",
                description: "Vaga para sistemas concorrentes",
                skills: ["Go", "Concorrência", "APIs de Alto Desempenho", "gRPC", "Kubernetes"]
            }),
            new Employment({
                id: 24,
                name: "Especialista em SEO",
                description: "Vaga para otimização de motores de busca",
                skills: ["Google Analytics", "Keyword Research", "Technical SEO", "SEMrush", "Linkbuilding"]
            }),
            new Employment({
                id: 25,
                name: "Arquiteto de Dados",
                description: "Vaga para modelagem de estruturas de dados",
                skills: ["Data Modeling", "SQL", "NoSQL", "Data Governance", "Big Data"]
            })
        ];
    }
}
