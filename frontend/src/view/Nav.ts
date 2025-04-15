import Components from "../dependencies/Components"
import CoreServices from "../dependencies/CoreServices"
import UIServices from "../dependencies/UIServices"

interface NavConfig {
    components: Components,
    coreServices: CoreServices
    uiServices: UIServices
}

export default class Nav {

    private readonly components
    private readonly coreServices
    private readonly uiServices

    constructor({components, coreServices, uiServices}: NavConfig) {
        this.components = components
        this.coreServices = coreServices
        this.uiServices = uiServices
    }

    insertNav() {
        const body = this.uiServices.domQuery.getBody()
        if (!body) return
        body.insertAdjacentHTML("afterbegin", this.components.nav.get())
    }

    activeNavListener() {
        const logOutBtn = this.uiServices.domQuery.getLogoutBtn()
        if (!logOutBtn) return

        logOutBtn.addEventListener("click", (event) => {
            if (this.coreServices.loginManager.loggedIn) this.coreServices.loginManager.logOut()
        })

        const resetDBBtn = this.uiServices.domQuery.getResetDBBtn()
        if (!resetDBBtn) return

        resetDBBtn.addEventListener("click", (event) => {
            if (this.coreServices.loginManager.loggedIn) this.coreServices.loginManager.logOut()
            this.coreServices.dbManager.reset()
        })
    }

}