//Feito por Vinícius Menezes Pontes
package aczg.gradleproject

import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import model.db.*
import model.api.*
import model.entities.EntityFactory
import controller.db.*
import controller.api.Server
import controller.services.SectionService

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

    JsonSlurper slurper = new JsonSlurper()
    JsonOutput jsonOutput = new JsonOutput()

    def jsonTools = [
            slurper   : slurper,
            jsonOutput: jsonOutput
    ]

    Routes routes = new Routes(section, jsonTools)
    routes.addRoutes(new EnterpriseRoutes(section, jsonTools).getRoutes())
    routes.addRoutes(new CandidateRoutes(section, jsonTools).getRoutes())
    routes.addRoutes(new EmploymentRoutes(section, jsonTools).getRoutes())

    new Server(routes.getAll()).startServer()
}

