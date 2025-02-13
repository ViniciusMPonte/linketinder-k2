package tests

import entities.CandidateTest
import entities.EnterpriseTest
import managers.UserManagerTest


static void main(String[] args) {

    //Candidate Setters and Getters
    println CandidateTest.setAndGetName() ? "passou" : "falhou"
    println CandidateTest.setAndGetEmail() ? "passou" : "falhou"
    println CandidateTest.setAndGetCountry() ? "passou" : "falhou"
    println CandidateTest.setAndGetState() ? "passou" : "falhou"
    println CandidateTest.setAndGetCep() ? "passou" : "falhou"
    println CandidateTest.setAndGetDescription() ? "passou" : "falhou"
    println CandidateTest.setAndGetAge() ? "passou" : "falhou"
    println CandidateTest.setAndGetCpf() ? "passou" : "falhou"
    println CandidateTest.setAndGetSkills() ? "passou" : "falhou"

    //Enterprise Setters and Getters
    println EnterpriseTest.setAndGetName() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetEmail() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetCountry() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetState() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetCep() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetDescription() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetCnpj() ? "passou" : "falhou"
    println EnterpriseTest.setAndGetSkills() ? "passou" : "falhou"


    //UserManager - register
    println UserManagerTest.successfulRegisterEnterprise() ? "passou" : "falhou"
    println UserManagerTest.validateSkillsRegisterEnterprise() ? "passou" : "falhou"
    println UserManagerTest.successfulRegisterCandidate() ? "passou" : "falhou"
    println UserManagerTest.validateSkillsRegisterCandidate() ? "passou" : "falhou"

    //UserManager - remove
    println UserManagerTest.successfulRemoveCandidate() ? "passou" : "falhou"
    println UserManagerTest.successfulRemoveEnterprise() ? "passou" : "falhou"



    //print "\033[H\033[2J"
}

