package entities

class NewCandidate {

    Integer id
    String email
    String password

    String name
    String description
    String cpf
    Date birthDate

    String country
    String state
    String postalCode

    List<String> skills

    NewCandidate(Map params = [:]) {
        this.id = params.id instanceof Number ? params.id as Integer : null
        this.email = params.email as String
        this.password = params.password as String
        this.name = params.name as String
        this.description = params.description as String
        this.cpf = params.cpf as String
        this.birthDate = parseDate(params.birthDate)
        this.country = params.country as String
        this.state = params.state as String
        this.postalCode = params.postalCode as String
        this.skills = params.skills instanceof List ? params.skills as List<String> : []
    }

    private static Date parseDate(Object date) {
        if (date instanceof Date) {
            return date
        } else if (date instanceof String) {
            try {
                return date.toString() ? Date.parse("yyyy-MM-dd", date.toString()) : null
            } catch (Exception e) {
                return null
            }
        }
        return null
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

    Date getBirthDate() { return birthDate }
    void setBirthDate(Date birthDate) { this.birthDate = birthDate }

    String getCountry() { return country }
    void setCountry(String country) { this.country = country }

    String getState() { return state }
    void setState(String state) { this.state = state }

    String getpostalCode() { return postalCode }
    void setpostalCode(String postalCode) { this.postalCode = postalCode }

    List<String> getSkills() { return skills }
    void setSkills(List<String> skills) { this.skills = skills }
}
