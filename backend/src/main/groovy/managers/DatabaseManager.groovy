package managers

import entities.Candidate
import entities.Employment
import entities.Enterprise

import java.sql.Connection
import java.sql.SQLException

import db.Queries

import managers.TransactionManager

class DatabaseManager {

    Connection connection
    TransactionManager transactionManager

    DatabaseManager(Connection connection, TransactionManager transactionManager){
        this.connection = connection
        this.transactionManager = transactionManager
    }


    boolean deleteEmploymentById(int id) {
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



    //CRUD Skills
    boolean saveNewSkill(String skill) {
        if (this.getSkillIdByName(skill)) {
            return true
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.insertSkillsTable(skill))
            }

            connection.commit()
            return true

        } catch (SQLException e) {

            connection.rollback()
            e.printStackTrace()
            return false

        } finally {
            connection.autoCommit = originalAutoCommit
        }
    }

    //UTILS
    Integer getUserIdByEmail(String email) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectIdByEmail(email)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("id")
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

    Integer getSkillIdByName(String name) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectSkillIdByName(name)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("id")
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

    Integer getUserIdByEmailAndPassword(String email, String password) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectIdByEmailAndPassword(email, password)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("id")
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

   Integer getPostalCodeId(update) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectPostalCodeId(update.getPostalCode(), update.getState())).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("id")
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

    Integer getEmploymentId(enterpriseId, employmentName) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectEmploymentId(enterpriseId, employmentName)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("id")
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

    int[] getCandidateIds() {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectAllCandidatesIds()).withCloseable { resultSet ->
                    List<Integer> ids = []

                    while (resultSet.next()) {
                        ids.add(resultSet.getInt("user_id"))
                    }

                    return ids as int[]
                }
            }
        } catch (SQLException e) {
            e.printStackTrace()
            return [] as int[]
        }
    }

    int[] getEmploymentIds(Integer enterpriseId = null) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectAllEmploymentsIds(enterpriseId)).withCloseable { resultSet ->
                    List<Integer> ids = []

                    while (resultSet.next()) {
                        ids.add(resultSet.getInt("id"))
                    }

                    return ids as int[]
                }
            }
        } catch (SQLException e) {
            e.printStackTrace()
            return [] as int[]
        }
    }

    public boolean hasDifferences(entity1, entity2, boolean ignoreId = false) {
        return entity1.properties.any { key, value ->
            boolean shouldIgnore = key == 'class' || (ignoreId && key == 'id')
            !shouldIgnore && entity2.properties[key] != value
        }
    }
}


