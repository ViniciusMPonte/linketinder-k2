package controller.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

class Server {

    private final int port = 8080
    private final int backlog = 0
    private final Map<String, Map<String, Closure>> routes

    Server(Map<String, Map<String, Closure>> routes) {
        this.routes = routes
    }

    void startServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), backlog)

        this.startRoutes(server)

        server.start()
        println "Servidor rodando em portas e rotas:"
        routes.keySet().each { println "  http://localhost:${port}$it" }
    }

    private void startRoutes(server) {
        routes.each { String path, Map<String, Closure> methodsMap ->
            server.createContext(path, new HttpHandler() {

                @Override
                void handle(HttpExchange exchange) throws IOException {

                    String method = exchange.requestMethod
                    exchange.responseHeaders.add("Content-Type", "text/plain; charset=UTF-8")

                    Closure handler = methodsMap[method]
                    if (handler) {
                        handler(exchange)
                    } else {
                        exchange.sendResponseHeaders(405, -1)
                    }
                    exchange.close()
                }
            })
        }
    }

}





