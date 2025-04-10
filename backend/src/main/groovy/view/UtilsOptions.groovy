package view

import services.SectionService

class UtilsOptions {

    SectionService section

    UtilsOptions(section) {
        this.section = section
    }

    String getQuestionResult(String question) {
        print question
        return this.section.input.nextLine()
    }

    Integer safeToInteger(String value) {
        return value?.isInteger() ? value.toInteger() : null
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
}
