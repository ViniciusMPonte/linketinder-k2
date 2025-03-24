package view

import entities.Candidate
import entities.Enterprise
import entities.NewCandidate
import entities.NewEnterprise
import services.CompatibilityService
import services.SectionService
import utils.GenericUtils

import java.util.function.Function

class Menu {

    SectionService section

    Menu(section) {
        this.section = section
    }

    def startMenu() {
        println """
        === Bem vindo ao Linketinder ===
        1. Fazer login Empresa
        2. Fazer login Candidato
        3. Cadastrar Empresa
        4. Cadastrar Candidato
        0. Sair
        """

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.loginEnterprise()
                break
            case "2":
                this.loginCandidate()
                break
            case "3":
                this.registerEnterprise()
                break
            case "4":
                this.registerCandidate()
                break
            case "0":
                println "Saindo..."
                return
            default:
                println "Opção inválida, tente novamente."
        }

        startMenu()
    }

    void loginEnterprise() {

        println "=== Login ==="

        def email = this.getQuestionResult("\nDigite o email: ")
        def password = this.getQuestionResult("\nDigite a senha: ")

        Integer enterpriseId = this.section.dbManager.getUserIdByEmailAndPassword(email, password)

        if (enterpriseId) {
            this.section.userLogged = this.section.dbManager.getEnterpriseById(enterpriseId)
            return
        }

        this.errorMenu("Empresa não encontrada", this.&loginEnterprise)
    }

    void loginCandidate() {

        println "=== Login ==="

        def email = this.getQuestionResult("\nDigite o email: ")
        def password = this.getQuestionResult("\nDigite a senha: ")

        Integer candidateId = this.section.dbManager.getUserIdByEmailAndPassword(email, password)

        if (candidateId) {
            this.section.userLogged = this.section.dbManager.getCandidateById(candidateId)
            return
        }

        println """
        
        Candidato não encontrado
        
        1. tentar novamente
        0. Voltar
        """

        switch (this.section.input.nextLine()) {
            case "1":
                this.loginCandidate()
                break
            case "0":
                println "Voltando..."
                break
            default:
                println "Opção inválida, voltando..."
        }
    }

    void registerEnterprise() {

        println "=== Cadastrar Empresa ==="

        Map params = [
                email      : this.getQuestionResult("\nDigite o email: "),
                password   : this.getQuestionResult("\nDigite a senha: "),
                name       : this.getQuestionResult("\nDigite o nome da empresa: "),
                description: this.getQuestionResult("\nDescreva a empresa: "),
                cnpj       : this.getQuestionResult("\nDigite o CNPJ da empresa: "),
                country    : this.getQuestionResult("\nDigite o país da empresa (Brasil): "),
                state      : this.getQuestionResult("\nDigite o estado da empresa: "),
                postalCode : this.getQuestionResult("\nDigite o CEP da empresa: ")
        ]

        if (this.section.dbManager.saveNewEnterprise(new NewEnterprise(params))) {
            println "\nEmpresa cadastrada com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar empresa", this.&registerEnterprise)
    }

    void registerCandidate() {

        println "=== Cadastrar Candidato ==="

        Map params = [
                email: this.getQuestionResult("\nDigite o email: "),
                password: this.getQuestionResult("\nDigite sua senha: "),
                name: this.getQuestionResult("\nDigite seu nome: "),
                description: this.getQuestionResult("\nDescreva-se: "),
                cpf: this.getQuestionResult("\nDigite seu CPF: "),
                birthday: this.getQuestionResult("\nDigite a data do seu aniversário [YYY-MM-DD]: "),
                country: this.getQuestionResult("\nDigite o páis que mora (Brasil): "),
                state: this.getQuestionResult("\nDigite o estado que você mora: "),
                postalCode: this.getQuestionResult("\nDigite seu CEP: "),
                skills: this.getQuestionResult(
                        "\nDigite suas habilidades, separadas por virgula " +
                                "[Opções: Java, Groovy, JavaScript, Git, GitHub, SQL, NoSQL, Angular, Spring, Docker]: "
                )?.replaceAll(/[{}]/, '')?.split(',')?.toList() ?: []
        ]

        if (this.section.dbManager.saveNewCandidate(new NewCandidate(params))) {
            println "\nCandidato cadastrado com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar candidato", this.&registerCandidate)
    }


    //UTILS
    String getQuestionResult(String question) {
        print question
        return this.section.input.nextLine()
    }

    void errorMenu(String message, Closure callback) {
        println """
    
        $message
        
        1. Tentar novamente
        0. Voltar
        """

        switch (this.section.input.nextLine()) {
            case "1":
                callback.call()
                break
            case "0":
                println "Voltando..."
                break
            default:
                println "Opção inválida, voltando..."
        }
    }
}
