import db.CRUDCandidate
import db.CRUDCandidateTests
import db.CRUDEnterpriseTests
import entities.Candidate
import utils.FakeConnection

import java.text.SimpleDateFormat
import db.TransactionManager

static void main(String[] args) {

    Candidate candidate = new Candidate([
            email: "john.doe@example.com",
            password: "senhaSegura123",
            name: "John Doe",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf: "123.456.789-00",
            birthday: new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15"),
            country: 'Brasil',
            state: "São Paulo",
            postalCode: "12345-678",
            skills: ["Java", "SQL", "Spring", "Groovy"]
    ])

    //def fakeConnection = new FakeConnection(candidate).connect()


    def fakeTransactionManager = new TransactionManager(null) {
        @Override
        def executeInTransaction(Closure action) {
            return action.call()
        }
    }


    def dbCandidate = new CRUDCandidateTests(fakeTransactionManager)
    def dbEnterprise = new CRUDEnterpriseTests(fakeTransactionManager)

    println((dbCandidate.save()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.save")
    println((dbCandidate.getById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.getById")
    println((dbCandidate.update()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.update")
    println((dbCandidate.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.deleteById")

    println((dbEnterprise.save()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.save")
    println((dbEnterprise.getById()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.getById")
    println((dbEnterprise.update()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.update")
    println((dbEnterprise.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbEnterprise.deleteById")






}