package services

class SectionService {

    public def userLogged
    Scanner input
    def db

    SectionService(input, db, userLogged = null) {
        this.input = input
        this.db = db
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
