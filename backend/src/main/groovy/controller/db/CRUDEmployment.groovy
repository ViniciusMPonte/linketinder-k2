package controller.db

import model.entities.*
import model.db.Queries
import model.db.TransactionManager

import java.sql.Connection
import java.sql.SQLException


class CRUDEmployment extends DatabaseUtils {

    EntityFactory entityFactory

    CRUDEmployment(Connection connection, TransactionManager transactionManager, EntityFactory entityFactory) {
        super(connection, transactionManager)
        this.entityFactory = entityFactory
    }

    boolean save(Employment employment) {
        if (!employment.isAllSet()) {
            return false
        }

        if (!employment.getEnterpriseId()) {
            this.setMessageError("Id da empresa ausente.")
            return false
        }

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable { statement ->
                    if (!this.getPostalCodeId(employment)) {
                        statement.execute(Queries.insertPostalCodesTable(employment))
                    }
                    statement.execute(Queries.insertEmploymentsTable(employment))
                    employment.getSkills().each { skill ->
                        if (this.getSkillIdByName(skill)) {
                            return
                        }
                        statement.execute(Queries.insertSkillsTable(skill))
                    }
                    statement.execute(Queries.insertEmploymentSkillTable(employment))
                }
                return true
            })
        } catch (SQLException e) {
            this.setMessageError(e.message)
            e.printStackTrace()
            return false
        }
    }

    Employment getById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectEmploymentById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id          : resultSet.getInt("id"),
                                enterpriseId: resultSet.getInt("enterpriseId"),
                                name        : resultSet.getString("name"),
                                description : resultSet.getString("description"),
                                country     : resultSet.getString("country"),
                                state       : resultSet.getString("state"),
                                postalCode  : resultSet.getString("postalCode"),
                                skills      : resultSet.getString("skills")?.replaceAll(/[{}]/, '')?.split(',')?.toList() ?: []
                        ]
                        return this.entityFactory.create('Employment', params)

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

    boolean update(Employment original, Employment updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable { statement ->
                    if (!this.getPostalCodeId(updated)) {
                        statement.execute(Queries.updatePostalCodesTable(original, updated))
                    }
                    statement.execute(Queries.updateEmploymentsTable(original, updated))
                    statement.execute(Queries.deleteUnusedPostalCodes())
                    updated.getSkills().each { skill ->
                        if (this.getSkillIdByName(skill)) {
                            return
                        }
                        statement.execute(Queries.insertSkillsTable(skill))
                    }
                    statement.execute(Queries.updateEmploymentSkillTable(original, updated))
                    statement.execute(Queries.deleteUnusedSkills())
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
                    statement.execute(Queries.deleteEmploymentById(id))
                    statement.execute(Queries.deleteUnusedPostalCodes())
                    statement.execute(Queries.deleteUnusedSkills())
                }
                return true
            })
        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }
}
