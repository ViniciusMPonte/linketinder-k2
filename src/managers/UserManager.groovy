package managers

import entities.Candidate
import entities.Enterprise
import utils.GenericUtils

class UserManager {

    static void registerEnterprise(Scanner input, List<Enterprise> enterprisesList) {

        Enterprise newEnterprise = new Enterprise()

        println "=== Cadastro de Empresa ==="

        print "\nDigite o nome da empresa: "
        newEnterprise.setName(input.nextLine())

        print "\nDigite o e-mail da empresa: "
        newEnterprise.setEmail(input.nextLine())

        print "\nDigite o país da empresa: "
        newEnterprise.setCountry(input.nextLine())

        print "\nDigite o estado da empresa: "
        newEnterprise.setState(input.nextLine())

        print "\nDigite o CEP da empresa: "
        newEnterprise.setCep(input.nextLine())

        print "\nDigite a descrição da empresa: "
        newEnterprise.setDescription(input.nextLine())

        print "\nDigite o CNPJ da empresa: "
        newEnterprise.setCnpj(input.nextLine())

        def differenceBetweenSkillsList
        while (true) {
            println "\nCompetências disponíveis: ${newEnterprise.getVALID_SKILLS().join(", ")}"

            print "Digite as competências que a empresa procura (separadas por vírgula): "

            String skillsInput = input.nextLine()

            List<String> skills = skillsInput.split(",")*.trim()

            differenceBetweenSkillsList = GenericUtils.getDifferenceBetweenArrays(skills, newEnterprise.getVALID_SKILLS())
            if (differenceBetweenSkillsList) {
                println "Erro! As seguintes competências não são validas: ${differenceBetweenSkillsList.join(", ")}\n\n"
            } else {
                newEnterprise.setSkills(skills)
                break
            }
        }

        enterprisesList << newEnterprise
        println "\nEmpresa cadastrada com sucesso!\n\n"
    }

    static void registerCandidate(Scanner input, List<Candidate> candidatesList) {

        Candidate newCandidate = new Candidate()

        println "=== Cadastro de Candidato ==="

        print "\nDigite o nome do Candidato: "
        newCandidate.setName(input.nextLine())

        print "\nDigite o e-mail do candidato: "
        newCandidate.setEmail(input.nextLine())

        while (true) {
            print "\nDigite a idade do candidato: "
            try {
                newCandidate.setAge(input.nextLine().toInteger())
                break
            } catch (NumberFormatException e) {
                println "Valor inválido. Por favor, digite um número inteiro."
            }
        }

        print "\nDigite o país do candidato: "
        newCandidate.setCountry(input.nextLine())

        print "\nDigite o estado do candidato: "
        newCandidate.setState(input.nextLine())

        print "\nDigite o CEP do candidato: "
        newCandidate.setCep(input.nextLine())

        print "\nDigite a descrição do candidato: "
        newCandidate.setDescription(input.nextLine())

        print "\nDigite o CPF do candidato: "
        newCandidate.setCpf(input.nextLine())

        def differenceBetweenSkillsList
        while (true) {
            println "\nCompetências disponíveis: ${newCandidate.getVALID_SKILLS().join(", ")}"

            print "Digite as competências do candidato (separadas por vírgula): "

            String skillsInput = input.nextLine()
            List<String> skills = skillsInput.split(",")*.trim()

            differenceBetweenSkillsList = GenericUtils.getDifferenceBetweenArrays(skills, newCandidate.getVALID_SKILLS())
            if (differenceBetweenSkillsList) {
                println "Erro! As seguintes competências não são validas: ${differenceBetweenSkillsList.join(", ")}\n\n"
            } else {
                newCandidate.setSkills(skills)
                break
            }
        }

        candidatesList << newCandidate
        println "\nCandidato cadastrado com sucesso!\n\n"
    }

    static def removeCandidate(Scanner input, List<Candidate> candidatesList){
        while (true){
            print "Digite o nome do candidato de deseja remover (ou 0 para voltar): "
            def name = input.nextLine()
            if (name == "0") break
            if (candidatesList.removeIf { it.name == name }){
                println "Candidato removido com sucesso!"
                break
            } else {
                println "Nome não encontrado, tente novamente"
            }
        }
    }

    static def removeEnterprise(Scanner input, List<Enterprise> enterprisesList){
        while (true){
            print "Digite o nome da empresa de deseja remover (ou 0 para voltar): "
            def name = input.nextLine()
            if (name == "0") break
            if (enterprisesList.removeIf { it.name == name }){
                println "Empresa removida com sucesso!"
                break
            } else {
                println "Nome não encontrado, tente novamente"
            }
        }
    }

}
