//Feito por Vinícius Menezes Pontes

import view.Cli
import data.CandidatesData
import data.EnterprisesData
import db.DatabaseConnection
import java.sql.ResultSet

static void main(String[] args) {

    def conn = DatabaseConnection.connect()
    if (conn) {
        println "Conexão bem-sucedida!"
        ResultSet test = conn.createStatement().executeQuery("SELECT * FROM users")

        while (test.next()) {
            println "Email: " + test.getString("email")
        }
        conn.close()
    }

    def candidatesList = CandidatesData.getInfos()
    def enterprisesList = EnterprisesData.getInfos()

    Scanner input = new Scanner(System.in)

    Cli.mainMenu(input, candidatesList, enterprisesList)

    input.close()
}

