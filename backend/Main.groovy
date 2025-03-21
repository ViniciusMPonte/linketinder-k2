//Feito por Vinícius Menezes Pontes


import entities.NewCandidate
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

    println dbManager.saveNewCandidate(new NewCandidate(
            email: "vinicius000brabo@email.com",
            password: "senha123",
            name: "Vinicius Dev",
            description: "Desenvolvedor Java Full Stack",
            cpf: "123.456.789-00",
            birthday: "1990-05-10",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "01240-000",
            skills: ["Java", "Spring", "SQL"]
    ))
    def currentId = dbManager.getUserIdByEmail("vinicius000brabo@email.com")
    println dbManager.getCandidateById(currentId)
    println currentId

    dbManager.updateCandidate(dbManager.getCandidateById(currentId), new NewCandidate(
            email: "vinicius002bradbo@email.com",
            password: "Senha-atualizada",
            name: "Vinicius Dev Atualizado",
            description: "Desenvolvedor Java Full Stack",
            cpf: "123.456.789-00",
            birthday: "1990-05-10",
            country: "Brasil",
            state: "São Paulo",
            postalCode: "01240-000",
            skills: ["Java", "Git", "SQL"]
    ))

    def teste = dbManager.getCandidateById(currentId)
    println teste

    dbManager.deleteCandidateById(currentId)

    println dbManager.getCandidateById(currentId)
    //--------------

    def candidatesList = CandidatesData.getInfos()
    def enterprisesList = EnterprisesData.getInfos()

    Scanner input = new Scanner(System.in)

    Cli.mainMenu(input, candidatesList, enterprisesList)

    input.close()
    conn.close()
}

