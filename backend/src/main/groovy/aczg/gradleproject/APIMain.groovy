//Feito por Vinícius Menezes Pontes
package aczg.gradleproject

import controller.api.Server
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import model.api.EnterpriseRoutes
import model.api.Routes
import model.api.CandidateRoutes
import model.entities.EntityFactory
import controller.services.SectionService
import view.CandidateOptions
import view.EmploymentOptions
import view.EnterpriseOptions

import view.Menu
import model.db.*
import controller.db.*

static void main(String[] args) {

    def conn = DatabaseConnection.connect()
    def transactionManager = new TransactionManager(conn)

    def entityFactory = new EntityFactory()
    def dbCandidate = new CRUDCandidate(conn, transactionManager, entityFactory)
    def dbEnterprise = new CRUDEnterprise(conn, transactionManager, entityFactory)
    def dbEmployment = new CRUDEmployment(conn, transactionManager, entityFactory)
    def dbUtils = new DatabaseUtils(conn, transactionManager)
    Scanner input = new Scanner(System.in)
    def db = [
            candidate    : dbCandidate,
            enterprise   : dbEnterprise,
            employment   : dbEmployment,
            utils        : dbUtils,
            entityFactory: entityFactory
    ]
    def section = new SectionService(input, db)
    def candidateOptions = new CandidateOptions(section)
    def enterpriseOptions = new EnterpriseOptions(section)
    def employmentOptions = new EmploymentOptions(section)
    def menu = new Menu(section, [
            candidate : candidateOptions,
            enterprise: enterpriseOptions,
            employment: employmentOptions
    ])

    JsonSlurper slurper = new JsonSlurper()
    JsonOutput jsonOutput = new JsonOutput()

    def jsonTools = [
            slurper   : slurper,
            jsonOutput: jsonOutput
    ]

    Routes routes = new Routes(section, jsonTools)
    routes.addRoutes(new EnterpriseRoutes(section, jsonTools).getRoutes())
    routes.addRoutes(new CandidateRoutes(section, jsonTools).getRoutes())

    new Server(routes.getAll()).startServer()

//    input.close()
//    conn.close()
}

