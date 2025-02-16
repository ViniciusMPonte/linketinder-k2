package view

import entities.Candidate
import entities.Enterprise

import managers.UserManager
import services.CompatibilityService

class Cli {

    static def mainMenu(Scanner input, List<Candidate> candidatesList, List<Enterprise> enterprisesList) {
        println """
        === Menu Principal ===
        1. Listar Usuários
        2. Listar Compartibilidade Empresa/Candidatos
        3. Cadastrar
        4. Deletar
        0. Sair
        """

        print "Escolha uma opção: "

        switch (input.nextLine()) {
            case "1":
                listMenu(input, candidatesList, enterprisesList)
                break
            case "2":
                println CompatibilityService.formatAllEnterprisesMatchList(candidatesList, enterprisesList)
                break
            case "3":
                registerMenu(input, candidatesList, enterprisesList)
                break
            case "4":
                removeMenu(input, candidatesList, enterprisesList)
                break
            case "0":
                println "Saindo..."
                return
            default:
                println "Opção inválida, tente novamente."
        }

        mainMenu(input, candidatesList, enterprisesList)
    }

    static def listMenu(Scanner input, List<Candidate> candidatesList, List<Enterprise> enterprisesList) {
        println """
        === Listar Usuários ===
        1. Listar empresas
        2. Listar candidatos
        3. Listar todos
        """

        print "Escolha uma opção: "

        switch (input.nextLine()) {
            case "1":
                println enterprisesList
                return
            case "2":
                println candidatesList
                return
            case "3":
                println candidatesList
                println enterprisesList
                return
            default:
                println "Opção inválida, tente novamente."
        }

        listMenu(input, candidatesList, enterprisesList)
    }

    static def registerMenu(Scanner input, List<Candidate> candidatesList, List<Enterprise> enterprisesList) {
        println """
        === Cadastrar ===
        1. Cadastrar empresas
        2. Cadastrar candidatos
        0. Voltar
        """

        print "Escolha uma opção: "

        switch (input.nextLine()) {
            case "1":
                UserManager.registerEnterprise(input, enterprisesList)
                return
            case "2":
                UserManager.registerCandidate(input, candidatesList)
                return
            case "0":

                return
            default:
                println "Opção inválida, tente novamente."
        }

        listMenu(input, candidatesList, enterprisesList)
    }

    def static removeMenu(Scanner input, List<Candidate> candidatesList, List<Enterprise> enterprisesList){
        println """
        === Deletar ===
        1. Deletar empresa
        2. Deletar candidato
        0. Voltar
        """

        print "Escolha uma opção: "

        switch (input.nextLine()) {
            case "1":
                UserManager.removeEnterprise(input, enterprisesList)
                return
            case "2":
                UserManager.removeCandidate(input, candidatesList)
                return
            case "0":
                return
            default:
                println "Opção inválida, tente novamente."
        }

        listMenu(input, candidatesList, enterprisesList)
    }

}

