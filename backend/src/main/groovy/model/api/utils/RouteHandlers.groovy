package model.api.utils

import java.nio.charset.StandardCharsets

class RouteHandlers {
    void handleResponse(HandleResponseParams args) throws IOException {
        byte[] bytes = args.message.getBytes(StandardCharsets.UTF_8)
        args.exchange.sendResponseHeaders(args.statusCode, bytes.length)
        args.exchange.responseBody.write(bytes)
    }
}
