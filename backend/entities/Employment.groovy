package entities

class Employment {

    Integer id
    Integer enterpriseId
    String name
    String description
    String postalCode
    List<String> skills

    Employment(Map params = [:]) {
        this.id = params.id instanceof Number ? params.id as Integer : null
        this.enterpriseId = params.enterpriseId instanceof Number ? params.enterpriseId as Integer : null
        this.name = params.name as String
        this.description = params.description as String
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
}
