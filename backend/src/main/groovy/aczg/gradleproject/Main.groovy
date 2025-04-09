package aczg.gradleproject
//Feito por Vinícius Menezes Pontes

import db.DatabaseConnection
import entities.Candidate
import entities.Enterprise
import entities.Employment
import services.SectionService
import view.Menu
import managers.DatabaseManager
import java.text.SimpleDateFormat


static void main(String[] args) {

    def conn = DatabaseConnection.connect()

    def dbManager = new DatabaseManager(conn)
    Scanner input = new Scanner(System.in)
    def section = new SectionService(input, dbManager)
    def menu = new Menu(section)

    //TESTES-------------------

    def birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15")

    Candidate candidate = new Candidate([
            email: "john.doe@example.com",
            password: "senhaSegura123",
            name: "John Doe",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf: "123.456.789-00", // ou "12345678900" dependendo da formatação
            birthday: birthdayDate,
            country: 'Brasil',
            state: "São Paulo", // Estado deve existir na tabela 'states'
            postalCode: "12345-678",
            skills: ["Java", "SQL", "Spring", "Groovy"]
    ])
// Criando o objeto Candidate

// Verifique se todos os campos estão preenchidos
    //assert candidate.isAllSet(): "Candidato não tem todos os campos obrigatórios"

    boolean isSaved = dbManager.saveNewCandidateTESTE(candidate)
    if (isSaved) {
        println("Candidato salvo com sucesso!")
    } else {
        println("Falha ao salvar candidato.")
    }

    int id = dbManager.getUserIdByEmailAndPassword("john.doe@example.com", "senhaSegura123")
    def oldCandidate = dbManager.getCandidateById(id)

    dbManager.updateCandidateTESTE(oldCandidate, new Candidate([
            email: "john.doe@example.com",
            password: "senhaSegura123",
            name: "DEU CERTO",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf: "123.456.789-00", // ou "12345678900" dependendo da formatação
            birthday: birthdayDate,
            country: 'Brasil',
            state: "São Paulo", // Estado deve existir na tabela 'states'
            postalCode: "12345-678",
            skills: ["Java", "SQL", "Spring", "Groovy"]
    ]))

    println dbManager.getCandidateById(id)

    if (dbManager.deleteCandidateByIdTESTE(id)) {
        println "candidato deletado com sucesso"
    } else {
        println "erro ao deletar usuário"
    }

    //-------------------

    Enterprise enterprise = new Enterprise([
            email: "empresa.teste@example.com",
            password: "senhaSegura123",
            name: "Pontevi Teste",
            description: "Empresa Top",
            cnpj: "11.222.333/0001-01",
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678"
    ])

    isSaved = dbManager.saveNewEnterpriseTESTE(enterprise)
    if (isSaved) {
        println("Empresa salva com sucesso!")
    } else {
        println("Falha ao salvar empresa.")
    }


    id = dbManager.getUserIdByEmailAndPassword("empresa.teste@example.com", "senhaSegura123")

    def oldEnterprise = dbManager.getEnterpriseById(id)

    dbManager.updateEnterpriseTESTE(oldEnterprise, new Enterprise([
            email: "empresa.teste@example.com",
            password: "senhaSegura123",
            name: "Pontevi Teste ATUALIZADO",
            description: "Empresa Top",
            cnpj: "11.222.333/0001-01",
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678"
    ]))

    println dbManager.getEnterpriseById(id)

    if (dbManager.deleteEnterpriseByIdTESTE(id)) {
        println "Empresa deletada com sucesso"
    } else {
        println "erro ao deletar empresa"
    }

    //-------------------

    Employment employment = new Employment([
            enterpriseId: 6,
            name: "Desenvolvedor Backend",
            description: "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "01000-000",
            skills: ["Java"]
    ])

    isSaved = dbManager.saveNewEmploymentTESTE(employment)
    if (isSaved) {
        println("Vaga salva com sucesso!")
    } else {
        println("Falha ao salvar vaga.")
    }

    id = dbManager.getEmploymentId(6, "Desenvolvedor Backend")

    def oldEmployment = dbManager.getEmploymentById(id)

    dbManager.updateEmploymentTESTE(oldEmployment, new Employment([
            enterpriseId: 6,
            name: "Desenvolvedor Backend ATUALIZADO",
            description: "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "01000-000",
            skills: ["Java"]
    ]))

    println dbManager.getEmploymentById(id)

    if (dbManager.deleteEmploymentByIdTESTE(id)) {
        println "Vaga deletada com sucesso"
    } else {
        println "erro ao deletar vaga"
    }
    //TESTES-------------------


    menu.startMenu()

    input.close()
    conn.close()
}

