package managers

import entities.Employment
import entities.Enterprise

import java.sql.Connection
import java.sql.SQLException

import entities.Candidate
import db.Queries

import managers.TransactionManager

class DatabaseManager {

    Connection connection

    DatabaseManager(Connection connection){
        this.connection = connection
    }

    //CRUD Candidates
    boolean saveNewCandidate(Candidate candidate) {
        if (!candidate.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
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

    boolean saveNewCandidateTESTE(Candidate candidate) {
        if (!candidate.isAllSet()) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.insertUsersTable(candidate))
                    if (!getPostalCodeId(candidate)) {
                        statement.execute(Queries.insertPostalCodesTable(candidate))
                    }
                    statement.execute(Queries.insertCandidatesTable(candidate))
                    candidate.getSkills().each { skill ->
                        if (!getSkillIdByName(skill)) {
                            statement.execute(Queries.insertSkillsTable(skill))
                        }
                    }
                    statement.execute(Queries.insertCandidateSkillTable(candidate))
                }
                return true
            })
        } catch (SQLException e) {
            e.printStackTrace()
            return false
        }
    }

    Candidate getCandidateById(int id) {
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
                        return new Candidate(params)
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

    boolean updateCandidate(Candidate original, Candidate updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
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

    boolean updateCandidateTESTE(Candidate original, Candidate updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.updateUsersTable(original, updated))
                    if(!this.getPostalCodeId(updated)){
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

    boolean deleteCandidateByIdTESTE(int id) {

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            return TransactionManager.executeInTransaction(connection, {
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

    //CRUD Enterprises
    boolean saveNewEnterprise(Enterprise enterprise) {
        if (!enterprise.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
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

    boolean saveNewEnterpriseTESTE(Enterprise enterprise) {
        if (!enterprise.isAllSet()) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.insertUsersTable(enterprise))
                    if(!this.getPostalCodeId(enterprise)){
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

    Enterprise getEnterpriseById(int id) {
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

    boolean updateEnterprise(Enterprise original, Enterprise updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
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

    boolean updateEnterpriseTESTE(Enterprise original, Enterprise updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
                    statement.execute(Queries.updateUsersTable(original, updated))
                    if(!this.getPostalCodeId(updated)){
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

    boolean deleteEnterpriseByIdTESTE(int id) {

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
                    this.getEmploymentIds(id).each {employmentId ->
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

    //CRUD Employments
    boolean saveNewEmployment(Employment employment) {
        if (!employment.isAllSet()) {
            return false
        }

        boolean originalAutoCommit = connection.autoCommit
        connection.autoCommit = false

        try {
            this.connection.createStatement().withCloseable { statement ->
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

    boolean saveNewEmploymentTESTE(Employment employment) {
        if (!employment.isAllSet()) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
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
                return true
            })
        } catch (SQLException e) {
            e.printStackTrace()
            return false
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
            this.connection.createStatement().withCloseable { statement ->
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

    boolean updateEmploymentTESTE(Employment original, Employment updated) {
        if (!original || !updated || !updated.isAllSet()) {
            return false
        }

        if (!this.hasDifferences(original, updated)) {
            return false
        }

        try {
            return TransactionManager.executeInTransaction(connection, {
                this.connection.createStatement().withCloseable { statement ->
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
                return true
            })
        } catch (SQLException e) {
            e.printStackTrace()
            return false
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

    boolean deleteEmploymentByIdTESTE(int id) {

        try {
            return TransactionManager.executeInTransaction(connection, {
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


