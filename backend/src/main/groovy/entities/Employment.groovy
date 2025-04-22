package entities

class Employment implements Entity {

    Integer id
    Integer enterpriseId
    String name
    String description
    String country
    String state
    String postalCode
    List<String> skills

    Employment(Map params = [:]) {
        this.id = params.id instanceof Number ? params.id as Integer : null
        this.enterpriseId = params.enterpriseId instanceof Number ? params.enterpriseId as Integer : null
        this.name = params.name as String
        this.description = params.description as String
        this.country = params.country as String
        this.state = params.state as String
        this.postalCode = params.postalCode as String
        this.skills = params.skills instanceof List ? params.skills as List<String> : []
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    Integer getEnterpriseId() {
        return enterpriseId
    }

    void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getCountry() {
        return country
    }

    void setCountry(String country) {
        this.country = country
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }

    String getPostalCode() {
        return postalCode
    }

    void setPostalCode(String postalCode) {
        this.postalCode = postalCode
    }

    List<String> getSkills() {
        return skills
    }

    void setSkills(List<String> skills) {
        this.skills = skills
    }

    @Override
    public String toString() {
        return "Vaga [" + '\n' +
                "id=" + id + '\n' +
                "enterpriseId=" + enterpriseId + '\n' +
                "name=" + name + '\n' +
                "description=" + description + '\n' +
                "country=" + country + '\n' +
                "state=" + state + '\n' +
                "postalCode=" + postalCode + '\n' +
                "skills=" + skills + '\n' +
                ']';
    }

    @Override
    boolean isAllSet() {
        return  getName() != null &&
                getDescription() != null &&
                getCountry() != null &&
                getState() != null &&
                getPostalCode() != null &&
                getSkills() != null &&
                getSkills() != []
    }
}
