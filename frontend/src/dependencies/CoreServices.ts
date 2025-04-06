import DatabaseManager from "../services/DatabaseManager"
import LoginManager from "../services/LoginManager"

interface CoreServicesConfig {
    dbManager: DatabaseManager
    loginManager: LoginManager
}

export default class CoreServices {

    public readonly dbManager
    public readonly loginManager

    constructor({
                    dbManager,
                    loginManager
                }:CoreServicesConfig) {
        this.dbManager = dbManager
        this.loginManager = loginManager
    }
}

