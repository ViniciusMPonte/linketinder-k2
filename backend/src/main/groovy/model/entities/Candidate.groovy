package model.entities

import java.text.ParseException
import java.text.SimpleDateFormat

class Candidate implements Entity {

    Integer id
    String email
    String password

    String name
    String description
    String cpf
    Date birthday

    String country
    String state
    String postalCode

    List<String> skills

    Candidate(Map params = [:]) {
        this.id = params.id instanceof Number ? params.id as Integer : null
        this.email = params.email as String
        this.password = params.password as String
        this.name = params.name as String
        this.description = params.description as String
        this.cpf = params.cpf as String
        this.birthday = this.parseDate(params.birthday)
        this.country = params.country as String
        this.state = params.state as String
        this.postalCode = params.postalCode as String
        this.skills = params.skills instanceof List ? params.skills as List<String> : []
    }

    Date parseDate(Object date) {
        if (date instanceof Date) {
            return date
        }
        if (!(date instanceof String)) {
            return null
        }

        String str = date.trim()
        if (!str) {
            return null
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd")
        fmt.setLenient(false)

        try {
            return fmt.parse(str)
        } catch (ParseException e) {
            return null
        }
    }

    Integer getId() { return id }

    void setId(Integer id) { this.id = id }

    String getEmail() { return email }

    void setEmail(String email) { this.email = email }

    String getPassword() { return password }

    void setPassword(String password) { this.password = password }

    String getName() { return name }

    void setName(String name) { this.name = name }

    String getDescription() { return description }

    void setDescription(String description) { this.description = description }

    String getCpf() { return cpf }

    void setCpf(String cpf) { this.cpf = cpf }

    Date getBirthday() { return birthday }

    void setBirthday(Date birthday) { this.birthday = birthday }

    String getCountry() { return country }

    void setCountry(String country) { this.country = country }

    String getState() { return state }

    void setState(String state) { this.state = state }

    String getPostalCode() { return postalCode }

    void setPostalCode(String postalCode) { this.postalCode = postalCode }

    List<String> getSkills() { return skills }

    void setSkills(List<String> skills) { this.skills = skills }


    @Override
    public String toString() {
        return "Candidato [" + '\n' +
                "id=" + id + '\n' +
                "email=" + email + '\n' +
                "password=" + password + '\n' +
                "name=" + name + '\n' +
                "description=" + description + '\n' +
                "cpf=" + cpf + '\n' +
                "birthday=" + birthday + '\n' +
                "country=" + country + '\n' +
                "state=" + state + '\n' +
                "postalCode=" + postalCode + '\n' +
                "skills=" + skills + '\n' +
                ']';
    }

    @Override
    boolean isAllSet() {
        return getEmail() != null &&
                getPassword() != null &&
                getName() != null &&
                getDescription() != null &&
                getCpf() != null &&
                getBirthday() != null &&
                getCountry() != null &&
                getState() != null &&
                getPostalCode() != null &&
                getSkills() != null &&
                getSkills() != []
    }
}
