//Feito por Vinícius Menezes Pontes
package aczg.gradleproject

import services.SectionService
import view.CandidateOptions
import view.EmploymentOptions
import view.EnterpriseOptions

import view.Menu
import db.*

static void main(String[] args) {

    def conn = DatabaseConnection.connect()
    def transactionManager = new TransactionManager(conn)

    def dbCandidate = new CRUDCandidate(conn, transactionManager)
    def dbEnterprise = new CRUDEnterprise(conn, transactionManager)
    def dbEmployment = new CRUDEmployment(conn, transactionManager)
    def dbUtils = new DatabaseUtils(conn, transactionManager)
    Scanner input = new Scanner(System.in)
    def db = [
            candidate : dbCandidate,
            enterprise: dbEnterprise,
            employment: dbEmployment,
            utils: dbUtils
    ]
    def section = new SectionService(input, db)
    def candidateOptions = new CandidateOptions(section)
    def enterpriseOptions = new EnterpriseOptions(section)
    def employmentOptions = new EmploymentOptions(section)
    def menu = new Menu(section, [
            candidate: candidateOptions,
            enterprise: enterpriseOptions,
            employment: employmentOptions
    ])

    menu.startMenu()

    input.close()
    conn.close()
}

