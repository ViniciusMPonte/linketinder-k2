package services

import managers.DatabaseManager

class SectionService {

    public def userLogged
    Scanner input
    DatabaseManager dbManager

    SectionService(input, dbManager, userLogged = null) {
        this.input = input
        this.dbManager = dbManager
        this.userLogged = userLogged
    }

    def getUserLogged() {
        return userLogged
    }

    void setUserLogged(userLogged) {
        this.userLogged = userLogged
    }

    def getInput() {
        return input
    }

    void setInput(input) {
        this.input = input
    }
}
