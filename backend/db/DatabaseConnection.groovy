package db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseConnection {

    static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/db_linketinder"
        String user = "postgres"
        String password = "senhasecreta"

        try {
            Class.forName("org.postgresql.Driver")
            return DriverManager.getConnection(url, user, password)
        } catch (ClassNotFoundException | SQLException e) {
            println "Erro ao conectar: ${e.message}"
            return null
        }
    }
}
