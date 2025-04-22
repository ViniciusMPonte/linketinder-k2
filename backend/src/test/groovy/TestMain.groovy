import db.CRUDCandidateTests
import db.CRUDEmploymentTests
import db.CRUDEnterpriseTests
import model.db.TransactionManager
import model.entities.EntityFactory

static void main(String[] args) {

    def fakeTransactionManager = new TransactionManager(null) {
        @Override
        def executeInTransaction(Closure action) {
            return action.call()
        }
    }

    EntityFactory entityFactory = new EntityFactory()

    def dbCandidate = new CRUDCandidateTests(fakeTransactionManager, entityFactory)
    def dbEnterprise = new CRUDEnterpriseTests(fakeTransactionManager, entityFactory)
    def dbEmployment = new CRUDEmploymentTests(fakeTransactionManager, entityFactory)

    println((dbCandidate.save()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.save")
    println((dbCandidate.getById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.getById")
    println((dbCandidate.update()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.update")
    println((dbCandidate.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.deleteById")

    println((dbEnterprise.save()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.save")
    println((dbEnterprise.getById()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.getById")
    println((dbEnterprise.update()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.update")
    println((dbEnterprise.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.deleteById")

    println((dbEmployment.save()  ? "[PASSED]" : "[FAILED]") + " dbEmployment.save")
    println((dbEmployment.getById()  ? "[PASSED]" : "[FAILED]") + " dbEmployment.getById")
    println((dbEmployment.update()  ? "[PASSED]" : "[FAILED]") + " dbEmployment.update")
    println((dbEmployment.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbEmployment.deleteById")
}