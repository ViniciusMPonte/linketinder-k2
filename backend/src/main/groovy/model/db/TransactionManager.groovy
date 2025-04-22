package model.db

import java.sql.Connection
import java.sql.SQLException


class TransactionManager {
    Connection connection

    TransactionManager(Connection connection){
        this.connection = connection
    }

    def executeInTransaction(Closure action) {
        boolean originalAutoCommit = this.connection.autoCommit
        this.connection.autoCommit = false
        try {
            def result = action.call()
            this.connection.commit()
            return result
        } catch (SQLException e) {
            this.connection.rollback()
            throw e
        } finally {
            this.connection.autoCommit = originalAutoCommit
        }
    }
}
