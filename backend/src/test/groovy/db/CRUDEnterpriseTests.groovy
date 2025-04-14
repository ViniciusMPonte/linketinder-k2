package db

import entities.Enterprise
import utils.FakeConnection
import mock.EnterpriseMocks

class CRUDEnterpriseTests extends FakeConnection {

    TransactionManager fakeTransactionManager
    CRUDEnterprise dbEnterprise

    CRUDEnterpriseTests(TransactionManager fakeTransactionManager, Enterprise resultSetEnterprise = new Enterprise([:])) {
        super(resultSetEnterprise)
        this.fakeTransactionManager = fakeTransactionManager
        this.setGetterResult(resultSetEnterprise)
    }


    private void setGetterResult(Enterprise enterprise) {
        this.setResultSet(enterprise)
        this.dbEnterprise = new CRUDEnterprise(this.connect(), this.fakeTransactionManager) {
            @Override
            Integer getPostalCodeId(update) {
                return 1
            }

            @Override
            Integer getSkillIdByName(String name) {
                return 1
            }

            @Override
            int[] getEmploymentIds(Integer enterpriseId = null) {
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
        this.dbEnterprise.save(EnterpriseMocks.save.input)

        def result = this.normalize(this.sqlResult) == this.normalize(EnterpriseMocks.save.output)

        this.sqlResult = ""
        return result
    }

    boolean getById() {
        this.setGetterResult(EnterpriseMocks.getById.input)
        this.dbEnterprise.getById(EnterpriseMocks.getById.input.getId())

        def result = this.normalize(this.sqlResult) == this.normalize(EnterpriseMocks.getById.output)

        this.sqlResult = ""
        return result
    }

    boolean update() {
        this.dbEnterprise.update(EnterpriseMocks.update.input.original, EnterpriseMocks.update.input.updated)

        def result = this.normalize(this.sqlResult) == this.normalize(EnterpriseMocks.update.output)

        this.sqlResult = ""
        return result
    }

    boolean deleteById() {
        this.dbEnterprise.deleteById(EnterpriseMocks.deleteById.input)

        def result = this.normalize(this.sqlResult) == this.normalize(EnterpriseMocks.deleteById.output)

        this.sqlResult = ""
        return result
    }
}
