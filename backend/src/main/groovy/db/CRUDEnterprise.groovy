package db

import entities.Enterprise

import java.sql.Connection
import java.sql.SQLException


class CRUDEnterprise extends DatabaseUtils{

    CRUDEnterprise (Connection connection, TransactionManager transactionManager) {
        super(connection, transactionManager)
    }

    boolean save(Enterprise enterprise) {
        if (!enterprise.isAllSet()) {
            return false
        }

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.insertUsersTable(enterprise))
                    if (!this.getPostalCodeId(enterprise)) {
                        statement.execute(Queries.insertPostalCodesTable(enterprise))
                    }
                    statement.execute(Queries.insertEnterprisesTable(enterprise))
                }
                return true
            })

        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }

    Enterprise getById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectEnterpriseById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id         : resultSet.getInt("id"),
                                email      : resultSet.getString("email"),
                                password   : resultSet.getString("password"),
                                name       : resultSet.getString("name"),
                                description: resultSet.getString("description"),
                                cnpj       : resultSet.getString("cnpj"),
                                country    : resultSet.getString("country"),
                                state      : resultSet.getString("state"),
                                postalCode : resultSet.getString("postalCode")
                        ]
                        return new Enterprise(params)
                    } else {
                        return null
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace()
            return null
        }
    }

    boolean update(Enterprise original, Enterprise updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.updateUsersTable(original, updated))
                    if (!this.getPostalCodeId(updated)) {
                        statement.execute(Queries.updatePostalCodesTable(original, updated))
                    }
                    statement.execute(Queries.updateEnterprisesTable(original, updated))
                    statement.execute(Queries.deleteUnusedPostalCodes())
                }
                return true
            })

        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }

    boolean deleteById(int id) {

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable { statement ->
                    this.getEmploymentIds(id).each { employmentId ->
                        this.deleteEmploymentById(employmentId as Integer)
                    }
                    statement.execute(Queries.deleteEnterpriseById(id))
                    statement.execute(Queries.deleteUnusedPostalCodes())
                }
                return true
            })

        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }
}
