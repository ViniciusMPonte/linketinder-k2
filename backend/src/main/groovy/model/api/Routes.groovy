package model.api

import controller.services.SectionService
import groovy.json.JsonOutput
import model.api.utils.RouteHandlers
import groovy.json.JsonSlurper

class Routes extends RouteHandlers {

    JsonSlurper slurper
    SectionService section
    JsonOutput jsonOutput
    private Map<String, Map<String, Closure>> routes

    Routes(SectionService section, jsonTools) {
        this.section = section
        this.slurper = jsonTools.slurper
        this.jsonOutput = jsonTools.jsonOutput
    }

    void addRoutes(Map<String, Map<String, Closure>> routes){
        this.routes = this.routes ? this.routes + routes : routes
    }

    Map<String, Map<String, Closure>> getAll() {
        return this.routes
    }
}