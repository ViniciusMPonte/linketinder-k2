package services

import entities.Candidate
import entities.Enterprise

class MatchService {

    def static enterpriseMatchList(Enterprise enterprise, List<Candidate> candidatesList) {

        def matchListCandidates = []

        candidatesList.each { candidate ->

            def matchList = enterprise.getSkills().findAll { it in candidate.getSkills() }

            matchListCandidates << [
                    "candidate"    : candidate,
                    "matchList"    : matchList,
                    "compatibility": matchList.size()
            ]
        }

        return matchListCandidates.sort { a, b ->
            b["compatibility"] <=> a["compatibility"]
        }
    }

    def static formatEnterpriseMatchList(Enterprise enterprise, List<Object> enterpriseMatchList) {


        def result = "\nEmpresa: ${enterprise.getName()}\n" +
                "Competencias desejadas: ${enterprise.skills.join(", ")}\n\n"
        enterpriseMatchList.each { candidate ->
            result += "\tCandidato(a): ${candidate["candidate"].getName()}\n" +
                    "\tCompartibilidade com a vaga: ${candidate["compatibility"]}\n"

            result += candidate["compatibility"]
                    ? "\tCompetências em comum: ${candidate["matchList"].join(", ")}\n\n"
                    : "\n\n"
        }

        return result
    }

    def static formatAllEnterprisesMatchList(List<Candidate> candidatesList, List<Enterprise> enterprisesList) {
        def result = ""

        enterprisesList.each { enterprise ->
            def enterpriseMatchList = enterpriseMatchList(enterprise, candidatesList)
            result += formatEnterpriseMatchList(enterprise, enterpriseMatchList)
        }
        return result
    }

}
