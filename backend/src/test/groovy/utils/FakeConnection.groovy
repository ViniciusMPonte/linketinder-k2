package utils

import entities.Candidate
import entities.Enterprise

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class FakeConnection {


    public def mockResultSet

    public String sqlResult = ""


    public FakeConnection(entity){
        this.setResultSet(entity)
    }

    def setResultSet(entity){
        this.mockResultSet = [
                next         : { -> true },

                getInt       : { String columnName ->
                    switch (columnName) {
                        case "id":
                            return entity.getId() ? entity.getId() : 1
                        default:
                            return 0
                    }
                },

                getString    : { String columnName ->
                    switch (columnName) {
                        case "email":
                            return entity.getEmail()
                        case "password":
                            return entity.getPassword()
                        case "name":
                            return entity.getName()
                        case "description":
                            return entity.getDescription()
                        case "cpf":
                            return entity.getCpf()
                        case "country":
                            return entity.getCountry()
                        case "state":
                            return entity.getState()
                        case "postalCode":
                            return entity.getPostalCode()
                        case "skills":
                            return "{" + entity.getSkills().join(",") + "}"
                        default:
                            return null
                    }
                },

                getDate: { String columnName ->
                    if (columnName == "birthday") {
                        def date = entity.getBirthday()
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
