package entities

import tests.mocks.UsersMock

class EnterpriseTest {

    static boolean setAndGetName() {
        def enterprise = new Enterprise()
        def name = UsersMock.getEnterpriseValidParameters().name

        enterprise.setName(name)

        return enterprise.getName() == name
    }

    static boolean setAndGetEmail() {
        def enterprise = new Enterprise()
        def email = UsersMock.getEnterpriseValidParameters().email

        enterprise.setEmail(email)

        return enterprise.getEmail() == email
    }

    static boolean setAndGetCountry() {
        def enterprise = new Enterprise()
        def country = UsersMock.getEnterpriseValidParameters().country

        enterprise.setCountry(country)

        return enterprise.getCountry() == country
    }

    static boolean setAndGetState() {
        def enterprise = new Enterprise()
        def state = UsersMock.getEnterpriseValidParameters().state

        enterprise.setState(state)

        return enterprise.getState() == state
    }

    static boolean setAndGetCep() {
        def enterprise = new Enterprise()
        def cep = UsersMock.getEnterpriseValidParameters().cep

        enterprise.setCep(cep)

        return enterprise.getCep() == cep
    }

    static boolean setAndGetDescription() {
        def enterprise = new Enterprise()
        def description = UsersMock.getEnterpriseValidParameters().description

        enterprise.setDescription(description)

        return enterprise.getDescription() == description
    }

    static boolean setAndGetCnpj() {
        def enterprise = new Enterprise()
        def cnpj = UsersMock.getEnterpriseValidParameters().cnpj

        enterprise.setCnpj(cnpj)

        return enterprise.getCnpj() == cnpj
    }

    static boolean setAndGetSkills() {
        def enterprise = new Enterprise()
        def skills = UsersMock.getEnterpriseValidParameters().skills

        enterprise.setSkills(skills)

        return enterprise.getSkills() == skills
    }

}
