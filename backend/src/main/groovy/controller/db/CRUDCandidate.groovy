package controller.db

import model.entities.*
import model.db.Queries
import model.db.TransactionManager
import model.entities.EntityFactory

import java.sql.Connection
import java.sql.SQLException


class CRUDCandidate extends DatabaseUtils {

    EntityFactory entityFactory

    CRUDCandidate(Connection connection, TransactionManager transactionManager, EntityFactory entityFactory) {
        super(connection, transactionManager)
        this.entityFactory = entityFactory
    }


    boolean save(Candidate candidate) {
        if (!candidate.isAllSet()) {
            return false
        }

        try {
            return this.transactionManager.executeInTransaction({
                this.connection.createStatement().withCloseable({ statement ->
                    statement.execute(Queries.insertUsersTable(candidate))
                    if (!this.getPostalCodeId(candidate)) {
                        statement.execute(Queries.insertPostalCodesTable(candidate))
                    }
                    statement.execute(Queries.insertCandidatesTable(candidate))
                    candidate.getSkills().each { skill ->
                        if (!getSkillIdByName(skill)) {
                            statement.execute(Queries.insertSkillsTable(skill))
                        }
                    }
                    statement.execute(Queries.insertCandidateSkillTable(candidate))
                })
                return true
            })
        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }

    Candidate getById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectCandidateById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id         : resultSet.getInt("id"),
                                email      : resultSet.getString("email"),
                                password   : resultSet.getString("password"),
                                name       : resultSet.getString("name"),
                                description: resultSet.getString("description"),
                                cpf        : resultSet.getString("cpf"),
                                birthday   : resultSet.getDate("birthday"),
                                country    : resultSet.getString("country"),
                                state      : resultSet.getString("state"),
                                postalCode : resultSet.getString("postalCode"),
                                skills     : resultSet.getString("skills")?.replaceAll(/[{}]/, '')?.split(',')?.toList() ?: []
                        ]
                        return this.entityFactory.create('Candidate', params)
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

    boolean update(Candidate original, Candidate updated) {
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
                    statement.execute(Queries.updateCandidatesTable(original, updated))
                    statement.execute(Queries.deleteUnusedPostalCodes())
                    statement.execute(Queries.updateCandidateSkillTable(original, updated))
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
                    statement.execute(Queries.deleteCandidateById(id))
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
