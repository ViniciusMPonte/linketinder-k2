import db.CRUDCandidate
import entities.Candidate
import java.text.SimpleDateFormat
import db.TransactionManager

static void main(String[] args) {


    def mockResultSet = [
            next         : { -> true },

            getInt       : { String columnName ->
                switch (columnName) {
                    case "id":
                        return 1
                    default:
                        return 0
                }
            },

            getString    : { String columnName ->
                switch (columnName) {
                    case "email":
                        return "email@exemplo.com"
                    case "password":
                        return "senha123"
                    case "name":
                        return "João da Silva"
                    case "description":
                        return "Desenvolvedor"
                    case "cpf":
                        return "123.456.789-00"
                    case "country":
                        return "Brasil"
                    case "state":
                        return "São Paulo"
                    case "postalCode":
                        return "01000-000"
                    case "skills":
                        return "{Java,Groovy}" // formato com chaves para simular o replaceAll
                    default:
                        return null
                }
            },

            getDate      : { String columnName ->
                if (columnName == "birthday") {
                    return java.sql.Date.valueOf("1990-01-01")
                }
                return null
            },

            close        : { -> println "ResultSet fechado" },

            withCloseable: { Closure c -> c.delegate = it; c.call(it) }
    ] as java.sql.ResultSet


    def mockConnection = [
            createStatement: { ->
                return [
                        execute     : { String sql ->
                            println "Executando SQL: $sql"
                            return true
                        },

                        executeQuery: { String sql ->
                            println "Executando SQL: $sql"
                            return mockResultSet
                        },

                        close       : { -> }
                ] as java.sql.Statement
            }
    ] as java.sql.Connection


    def mockTransactionManager = new TransactionManager(null) {
        @Override
        def executeInTransaction(Closure action) {
            return action.call()
        }
    }


    def dbCandidate = new CRUDCandidate(mockConnection, mockTransactionManager) {
        @Override
        Integer getPostalCodeId(update) {
            return 1
        }

        @Override
        Integer getSkillIdByName(String name) {
            return 1
        }
    }

    def birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15")

    Candidate candidate = new Candidate([
            email      : "john.doe@example.com",
            password   : "senhaSegura123",
            name       : "John Doe",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf        : "123.456.789-00",
            birthday   : birthdayDate,
            country    : 'Brasil',
            state      : "São Paulo",
            postalCode : "12345-678",
            skills     : ["Java", "SQL", "Spring", "Groovy"]
    ])


    boolean isSaved = dbCandidate.save(candidate)
    if (isSaved) {
        println("Candidato salvo com sucesso!")
    } else {
        println("Falha ao salvar candidato.")
    }

    def oldCandidate = new Candidate([
            id         : 1,
            email      : "john.doe@example.com",
            password   : "senhaSegura123",
            name       : "Nome Antigo",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf        : "123.456.789-00",
            birthday   : birthdayDate,
            country    : 'Brasil',
            state      : "São Paulo",
            postalCode : "12345-678",
            skills     : ["Java", "SQL", "Spring", "Groovy"]
    ])

    def newCandidate = new Candidate([
            email      : "john.doe@example.com",
            password   : "senhaSegura123",
            name       : "Nome Novo",
            description: "Desenvolvedor backend com 5 anos de experiência",
            cpf        : "123.456.789-00",
            birthday   : birthdayDate,
            country    : 'Brasil',
            state      : "São Paulo",
            postalCode : "12345-678",
            skills     : ["Java", "SQL", "Spring", "Groovy"]
    ])

    dbCandidate.update(oldCandidate, newCandidate)

    println dbCandidate.getById(1)

    if (dbCandidate.deleteById(1)) {
        println "candidato deletado com sucesso"
    } else {
        println "erro ao deletar usuário"
    }


}