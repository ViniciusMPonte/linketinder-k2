package entities

import tests.mocks.UsersMock

class CandidateTest {

    static boolean setAndGetName() {
        def candidate = new Candidate()
        def name = UsersMock.getCandidateValidParameters().name

        candidate.setName(name)

        return candidate.getName() == name
    }

    static boolean setAndGetEmail() {
        def candidate = new Candidate()
        def email = UsersMock.getCandidateValidParameters().email

        candidate.setEmail(email)

        return candidate.getEmail() == email
    }

    static boolean setAndGetCountry() {
        def candidate = new Candidate()
        def country = UsersMock.getCandidateValidParameters().country

        candidate.setCountry(country)

        return candidate.getCountry() == country
    }

    static boolean setAndGetState() {
        def candidate = new Candidate()
        def state = UsersMock.getCandidateValidParameters().state

        candidate.setState(state)

        return candidate.getState() == state
    }

    static boolean setAndGetCep() {
        def candidate = new Candidate()
        def cep = UsersMock.getCandidateValidParameters().cep

        candidate.setCep(cep)

        return candidate.getCep() == cep
    }

    static boolean setAndGetDescription() {
        def candidate = new Candidate()
        def description = UsersMock.getCandidateValidParameters().description

        candidate.setDescription(description)

        return candidate.getDescription() == description
    }

    static boolean setAndGetAge() {
        def candidate = new Candidate()
        def age = UsersMock.getCandidateValidParameters().age

        candidate.setAge(age)

        return candidate.getAge() == age
    }

    static boolean setAndGetCpf() {
        def candidate = new Candidate()
        def cpf = UsersMock.getCandidateValidParameters().cpf

        candidate.setCpf(cpf)

        return candidate.getCpf() == cpf
    }

    static boolean setAndGetSkills() {
        def candidate = new Candidate()
        def skills = UsersMock.getCandidateValidParameters().skills

        candidate.setSkills(skills)

        return candidate.getSkills() == skills
    }

}
