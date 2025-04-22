package view


import model.entities.Employment

class EmploymentOptions extends UtilsOptions {

    EmploymentOptions(section) {
        super(section)
    }

    void create() {

        println "=== Criar Vaga ==="

        Map params = [
                enterpriseId: this.section.userLogged.getId(),
                name        : this.getQuestionResult("\nDigite o nome da vaga: "),
                description : this.getQuestionResult("\nDescreva a vaga: "),
                country     : this.getQuestionResult("\nDigite o país da vaga (Brasil): "),
                state       : this.getQuestionResult("\nDigite o estado onde a vaga se localiza: "),
                postalCode  : this.getQuestionResult("\nDigite o CEP: "),
                skills      : this.getQuestionResult(
                        "\nDigite as habilidades desejadas, separadas por virgula: "
                )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
        ]

        if (this.section.db.employment.save(new Employment(params))) {
            println "\nVaga cadastrada com sucesso."
            return
        }

        this.errorMenu("Falha ao cadastrar vaga", this.&create)
    }

    void readAll() {

        int[] employmentsIds = this.section.db.utils.getEmploymentIds(this.section.userLogged.getId())
        employmentsIds.each { id ->
            println this.section.db.employment.getById(id as int)
        }

        if (employmentsIds.length == 0) {
            println "\nNenhuma vaga encontrada"
        }

    }

    void update() {

        println "=== Editar Vaga ==="

        Integer employmentId = this.safeToInteger(this.getQuestionResult("\nDigite o ID da vaga à ser editada: "))

        Employment originalEmployment = this.section.db.employment.getById(employmentId)
        List<Integer> employmentsIdList = this.section.db.utils.getEmploymentIds(this.section.userLogged.getId())

        if (originalEmployment && employmentsIdList.contains(employmentId)) {
            Map params = [
                    enterpriseId: this.section.userLogged.getId(),
                    name        : this.getQuestionResult("\nDigite o nome da vaga: "),
                    description : this.getQuestionResult("\nDescreva a vaga: "),
                    country     : this.getQuestionResult("\nDigite o país da vaga (Brasil): "),
                    state       : this.getQuestionResult("\nDigite o estado onde a vaga se localiza: "),
                    postalCode  : this.getQuestionResult("\nDigite o CEP: "),
                    skills      : this.getQuestionResult(
                            "\nDigite as habilidades desejadas, separadas por virgula: "
                    )?.replaceAll(/ /, '')?.split(',')?.toList() ?: []
            ]

            if (this.section.db.employment.update(originalEmployment, new Employment(params))) {
                println "\nVaga editada com sucesso."
                return
            }
        }

        this.errorMenu("Falha ao tentar editar vaga.", this.&update)
    }

    void delete() {

        Integer employmentId = this.safeToInteger(this.getQuestionResult("\nDigite o ID da vaga à ser deletada: "))

        Employment originalEmployment = this.section.db.employment.getById(employmentId)
        List<Integer> employmentsIdList = this.section.db.utils.getEmploymentIds(this.section.userLogged.getId())

        if (originalEmployment && employmentsIdList.contains(employmentId)) {
            this.section.db.employment.deleteById(employmentId)
            if (!this.section.db.employment.getById(employmentId)) {
                println "\nVaga Excluída com sucesso!"
                return
            }
        }

        this.errorMenu("Erro ao tentar excluir vaga", this.&delete)
    }
}
