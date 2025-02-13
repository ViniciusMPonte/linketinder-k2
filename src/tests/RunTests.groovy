package tests

import tests.entities.CandidateTest
import tests.entities.EnterpriseTest
import tests.managers.UserManagerTest


static void main(String[] args) {

    def result = []


    //Candidate Setters and Getters
    result.add((CandidateTest.setAndGetName() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetName")
    result.add((CandidateTest.setAndGetEmail() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetEmail")
    result.add((CandidateTest.setAndGetCountry() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetCountry")
    result.add((CandidateTest.setAndGetState() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetState")
    result.add((CandidateTest.setAndGetCep() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetCep")
    result.add((CandidateTest.setAndGetDescription() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetDescription")
    result.add((CandidateTest.setAndGetAge() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetAge")
    result.add((CandidateTest.setAndGetCpf() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetCpf")
    result.add((CandidateTest.setAndGetSkills() ? "[PASSED]" : "[FAILED]") + " CandidateTest.setAndGetSkills")

    //Enterprise Setters and Getters
    result.add((EnterpriseTest.setAndGetName() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetName")
    result.add((EnterpriseTest.setAndGetEmail() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetEmail")
    result.add((EnterpriseTest.setAndGetCountry() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetCountry")
    result.add((EnterpriseTest.setAndGetState() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetState")
    result.add((EnterpriseTest.setAndGetCep() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetCep")
    result.add((EnterpriseTest.setAndGetDescription() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetDescription")
    result.add((EnterpriseTest.setAndGetCnpj() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetCnpj")
    result.add((EnterpriseTest.setAndGetSkills() ? "[PASSED]" : "[FAILED]") + " EnterpriseTest.setAndGetSkills")


    //UserManager - register
    result.add((UserManagerTest.successfulRegisterEnterprise() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.successfulRegisterEnterprise")
    result.add((UserManagerTest.validateParametersRegisterEnterprise() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.validateParametersRegisterEnterprise")
    result.add((UserManagerTest.successfulRegisterCandidate() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.successfulRegisterCandidate")
    result.add((UserManagerTest.validateParametersRegisterCandidate() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.validateParametersRegisterCandidate")

    //UserManager - remove
    result.add((UserManagerTest.successfulRemoveCandidate() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.successfulRemoveCandidate")
    result.add((UserManagerTest.successfulRemoveEnterprise() ? "[PASSED]" : "[FAILED]") + " UserManagerTest.successfulRemoveEnterprise")


    println "\033[H\033[2J"
    println result.join("\n")
}

