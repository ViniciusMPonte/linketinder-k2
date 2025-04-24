package model.api

import model.api.utils.HandleResponseParams
import model.api.utils.RouteHandlers
import com.sun.net.httpserver.HttpExchange

class Routes extends RouteHandlers{
    private Map<String, Map<String, Closure>> routes = [
            "/rota-teste": [
                    "GET": { HttpExchange exchange ->
                        handleResponse([
                                exchange  : exchange,
                                statusCode: 200,
                                message   : "Você acessou GET em /rota-teste."
                        ] as HandleResponseParams)
                    },
                    POST : { HttpExchange exchange ->
                        String body = exchange.requestBody.getText("UTF-8")

                        String response = "POST em /rota-teste com dados: ${body}"

                        handleResponse([
                                exchange  : exchange,
                                statusCode: 201,
                                message   : response
                        ] as HandleResponseParams)
                    }
            ]
    ]

    Map<String, Map<String, Closure>> getAll(){
        return this.routes
    }
}



