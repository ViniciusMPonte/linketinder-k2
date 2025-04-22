package view

import model.entities.Enterprise

class EnterpriseOptions extends UtilsOptions {

    EnterpriseOptions(section) {
        super(section)
    }

    void register() {

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

        if (this.section.db.enterprise.save(new Enterprise(params))) {
            println "\nEmpresa cadastrada com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar empresa", this.&register)
    }

    void login() {

        println "=== Login ==="

        def email = this.getQuestionResult("\nDigite o email: ")
        def password = this.getQuestionResult("\nDigite a senha: ")

        Integer enterpriseId = this.section.db.utils.getUserIdByEmailAndPassword(email, password)

        if (enterpriseId) {
            this.section.userLogged = this.section.db.enterprise.getById(enterpriseId)
            return
        }

        this.errorMenu("Empresa não encontrada", this.&login)
    }

    void readProfile() {
        println this.section.userLogged
    }

    void update() {

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

        if (this.section.db.enterprise.update(this.section.userLogged as Enterprise, new Enterprise(params))) {
            this.section.userLogged = this.section.db.enterprise.getById(this.section.userLogged.getId())
            println "\nPerfil editado com sucesso."
            return
        }

        this.errorMenu("Falha ao tentar editar perfil da empresa.", this.&register)
    }

    void delete() {
        this.section.db.enterprise.deleteById(this.section.userLogged.getId())
        if (!this.section.db.enterprise.getById(this.section.userLogged.getId())) {
            this.section.userLogged = null
            println "\nEmpresa Excluída com sucesso!"
            return
        }
        this.errorMenu("Erro ao tentar excluir empresa", this.&delete)
    }

    void chooseCandidateToLike() {

        int[] candidatesIds = this.section.db.utils.getCandidateIds()
        candidatesIds.each { id ->
            println this.section.db.candidate.getById(id as int)
        }

    }
}