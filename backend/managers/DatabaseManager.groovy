package managers

import entities.Employment
import entities.NewEnterprise

import java.sql.Connection
import java.sql.SQLException

import entities.NewCandidate
import db.Queries

class DatabaseManager {

    Connection connection

    DatabaseManager(Connection connection){
        this.connection = connection
    }

    //CRUD Candidates
    boolean saveNewCandidate(NewCandidate candidate) {
        if (!candidate.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.insertUsersTable(candidate))
                if(!this.getPostalCodeId(candidate)){
                    statement.execute(Queries.insertPostalCodesTable(candidate))
                }
                statement.execute(Queries.insertCandidatesTable(candidate))
                candidate.getSkills().each { skill ->
                    if (this.getSkillIdByName(skill)) {
                        return
                    }
                    statement.execute(Queries.insertSkillsTable(skill))
                }
                statement.execute(Queries.insertCandidateSkillTable(candidate))
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

    NewCandidate getCandidateById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectCandidateById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id: resultSet.getInt("id"),
                                email: resultSet.getString("email"),
                                password: resultSet.getString("password"),
                                name: resultSet.getString("name"),
                                description: resultSet.getString("description"),
                                cpf: resultSet.getString("cpf"),
                                birthday: resultSet.getDate("birthday"),
                                country: resultSet.getString("country"),
                                state: resultSet.getString("state"),
                                postalCode: resultSet.getString("postalCode"),
                                skills: resultSet.getString("skills")?.replaceAll(/[{}]/, '')?.split(',')?.toList() ?: []
                        ]
                        return new NewCandidate(params)
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

    boolean updateCandidate(NewCandidate original, NewCandidate updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.updateUsersTable(original, updated))
                if(!this.getPostalCodeId(updated)){
                    statement.execute(Queries.updatePostalCodesTable(original, updated))
                }
                statement.execute(Queries.updateCandidatesTable(original, updated))
                statement.execute(Queries.deleteUnusedPostalCodes())
                statement.execute(Queries.updateCandidateSkillTable(original, updated))
                statement.execute(Queries.deleteUnusedSkills())
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

    boolean deleteCandidateById(int id) {

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.deleteCandidateById(id))
                statement.execute(Queries.deleteUnusedPostalCodes())
                statement.execute(Queries.deleteUnusedSkills())
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

    //CRUD Enterprises
    boolean saveNewEnterprise(NewEnterprise enterprise) {
        if (!enterprise.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.insertUsersTable(enterprise))
                if(!this.getPostalCodeId(enterprise)){
                    statement.execute(Queries.insertPostalCodesTable(enterprise))
                }
                statement.execute(Queries.insertEnterprisesTable(enterprise))
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

    NewEnterprise getEnterpriseById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectEnterpriseById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id: resultSet.getInt("id"),
                                email: resultSet.getString("email"),
                                password: resultSet.getString("password"),
                                name: resultSet.getString("name"),
                                description: resultSet.getString("description"),
                                cnpj: resultSet.getString("cnpj"),
                                country: resultSet.getString("country"),
                                state: resultSet.getString("state"),
                                postalCode: resultSet.getString("postalCode")
                        ]
                        return new NewEnterprise(params)
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

    boolean updateEnterprise(NewEnterprise original, NewEnterprise updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.updateUsersTable(original, updated))
                if(!this.getPostalCodeId(updated)){
                    statement.execute(Queries.updatePostalCodesTable(original, updated))
                }
                statement.execute(Queries.updateEnterprisesTable(original, updated))
                statement.execute(Queries.deleteUnusedPostalCodes())
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

    boolean deleteEnterpriseById(int id) {

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
                this.getEmploymentIds(id).each {employmentId ->
                    this.deleteEmploymentById(employmentId as Integer)
                }
                statement.execute(Queries.deleteEnterpriseById(id))
                statement.execute(Queries.deleteUnusedPostalCodes())
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

    //CRUD Employments
    boolean saveNewEmployment(Employment employment) {
        if (!employment.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                if(!this.getPostalCodeId(employment)){
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

    Employment getEmploymentById(int id) {
        try {
            return this.connection.createStatement().withCloseable { statement ->
                statement.executeQuery(Queries.selectEmploymentById(id)).withCloseable { resultSet ->
                    if (resultSet.next()) {
                        Map params = [
                                id: resultSet.getInt("id"),
                                enterpriseId: resultSet.getInt("enterpriseId"),
                                name: resultSet.getString("name"),
                                description: resultSet.getString("description"),
                                country: resultSet.getString("country"),
                                state: resultSet.getString("state"),
                                postalCode: resultSet.getString("postalCode"),
                                skills: resultSet.getString("skills")?.replaceAll(/[{}]/, '')?.split(',')?.toList() ?: []
                        ]
                        return new Employment(params)
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

    boolean updateEmployment(Employment original, Employment updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
                if(!this.getPostalCodeId(updated)){
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

    boolean deleteEmploymentById(int id) {

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
                statement.execute(Queries.deleteEmploymentById(id))
                statement.execute(Queries.deleteUnusedPostalCodes())
                statement.execute(Queries.deleteUnusedSkills())
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

    //CRUD Skills
    boolean saveNewSkill(String skill) {
        if (this.getSkillIdByName(skill)) {
            return true
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            connection.createStatement().withCloseable { statement ->
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

    boolean hasDifferences(entity1, entity2, boolean ignoreId = false) {
        return entity1.properties.any { key, value ->
            boolean shouldIgnore = key == 'class' || (ignoreId && key == 'id')
            !shouldIgnore && entity2.properties[key] != value
        }
    }
}


