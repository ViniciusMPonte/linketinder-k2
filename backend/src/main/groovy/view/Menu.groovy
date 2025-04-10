package view


import entities.Candidate
import entities.Enterprise
import services.SectionService

class Menu {

    SectionService section
    def entitiesOptions

    Menu(section, entitiesOptions) {
        this.section = section
        this.entitiesOptions = entitiesOptions
    }

    def startMenu() {
        if (this.section.userLogged instanceof Candidate) {
            this.mainMenuCandidate()
        }
        if (this.section.userLogged instanceof Enterprise) {
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
                    this.entitiesOptions.enterprise.login()
                    break
                case "2":
                    this.entitiesOptions.candidate.login()
                    break
                case "3":
                    this.entitiesOptions.enterprise.register()
                    break
                case "4":
                    this.entitiesOptions.candidate.register()
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
                this.entitiesOptions.candidate.chooseEmploymentToLike()
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
                this.entitiesOptions.candidate.readProfile()
                break
            case "2":
                this.entitiesOptions.candidate.update()
                break
            case "3":
                this.entitiesOptions.candidate.delete()
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
                this.entitiesOptions.enterprise.chooseCandidateToLike()
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
                this.entitiesOptions.enterprise.readProfile()
                break
            case "2":
                this.entitiesOptions.enterprise.update()
                break
            case "3":
                this.entitiesOptions.enterprise.delete()
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
                this.entitiesOptions.employment.readAll()
                break
            case "2":
                this.entitiesOptions.employment.create()
                break
            case "3":
                this.entitiesOptions.employment.update()
                break
            case "4":
                this.entitiesOptions.employment.delete()
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
}
