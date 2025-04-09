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
import managers.TransactionManager

import db.*

static void main(String[] args) {

    def conn = DatabaseConnection.connect()
    def transactionManager = new TransactionManager(conn)
    def dbManager = new DatabaseManager(conn, transactionManager)
    def dbCandidate = new CRUDCandidate(conn, transactionManager)
    def dbEnterprise = new CRUDEnterprise(conn, transactionManager)
    def dbEmployment = new CRUDEmployment(conn, transactionManager)
    
    def db =  [
            candidate : dbCandidate,
            enterprise: dbEnterprise,
            employment: dbEmployment
    ]
    
    
    Scanner input = new Scanner(System.in)
    def section = new SectionService(input, db)
    def menu = new Menu(section)

    //TESTES-------------------

    def birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15")

    Candidate candidate = new Candidate([
            email: "john.doe@example.com",
            password: "senhaSegura123",
            name: "John Doe",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf: "123.456.789-00",
            birthday: birthdayDate,
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678",
            skills: ["Java", "SQL", "Spring", "Groovy"]
    ])

    boolean isSaved = db.candidate.save(candidate)
    if (isSaved) {
        println("Candidato salvo com sucesso!")
    } else {
        println("Falha ao salvar candidato.")
    }

    int id = db.candidate.getUserIdByEmailAndPassword("john.doe@example.com", "senhaSegura123")
    def oldCandidate = db.candidate.getById(id)

    db.candidate.update(oldCandidate, new Candidate([
            email: "john.doe@example.com",
            password: "senhaSegura123",
            name: "DEU CERTO",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf: "123.456.789-00",
            birthday: birthdayDate,
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678",
            skills: ["Java", "SQL", "Spring", "Groovy"]
    ]))

    println db.candidate.getById(id)

    if (db.candidate.deleteById(id)) {
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

    isSaved = db.enterprise.save(enterprise)
    if (isSaved) {
        println("Empresa salva com sucesso!")
    } else {
        println("Falha ao salvar empresa.")
    }


    id = db.enterprise.getUserIdByEmailAndPassword("empresa.teste@example.com", "senhaSegura123")

    def oldEnterprise = db.enterprise.getById(id)

    db.enterprise.update(oldEnterprise, new Enterprise([
            email: "empresa.teste@example.com",
            password: "senhaSegura123",
            name: "Pontevi Teste ATUALIZADO",
            description: "Empresa Top",
            cnpj: "11.222.333/0001-01",
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678"
    ]))

    println db.enterprise.getById(id)

    if (db.enterprise.deleteById(id)) {
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

    isSaved = db.employment.save(employment)
    if (isSaved) {
        println("Vaga salva com sucesso!")
    } else {
        println("Falha ao salvar vaga.")
    }

    id = db.employment.getEmploymentId(6, "Desenvolvedor Backend")

    def oldEmployment = db.employment.getById(id)

    db.employment.update(oldEmployment, new Employment([
            enterpriseId: 6,
            name: "Desenvolvedor Backend ATUALIZADO",
            description: "Responsável pelo desenvolvimento e manutenção de APIs RESTful usando Java e Spring Boot.",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "01000-000",
            skills: ["Java"]
    ]))

    println db.employment.getById(id)

    if (db.employment.deleteById(id)) {
        println "Vaga deletada com sucesso"
    } else {
        println "erro ao deletar vaga"
    }
    //TESTES-------------------


    menu.startMenu()

    input.close()
    conn.close()
}

