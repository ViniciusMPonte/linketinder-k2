package view

import model.entities.Candidate

class CandidateOptions extends UtilsOptions {

    CandidateOptions(section) {
        super(section)
    }

    void register() {

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

        if (this.section.db.candidate.save(new Candidate(params))) {
            println "\nCandidato cadastrado com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar candidato", this.&register)
    }

    void login() {

        println "=== Login ==="

        def email = this.getQuestionResult("\nDigite o email: ")
        def password = this.getQuestionResult("\nDigite a senha: ")

        Integer candidateId = this.section.db.utils.getUserIdByEmailAndPassword(email, password)

        if (candidateId) {
            this.section.userLogged = this.section.db.candidate.getById(candidateId)
            return
        }

        println """
        
        Candidato não encontrado
        
        1. tentar novamente
        0. Voltar
        """.stripIndent()

        switch (this.section.input.nextLine()) {
            case "1":
                this.login()
                break
            case "0":
                println "Voltando..."
                break
            default:
                println "Opção inválida, voltando..."
        }
    }

    void readProfile() {
        println this.section.userLogged
    }

    void update() {

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

        if (this.section.db.candidate.update(this.section.userLogged as Candidate, new Candidate(params))) {
            this.section.userLogged = this.section.db.candidate.getById(this.section.userLogged.getId())
            println "\nPerfil editado com sucesso."
            return
        }

        this.errorMenu("Falha ao tentar editar perfil do candidato.", this.&registerCandidate)
    }

    void delete() {
        this.section.db.candidate.delete(this.section.userLogged.getId())
        if (!this.section.db.candidate.getById(this.section.userLogged.getId())) {
            this.section.userLogged = null
            println "\nCandidato Excluído com sucesso!"
            return
        }
        this.errorMenu("Erro ao tentar excluir candidato", this.&deleteCandidate)
    }

    void chooseEmploymentToLike() {

        int[] employmentIds = this.section.db.utils.getEmploymentIds()
        employmentIds.each { id ->
            println this.section.db.employment.getById(id as int)
        }

    }
}