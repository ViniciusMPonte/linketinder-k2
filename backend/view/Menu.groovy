package view

import entities.Employment
import entities.NewCandidate
import entities.NewEnterprise
import services.SectionService


class Menu {

    SectionService section

    Menu(section) {
        this.section = section
    }

    def startMenu() {
        if (this.section.userLogged instanceof NewCandidate) {
            this.mainMenuCandidate()
        }
        if (this.section.userLogged instanceof NewEnterprise) {
            this.mainMenuEnterprise()
        }
        if (this.section.userLogged == null) {
            println """
            === Bem vindo ao Linketinder ===
            1. Fazer login Empresa
            2. Fazer login Candidato
            3. Cadastrar Empresa
            4. Cadastrar Candidato
            0. Sair
            """.stripIndent()

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

        }
        this.startMenu()
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
        """.stripIndent()

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
                email      : this.getQuestionResult("\nDigite o email: "),
                password   : this.getQuestionResult("\nDigite sua senha: "),
                name       : this.getQuestionResult("\nDigite seu nome: "),
                description: this.getQuestionResult("\nDescreva-se: "),
                cpf        : this.getQuestionResult("\nDigite seu CPF: "),
                birthday   : this.getQuestionResult("\nDigite a data do seu aniversário [YYYY-MM-DD]: "),
                country    : this.getQuestionResult("\nDigite o páis que mora (Brasil): "),
                state      : this.getQuestionResult("\nDigite o estado que você mora: "),
                postalCode : this.getQuestionResult("\nDigite seu CEP: "),
                skills     : this.getQuestionResult(
                        "\nDigite suas habilidades, separadas por virgula: "
                )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
        ]

        if (this.section.dbManager.saveNewCandidate(new NewCandidate(params))) {
            println "\nCandidato cadastrado com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar candidato", this.&registerCandidate)
    }

    def mainMenuCandidate() {
        println """
        === Menu Candidato ===
        1. Visualizar Vagas
        2. Perfil
        0. Logout
        """.stripIndent()

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.chooseEmploymentToLike()
                break
            case "2":
                this.RUDMenuCandidate()
                break
            case "0":
                println "Deslogando..."
                this.section.userLogged = null
                break
            default:
                println "Opção inválida, tente novamente."
        }
    }

    void chooseEmploymentToLike() {

        int[] employmentIds = this.section.dbManager.getEmploymentIds()
        employmentIds.each { id ->
            println this.section.dbManager.getEmploymentById(id as int)
        }

    }

    def RUDMenuCandidate() {
        println """
        === Perfil do Candidato ===
        1. Visualizar Perfil
        2. Editar Perfil
        3. Excluir Perfil
        0. Voltar
        """.stripIndent()

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.readCandidateProfile()
                break
            case "2":
                this.updateCandidate()
                break
            case "3":
                this.deleteCandidate()
                break
            case "0":
                println "Voltando..."
                return
            default:
                println "Opção inválida, tente novamente."
        }

        if (this.section.userLogged == null) return
        this.RUDMenuCandidate()
    }

    void readCandidateProfile() {
        println this.section.userLogged
    }

    void updateCandidate() {

        println "=== Editar Candidato ==="

        Map params = [
                email      : this.getQuestionResult("\nDigite o email: "),
                password   : this.getQuestionResult("\nDigite sua senha: "),
                name       : this.getQuestionResult("\nDigite seu nome: "),
                description: this.getQuestionResult("\nDescreva-se: "),
                cpf        : this.getQuestionResult("\nDigite seu CPF: "),
                birthday   : this.getQuestionResult("\nDigite a data do seu aniversário [YYYY-MM-DD]: "),
                country    : this.getQuestionResult("\nDigite o páis que mora (Brasil): "),
                state      : this.getQuestionResult("\nDigite o estado que você mora: "),
                postalCode : this.getQuestionResult("\nDigite seu CEP: "),
                skills     : this.getQuestionResult(
                        "\nDigite suas habilidades, separadas por virgula: "
                )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
        ]

        if (this.section.dbManager.updateCandidate(this.section.userLogged as NewCandidate, new NewCandidate(params))) {
            this.section.userLogged = this.section.dbManager.getCandidateById(this.section.userLogged.getId())
            println "\nPerfil editado com sucesso."
            return
        }

        this.errorMenu("Falha ao tentar editar perfil do candidato.", this.&registerCandidate)
    }

    void deleteCandidate() {
        this.section.dbManager.deleteCandidateById(this.section.userLogged.getId())
        if (!this.section.dbManager.getCandidateById(this.section.userLogged.getId())) {
            this.section.userLogged = null
            println "\nCandidato Excluído com sucesso!"
            return
        }
        this.errorMenu("Erro ao tentar excluir candidato", this.&deleteCandidate)
    }

    def mainMenuEnterprise() {
        println """
        === Menu Empresa ===
        1. Visualizar Candidatos
        2. Minhas Vagas
        3. Perfil
        0. Logout
        """.stripIndent()

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.chooseCandidateToLike()
                break
            case "2":
                this.RUDMenuEmployment()
                break
            case "3":
                this.RUDMenuEnterprise()
                break
            case "0":
                println "Deslogando..."
                this.section.userLogged = null
                break
            default:
                println "Opção inválida, tente novamente."
        }
    }

    void chooseCandidateToLike() {

        int[] candidatesIds = this.section.dbManager.getCandidateIds()
        candidatesIds.each { id ->
            println this.section.dbManager.getCandidateById(id as int)
        }

    }

    def RUDMenuEmployment() {
        println """
        === Vagas da Empresa ===
        1. Visualizar Vagas
        2. Criar Nova Vaga
        3. Editar Vaga
        4. Excluir Vaga
        0. Voltar
        """.stripIndent()

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.readEmployments()
                break
            case "2":
                this.createEmployment()
                break
            case "3":
                this.updateEmployment()
                break
            case "4":
                this.deleteEmployment()
                break
            case "0":
                println "Voltando..."
                return
            default:
                println "Opção inválida, tente novamente."
        }

        if (this.section.userLogged == null) return
        this.RUDMenuEmployment()
    }

    void createEmployment() {

        println "=== Criar Vaga ==="

        Map params = [
                enterpriseId: this.section.userLogged.getId(),
                name       : this.getQuestionResult("\nDigite o nome da vaga: "),
                description: this.getQuestionResult("\nDescreva a vaga: "),
                country    : this.getQuestionResult("\nDigite o país da vaga (Brasil): "),
                state      : this.getQuestionResult("\nDigite o estado onde a vaga se localiza: "),
                postalCode : this.getQuestionResult("\nDigite o CEP: "),
                skills     : this.getQuestionResult(
                        "\nDigite as habilidades desejadas, separadas por virgula: "
                )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
        ]

        if (this.section.dbManager.saveNewEmployment(new Employment(params))) {
            println "\nVaga cadastrada com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar vaga", this.&createEmployment)
    }

    void readEmployments() {

        int[] employmentsIds = this.section.dbManager.getEmploymentIds(this.section.userLogged.getId())
        employmentsIds.each { id ->
            println this.section.dbManager.getEmploymentById(id as int)
        }

        if(employmentsIds.length == 0){
            println "\nNenhuma vaga encontrada"
        }

    }

    void updateEmployment() {

        println "=== Editar Vaga ==="

        Integer employmentId = this.safeToInteger(this.getQuestionResult("\nDigite o ID da vaga à ser editada: "))

        Employment originalEmployment = this.section.dbManager.getEmploymentById(employmentId)
        List<Integer> employmentsIdList = this.section.dbManager.getEmploymentIds(this.section.userLogged.getId())

        if(originalEmployment && employmentsIdList.contains(employmentId)){
            Map params = [
                    enterpriseId: this.section.userLogged.getId(),
                    name       : this.getQuestionResult("\nDigite o nome da vaga: "),
                    description: this.getQuestionResult("\nDescreva a vaga: "),
                    country    : this.getQuestionResult("\nDigite o país da vaga (Brasil): "),
                    state      : this.getQuestionResult("\nDigite o estado onde a vaga se localiza: "),
                    postalCode : this.getQuestionResult("\nDigite o CEP: "),
                    skills     : this.getQuestionResult(
                            "\nDigite as habilidades desejadas, separadas por virgula: "
                    )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
            ]

            if (this.section.dbManager.updateEmployment(originalEmployment, new Employment(params))) {
                println "\nVaga editada com sucesso."
                return
            }
        }

        this.errorMenu("Falha ao tentar editar vaga.", this.&updateEmployment)
    }

    void deleteEmployment() {

        Integer employmentId = this.safeToInteger(this.getQuestionResult("\nDigite o ID da vaga à ser deletada: "))

        Employment originalEmployment = this.section.dbManager.getEmploymentById(employmentId)
        List<Integer> employmentsIdList = this.section.dbManager.getEmploymentIds(this.section.userLogged.getId())

        if (originalEmployment && employmentsIdList.contains(employmentId)){
            this.section.dbManager.deleteEmploymentById(employmentId)
            if (!this.section.dbManager.getEmploymentById(employmentId)) {
                println "\nVaga Excluída com sucesso!"
                return
            }
        }

        this.errorMenu("Erro ao tentar excluir candidato", this.&deleteCandidate)
    }

    def RUDMenuEnterprise() {
        println """
        === Perfil da Empresa ===
        1. Visualizar Perfil
        2. Editar Perfil
        3. Excluir Perfil
        0. Voltar
        """.stripIndent()

        print "Escolha uma opção: "

        switch (this.section.input.nextLine()) {
            case "1":
                this.readEnterpriseProfile()
                break
            case "2":
                this.updateEnterprise()
                break
            case "3":
                this.deleteEnterprise()
                break
            case "0":
                println "Voltando..."
                return
            default:
                println "Opção inválida, tente novamente."
        }

        if (this.section.userLogged == null) return
        this.RUDMenuEnterprise()
    }

    void readEnterpriseProfile() {
        println this.section.userLogged
    }

    void updateEnterprise() {

        println "=== Editar Empresa ==="

        Map params = [
                email      : this.getQuestionResult("\nDigite o email: "),
                password   : this.getQuestionResult("\nDigite a senha: "),
                name       : this.getQuestionResult("\nDigite o nome da empresa: "),
                description: this.getQuestionResult("\nDescreva a empresa: "),
                cnpj       : this.getQuestionResult("\nDigite seu CNPJ: "),
                country    : this.getQuestionResult("\nDigite o país da empresa (Brasil): "),
                state      : this.getQuestionResult("\nDigite o estado onde a empresa se localiza: "),
                postalCode : this.getQuestionResult("\nDigite o CEP: ")
        ]

        if (this.section.dbManager.updateEnterprise(this.section.userLogged as NewEnterprise, new NewEnterprise(params))) {
            this.section.userLogged = this.section.dbManager.getEnterpriseById(this.section.userLogged.getId())
            println "\nPerfil editado com sucesso."
            return
        }

        this.errorMenu("Falha ao tentar editar perfil da empresa.", this.&registerEnterprise)
    }

    void deleteEnterprise() {
        this.section.dbManager.deleteEnterpriseById(this.section.userLogged.getId())
        if (!this.section.dbManager.getEnterpriseById(this.section.userLogged.getId())) {
            this.section.userLogged = null
            println "\nEmpresa Excluída com sucesso!"
            return
        }
        this.errorMenu("Erro ao tentar excluir empresa", this.&deleteEnterprise)
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
        """.stripIndent()

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

    Integer safeToInteger(String value) {
        return value?.isInteger() ? value.toInteger() : null
    }
}
