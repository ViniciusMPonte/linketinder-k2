//Feito por Vinícius Menezes Pontes


import entities.*
import view.Cli
import data.CandidatesData
import data.EnterprisesData
import db.DatabaseConnection
import java.sql.ResultSet
import managers.DatabaseManager


static void main(String[] args) {

    //Conexão com o Banco
    def conn = DatabaseConnection.connect()
    def dbManager = new DatabaseManager(conn)

    //TESTES
    //--------------

//    println dbManager.saveNewCandidate(new NewCandidate(
//            email: "vinicius000brabo@email.com",
//            password: "senha123",
//            name: "Vinicius Dev",
//            description: "Desenvolvedor Java Full Stack",
//            cpf: "123.456.789-00",
//            birthday: "1990-05-10",
//            country: "Brasil",
//            state: "São Paulo",
//            postalCode: "01240-000",
//            skills: ["Java", "Spring", "SQL"]
//    ))
//    def currentId = dbManager.getUserIdByEmail("vinicius000brabo@email.com")
//    println dbManager.getCandidateById(currentId)
//    println currentId
//
//    dbManager.updateCandidate(dbManager.getCandidateById(currentId), new NewCandidate(
//            email: "vinicius002bradbo@email.com",
//            password: "Senha-atualizada",
//            name: "Vinicius Dev Atualizado",
//            description: "Desenvolvedor Java Full Stack",
//            cpf: "123.456.789-00",
//            birthday: "1990-05-10",
//            country: "Brasil",
//            state: "São Paulo",
//            postalCode: "01240-000",
//            skills: ["Java", "Git", "SQL"]
//    ))
//
//    def teste = dbManager.getCandidateById(currentId)
//    println teste
//
//    dbManager.deleteCandidateById(currentId)
//
//    println dbManager.getCandidateById(currentId)
//
//    println dbManager.saveNewEnterprise(new NewEnterprise(
//            email: "contato@neonixtech.com",
//            password: "inovacao2025",
//            name: "Neonix Tech",
//            description: "Liderando a revolução da tecnologia sustentável",
//            cnpj: "45.678.912/0001-55",
//            country: "Brasil",
//            state: "Santa Catarina",
//            postalCode: "88000-001"
//    ))
//    currentId = dbManager.getUserIdByEmail("contato@neonixtech.com")
//    println dbManager.getEnterpriseById(currentId)
//
//    dbManager.updateEnterprise(dbManager.getEnterpriseById(currentId), new NewEnterprise(
//            email: "contato@neonixtech.com",
//            password: "inovacao2025ATUALIZADA",
//            name: "Neonix Tech ATUALIZADA",
//            description: "ATUALIZADA Liderando a revolução da tecnologia sustentável",
//            cnpj: "45.678.912/0001-55",
//            country: "Brasil",
//            state: "Santa Catarina",
//            postalCode: "88880-001"
//    ))
//
//    println dbManager.getEnterpriseById(currentId)
//
//    dbManager.deleteEnterpriseById(currentId)
//
//    println dbManager.getEnterpriseById(currentId)



    println "90 "+ dbManager.saveNewEmployment(new Employment([
            enterpriseId: 10,
            name: "Desenvolvedor Backend Java",
            description: "Vaga para desenvolvimento de APIs REST com Spring Boot e integração com sistemas cloud",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "11000-000",
            skills: ["Java", "Git"]
    ]))

    Integer employmentId = dbManager.getEmploymentId(10, "Desenvolvedor Backend Java")
    def originalEmployment = dbManager.getEmploymentById(employmentId)

    println "103 "+ originalEmployment

    println "105 "+ dbManager.updateEmployment(originalEmployment, new Employment([
            enterpriseId: 10,
            name: "Desenvolvedor Backend Java Atualizado",
            description: "ATUALIZADO Vaga para desenvolvimento de APIs REST com Spring Boot e integração com sistemas cloud",
            country: "Brasil",
            state: "Bahia",
            postalCode: "11000-000",
            skills: ["Java", "Git"]
    ]))

    def updateEmployment = dbManager.getEmploymentById(employmentId)

    println "117 "+ updateEmployment

    println "119 "+ dbManager.deleteEmploymentById(employmentId)

    println "121 "+ dbManager.getEmploymentById(employmentId)


    //--------------

    def candidatesList = CandidatesData.getInfos()
    def enterprisesList = EnterprisesData.getInfos()

    Scanner input = new Scanner(System.in)

    Cli.mainMenu(input, candidatesList, enterprisesList)

    input.close()
    conn.close()
}

