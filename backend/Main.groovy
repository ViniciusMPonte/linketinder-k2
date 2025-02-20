//Feito por Vinícius Menezes Pontes

import view.Cli
import data.CandidatesData
import data.EnterprisesData

static void main(String[] args) {
  def candidatesList = CandidatesData.getInfos()
  def enterprisesList = EnterprisesData.getInfos()

  Scanner input = new Scanner(System.in)

  Cli.mainMenu(input, candidatesList, enterprisesList)

  input.close()
}

