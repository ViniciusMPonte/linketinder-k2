package db

import controller.db.CRUDCandidate
import model.db.TransactionManager
import model.entities.Candidate
import model.entities.EntityFactory
import utils.FakeConnection
import mock.CandidateMocks

class CRUDCandidateTests extends FakeConnection {

    TransactionManager fakeTransactionManager
    CRUDCandidate dbCandidate
    EntityFactory entityFactory

    CRUDCandidateTests(TransactionManager fakeTransactionManager, EntityFactory entityFactory, Candidate resultSetCandidate = new Candidate([:])) {
        super(resultSetCandidate)
        this.fakeTransactionManager = fakeTransactionManager
        this.setGetterResult(resultSetCandidate)
        this.entityFactory = entityFactory
    }


    private void setGetterResult(Candidate candidate) {
        this.setResultSet(candidate)
        this.dbCandidate = new CRUDCandidate(this.connect(), this.fakeTransactionManager, entityFactory) {
            @Override
            Integer getPostalCodeId(update) {
                return 1
            }

            @Override
            Integer getSkillIdByName(String name) {
                return 1
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
        this.dbCandidate.save(CandidateMocks.save.input)

        def result = this.normalize(this.sqlResult) == this.normalize(CandidateMocks.save.output)

        this.sqlResult = ""
        return result
    }

    boolean getById() {
        this.setGetterResult(CandidateMocks.getById.input)
        this.dbCandidate.getById(CandidateMocks.getById.input.getId())

        def result = this.normalize(this.sqlResult) == this.normalize(CandidateMocks.getById.output)

        this.sqlResult = ""
        return result
    }

    boolean update() {
        this.dbCandidate.update(CandidateMocks.update.input.original, CandidateMocks.update.input.updated)

        def result = this.normalize(this.sqlResult) == this.normalize(CandidateMocks.update.output)

        this.sqlResult = ""
        return result
    }

    boolean deleteById() {
        this.dbCandidate.deleteById(CandidateMocks.deleteById.input)

        def result = this.normalize(this.sqlResult) == this.normalize(CandidateMocks.deleteById.output)

        this.sqlResult = ""
        return result
    }
}
