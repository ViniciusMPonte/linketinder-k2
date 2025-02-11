package entities

class Enterprise extends SkillsList implements User {
    String name
    String email
    String country
    String state
    String cep
    String description

    String cnpj

    @Override
    String toString() {
        return """

        Empresa: $name
        Email: $email
        CNPJ: $cnpj
        País: $country
        Estado: $state
        CEP: $cep
        Descrição: $description
        Habilidades: ${skills.join(", ")}

        """
    }
}
