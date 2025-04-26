package model.api

import controller.services.SectionService
import groovy.json.JsonOutput
import model.api.utils.HandleResponseParams
import model.api.utils.RouteHandlers
import com.sun.net.httpserver.HttpExchange
import groovy.json.JsonSlurper
import model.entities.Candidate
import model.entities.Enterprise
import org.apache.groovy.json.internal.LazyMap
import view.EnterpriseOptions

class Routes extends RouteHandlers {

    JsonSlurper slurper
    SectionService section
    JsonOutput jsonOutput

    Routes(SectionService section, JsonSlurper slurper, JsonOutput jsonOutput) {
        this.section = section
        this.slurper = slurper
        this.jsonOutput = jsonOutput
    }

    private Map<String, Map<String, Closure>> routes = [
            "/enterprise": [
                    "GET": { HttpExchange exchange ->

                        try{
                            List<Candidate> candidates = []
                            int[] candidatesIds = this.section.db.utils.getCandidateIds()
                            candidatesIds.each { id ->
                                Candidate candidate = this.section.db.candidate.getById(id as int)
                                candidates.add(candidate)
                            }

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: 200,
                                    message   : this.jsonOutput.toJson(candidates)
                            ] as HandleResponseParams)

                        }catch (Exception e){

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: 500,
                                    message   : "$e.message"
                            ] as HandleResponseParams)

                        }
                    },
                    POST : { HttpExchange exchange ->

                        try {

                            Object req = this.slurper.parseText(exchange.requestBody.getText("UTF-8"))

                            Map params = [
                                    email      : req?.email,
                                    password   : req?.password,
                                    name       : req?.name,
                                    description: req?.description,
                                    cnpj       : req?.cnpj,
                                    country    : req?.country,
                                    state      : req?.state,
                                    postalCode : req?.postalCode
                            ]

                            Enterprise enterprise = new Enterprise(params)
                            if (enterprise.isAllSet() && this.section.db.enterprise.save(enterprise)) {
                                handleResponse([
                                        exchange  : exchange,
                                        statusCode: 201,
                                        message   : "Empresa cadastrada com sucesso."
                                ] as HandleResponseParams)
                            } else {
                                throw new IllegalArgumentException("Campos obrigatórios ausentes.")
                            }

                        } catch (Exception e) {

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: 400,
                                    message   : "Falha ao cadastrar empresa. $e.message"
                            ] as HandleResponseParams)

                        }
                    }
            ]
    ]

    Map<String, Map<String, Closure>> getAll() {
        return this.routes
    }
}