package model.api

import controller.services.SectionService
import groovy.json.JsonOutput
import model.api.utils.HandleResponseParams

import groovy.json.JsonSlurper

import java.nio.charset.StandardCharsets

class Routes {

    JsonSlurper slurper
    SectionService section
    JsonOutput jsonOutput
    Integer statusCode
    private Map<String, Map<String, Closure>> routes

    Routes(SectionService section, jsonTools) {
        this.section = section
        this.slurper = jsonTools.slurper
        this.jsonOutput = jsonTools.jsonOutput
    }

    void handleResponse(HandleResponseParams args) throws IOException {
        byte[] bytes = args.message.getBytes(StandardCharsets.UTF_8)
        args.exchange.sendResponseHeaders(args.statusCode, bytes.length)
        args.exchange.responseBody.write(bytes)
    }

    void addRoutes(Map<String, Map<String, Closure>> routes){
        this.routes = this.routes ? this.routes + routes : routes
    }

    Map<String, Map<String, Closure>> getAll() {
        return this.routes
    }
}