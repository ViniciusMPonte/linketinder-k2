//Feito por Vinícius Menezes Pontes

import db.DatabaseConnection
import services.SectionService
import view.Menu
import managers.DatabaseManager


static void main(String[] args) {

    def conn = DatabaseConnection.connect()

    def dbManager = new DatabaseManager(conn)
    Scanner input = new Scanner(System.in)
    def section = new SectionService(input, dbManager)
    def menu = new Menu(section)

    menu.startMenu()

    input.close()
    conn.close()
}

