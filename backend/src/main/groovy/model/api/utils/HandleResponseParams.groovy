package model.api.utils

import com.sun.net.httpserver.HttpExchange

class HandleResponseParams {
    HttpExchange exchange
    Integer statusCode
    String message
}
