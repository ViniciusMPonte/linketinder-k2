package entities


class Candidate extends SkillsList implements User {
    String name
    String email
    String country
    String state
    String cep
    String description


    String cpf
    int age

    @Override
    String toString() {
        return """

        Nome: $name
        Email: $email
        Idade: $age
        CPF: $cpf
        País: $country
        Estado: $state
        CEP: $cep
        Descrição: $description
        Habilidades: ${skills.join(", ")}

        """
    }

}
