package db

import controller.db.CRUDEmployment
import model.db.TransactionManager
import model.entities.Employment
import model.entities.EntityFactory
import utils.FakeConnection
import mock.EmploymentMocks

class CRUDEmploymentTests extends FakeConnection {

    TransactionManager fakeTransactionManager
    CRUDEmployment dbEmployment
    EntityFactory entityFactory

    CRUDEmploymentTests(TransactionManager fakeTransactionManager, EntityFactory entityFactory, Employment resultSetEmployment = new Employment([:])) {
        super(resultSetEmployment)
        this.fakeTransactionManager = fakeTransactionManager
        this.setGetterResult(resultSetEmployment)
        this.entityFactory = entityFactory
    }


    private void setGetterResult(Employment employment) {
        this.setResultSet(employment)
        this.dbEmployment = new CRUDEmployment(this.connect(), this.fakeTransactionManager, entityFactory) {
            @Override
            Integer getPostalCodeId(update) {
                return 1
            }

            @Override
            Integer getSkillIdByName(String name) {
                return 1
            }

            @Override
            int[] getEmploymentIds(Integer employmentId = null) {
                return [1, 2]
            }
        }
    }

    String normalize(str) {
        return str.replaceAll(/[\n\r\t]+/, "")
                .replaceAll(/ */, "")
                .trim()
    }

    //tests
    boolean save() {
        this.dbEmployment.save(EmploymentMocks.save.input)

        def result = this.normalize(this.sqlResult) == this.normalize(EmploymentMocks.save.output)

        this.sqlResult = ""
        return result
    }

    boolean getById() {
        this.setGetterResult(EmploymentMocks.getById.input)
        this.dbEmployment.getById(EmploymentMocks.getById.input.getId())

        def result = this.normalize(this.sqlResult) == this.normalize(EmploymentMocks.getById.output)

        this.sqlResult = ""
        return result
    }

    boolean update() {
        this.dbEmployment.update(EmploymentMocks.update.input.original, EmploymentMocks.update.input.updated)

        def result = this.normalize(this.sqlResult) == this.normalize(EmploymentMocks.update.output)

        this.sqlResult = ""
        return result
    }

    boolean deleteById() {
        this.dbEmployment.deleteById(EmploymentMocks.deleteById.input)

        def result = this.normalize(this.sqlResult) == this.normalize(EmploymentMocks.deleteById.output)

        this.sqlResult = ""
        return result
    }
}
