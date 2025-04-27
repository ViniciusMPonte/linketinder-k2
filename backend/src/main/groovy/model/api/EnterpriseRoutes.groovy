package model.api

import com.sun.net.httpserver.HttpExchange
import controller.services.SectionService
import model.api.utils.HandleResponseParams
import model.entities.Candidate
import model.entities.Enterprise

class EnterpriseRoutes extends Routes {

    EnterpriseRoutes(SectionService section, jsonTools){
        super(section, jsonTools)
    }

    private Map<String, Map<String, Closure>> routes = [
            "/enterprise/register-enterprise": [
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

                            Enterprise enterprise = this.section.db.entityFactory.create("Enterprise", params)
                            if (enterprise.isAllSet()) {
                                if (this.section.db.enterprise.save(enterprise)) {
                                    this.statusCode = 201
                                } else {
                                    this.statusCode = 409
                                    throw new IllegalArgumentException(this.section.db.enterprise.getMessageError())
                                }
                            } else {
                                this.statusCode = 400
                                throw new IllegalArgumentException("Campos obrigatórios ausentes.")
                            }

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode,
                                    message   : "Empresa cadastrada com sucesso."
                            ] as HandleResponseParams)

                        } catch (Exception e) {
                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode ? this.statusCode : 500,
                                    message   : "Falha ao cadastrar empresa. $e.message"
                            ] as HandleResponseParams)

                        }
                    }
            ],
            "/enterprise/candidates-list": [
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
                    }
            ]
    ]

    Map<String, Map<String, Closure>> getRoutes(){return this.routes}
}
