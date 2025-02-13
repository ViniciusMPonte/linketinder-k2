package tests.mocks

class UsersMock {

    static  getCorrectEntriesToCreateOneEnterprise() {
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

    static  getCorrectEntriesToCreateOneCandidate() {
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
}