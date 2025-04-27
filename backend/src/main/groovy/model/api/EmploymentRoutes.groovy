package model.api

import com.sun.net.httpserver.HttpExchange
import controller.services.SectionService
import model.api.utils.HandleResponseParams
import model.entities.Employment

class EmploymentRoutes extends Routes {
    private Map<String, Map<String, Closure>> routes = [
            "/enterprise/register-employment": [
                    POST : { HttpExchange exchange ->

                        try {

                            Object req = this.slurper.parseText(exchange.requestBody.getText("UTF-8"))

                            Map params = [
                                    enterpriseId: req?.enterpriseId,
                                    name        : req?.name,
                                    description : req?.description,
                                    country     : req?.country,
                                    state       : req?.state,
                                    postalCode  : req?.postalCode,
                                    skills      : req?.skills
                            ]

                            Employment employment = this.section.db.entityFactory.create("Employment", params)
                            if (employment.isAllSet()) {
                                if (this.section.db.employment.save(employment)) {
                                    this.statusCode = 201
                                } else {
                                    this.statusCode = 409
                                    throw new IllegalArgumentException(this.section.db.employment.getMessageError())
                                }
                            } else {
                                this.statusCode = 400
                                throw new IllegalArgumentException("Campos obrigatórios ausentes.")
                            }

                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode,
                                    message   : "vaga cadastrada com sucesso."
                            ] as HandleResponseParams)

                        } catch (Exception e) {
                            handleResponse([
                                    exchange  : exchange,
                                    statusCode: this.statusCode ? this.statusCode : 500,
                                    message   : "Falha ao cadastrar vaga. $e.message"
                            ] as HandleResponseParams)

                        }
                    }
            ]
    ]

    EmploymentRoutes(SectionService section, jsonTools) {
        super(section, jsonTools)
    }

    Map<String, Map<String, Closure>> getRoutes() { return this.routes }
}
