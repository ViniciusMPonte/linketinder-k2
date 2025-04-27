package model.api

import com.sun.net.httpserver.HttpExchange
import controller.services.SectionService
import model.api.utils.HandleResponseParams
import model.entities.Candidate

class CandidateRoutes extends Routes {
    private Map<String, Map<String, Closure>> routes = [
            "/candidate": [
                    "GET": { HttpExchange exchange ->

                        try {
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

                        } catch (Exception e) {

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
                                    cpf        : req?.cpf,
                                    country    : req?.country,
                                    state      : req?.state,
                                    postalCode : req?.postalCode,
                                    birthday   : req?.birthday,
                                    skills     : req?.skills
                            ]

                            Candidate candidate = this.section.db.entityFactory.create("Candidate", params)
                            if (candidate.isAllSet()) {
                                if (this.section.db.candidate.save(candidate)) {
                                    this.statusCode = 201
                                } else {
                                    this.statusCode = 409
                                    throw new IllegalArgumentException(this.section.db.candidate.getMessageError())
                                }
                            } else {
                                this.statusCode = 400
                                throw new IllegalArgumentException("Campos obrigatórios ausentes.")
                            }

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode,
                                    message   : "Candidato cadastrado com sucesso."
                            ] as HandleResponseParams)

                        } catch (Exception e) {
                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode ? this.statusCode : 500,
                                    message   : "Falha ao cadastrar candidato. $e.message"
                            ] as HandleResponseParams)

                        }
                    }
            ]
    ]

    CandidateRoutes(SectionService section, jsonTools) {
        super(section, jsonTools)
    }

    Map<String, Map<String, Closure>> getRoutes() { return this.routes }
}
