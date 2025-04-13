import db.CRUDCandidate
import db.CRUDCandidateTests
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

    println((dbCandidate.save()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.save")
    println((dbCandidate.getById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.getById")
    println((dbCandidate.update()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.update")
    println((dbCandidate.deleteById()  ? "[PASSED]" : "[FAILED]") + " dbCandidate.deleteById")
//
//    def birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15")
//
//    Candidate candidate2 = new Candidate([
//            email      : "john.doe@example.com",
//            password   : "senhaSegura123",
//            name       : "John Doe",
//            description: "Desenvolvedor backend com 5 anos de experiência",
//            cpf        : "123.456.789-00",
//            birthday   : birthdayDate,
//            country    : 'Brasil',
//            state      : "São Paulo",
//            postalCode : "12345-678",
//            skills     : ["Java", "SQL", "Spring", "Groovy"]
//    ])
//
//
//    boolean isSaved = dbCandidate.save(candidate2)
//    if (isSaved) {
//        println("Candidato salvo com sucesso!")
//    } else {
//        println("Falha ao salvar candidato.")
//    }
//
//    def oldCandidate = new Candidate([
//            id         : 1,
//            email      : "john.doe@example.com",
//            password   : "senhaSegura123",
//            name       : "Nome Antigo",
//            description: "Desenvolvedor backend com 5 anos de experiência",
//            cpf        : "123.456.789-00",
//            birthday   : birthdayDate,
//            country    : 'Brasil',
//            state      : "São Paulo",
//            postalCode : "12345-678",
//            skills     : ["Java", "SQL", "Spring", "Groovy"]
//    ])
//
//    def newCandidate = new Candidate([
//            email      : "john.doe@example.com",
//            password   : "senhaSegura123",
//            name       : "Nome Novo",
//            description: "Desenvolvedor backend com 5 anos de experiência",
//            cpf        : "123.456.789-00",
//            birthday   : birthdayDate,
//            country    : 'Brasil',
//            state      : "São Paulo",
//            postalCode : "12345-678",
//            skills     : ["Java", "SQL", "Spring", "Groovy"]
//    ])
//
//    dbCandidate.update(oldCandidate, newCandidate)
//
//    println dbCandidate.getById(1)
//
//    if (dbCandidate.deleteById(1)) {
//        println "candidato deletado com sucesso"
//    } else {
//        println "erro ao deletar usuário"
//    }


}