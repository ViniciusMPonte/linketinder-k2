package utils

import entities.Candidate

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class FakeConnection {


    public def mockResultSet

    public String sqlResult = ""


    public FakeConnection(Candidate candidate){
        this.setResultSet(candidate)
    }

    def setResultSet(Candidate candidate){
        this.mockResultSet = [
                next         : { -> true },

                getInt       : { String columnName ->
                    switch (columnName) {
                        case "id":
                            return candidate.getId() ? candidate.getId() : 1
                        default:
                            return 0
                    }
                },

                getString    : { String columnName ->
                    switch (columnName) {
                        case "email":
                            return candidate.getEmail()
                        case "password":
                            return candidate.getPassword()
                        case "name":
                            return candidate.getName()
                        case "description":
                            return candidate.getDescription()
                        case "cpf":
                            return candidate.getCpf()
                        case "country":
                            return candidate.getCountry()
                        case "state":
                            return candidate.getState()
                        case "postalCode":
                            return candidate.getPostalCode()
                        case "skills":
                            return "{" + candidate.getSkills().join(",") + "}"
                        default:
                            return null
                    }
                },

                getDate: { String columnName ->
                    if (columnName == "birthday") {
                        def date = candidate.getBirthday()
                        return date != null ? new java.sql.Date(date.getTime()) : null
                    }
                    return null
                },

                close        : { -> },

                withCloseable: { Closure c -> c.delegate = it; c.call(it) }
        ] as ResultSet
    }

    Connection connect() {
        return [
                createStatement: { ->
                    return [
                            execute: { String sql ->
                                this.sqlResult = this.sqlResult + sql
                                return true
                            },
                            executeQuery: { String sql ->
                                this.sqlResult = this.sqlResult + sql
                                return this.mockResultSet
                            },
                            close: { -> }
                    ] as Statement
                }
        ] as Connection
    }
}
