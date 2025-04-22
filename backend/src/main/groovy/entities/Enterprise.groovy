package entities

class Enterprise implements Entity {

    Integer id
    String email
    String password

    String name
    String description
    String cnpj

    String country
    String state
    String postalCode

    Enterprise(Map params = [:]) {
        this.id = params.id instanceof Number ? params.id as Integer : null
        this.email = params.email as String
        this.password = params.password as String
        this.name = params.name as String
        this.description = params.description as String
        this.cnpj = params.cnpj as String
        this.country = params.country as String
        this.state = params.state as String
        this.postalCode = params.postalCode as String
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

    String getCnpj() { return cnpj }
    void setCnpj(String cnpj) { this.cnpj = cnpj }

    String getCountry() { return country }
    void setCountry(String country) { this.country = country }

    String getState() { return state }
    void setState(String state) { this.state = state }

    String getPostalCode() { return postalCode }
    void setPostalCode(String postalCode) { this.postalCode = postalCode }

    boolean isAllSet() {
        return  getEmail() != null &&
                getPassword() != null &&
                getName() != null &&
                getDescription() != null &&
                getCnpj() != null &&
                getCountry() != null &&
                getState() != null &&
                getPostalCode() != null
    }

    @Override
    public String toString() {
        return "Empresa [" + '\n' +
                "id=" + id + '\n' +
                "email=" + email + '\n' +
                "password=" + password + '\n' +
                "name=" + name + '\n' +
                "description=" + description + '\n' +
                "cnpj=" + cnpj + '\n' +
                "country=" + country + '\n' +
                "state=" + state + '\n' +
                "postalCode=" + postalCode + '\n' +
                ']';
    }
}
