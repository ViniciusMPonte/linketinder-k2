package managers

import java.sql.Connection
import java.sql.SQLException


class TransactionManager {

    static def executeInTransaction(Connection connection, Closure action) {
        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false
        try {
            def result = action.call()
            connection.commit()
            return result
        } catch (SQLException e) {
            connection.rollback()
            throw e // Ou tratar a exceção conforme necessário
        } finally {
            connection.autoCommit = originalAutoCommit
        }
    }
}
