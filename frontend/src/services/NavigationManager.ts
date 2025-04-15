import {coreServices, uiServices, components, contentBuilder} from "../main"
import Nav from "../view/Nav"
import UIServices from "../dependencies/UIServices"
import ContentBuilder from "../view/Dynamic/ContentBuilder"

interface NavigationManagerConfig {
    uiServices: UIServices
    contentBuilder: ContentBuilder
}

const dependencies = {coreServices, uiServices, components, contentBuilder}
const nav = new Nav(dependencies)

export default class NavigationManager {

    uiServices
    contentBuilder

    constructor({
                    uiServices,
                    contentBuilder
                }:NavigationManagerConfig) {

        this.uiServices = uiServices
        this.contentBuilder = contentBuilder
    }

    router(): void {

        nav.insertNav()
        nav.activeNavListener()

        const currentPath = window.location.pathname
        const routeConfig = this.uiServices.redirect.routes[currentPath]
        const builderConfig = this.contentBuilder.routes[currentPath]

        if (routeConfig) {
            routeConfig.redirectMethod()
            if (builderConfig.action) builderConfig.action()
        } else {
            console.log("Rota não encontrada: Página 404")
        }
    }
}