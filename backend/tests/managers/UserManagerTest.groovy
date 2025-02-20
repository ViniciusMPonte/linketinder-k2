package tests.managers

import entities.Candidate
import entities.Enterprise
import managers.UserManager
import tests.utils.FakeScanner
import tests.mocks.UsersMock

class UserManagerTest {

    static boolean successfulRegisterEnterprise() {
        //Arrange
        Scanner fakeInput = new FakeScanner(
                UsersMock.getCorrectEntriesToCreateOneEnterprise()
        ).fakeInput
        List<Enterprise> enterprisesList = []

        //Act
        UserManager.registerEnterprise(fakeInput, enterprisesList)
        fakeInput.close()

        //Assert
        return enterprisesList.size() == 1 && enterprisesList.get(0) instanceof Enterprise
    }

    static boolean validateParametersRegisterEnterprise() {
        //Arrange
        Scanner fakeInput = new FakeScanner(
                UsersMock.getWrongEntriesToCreateOneEnterprise()
        ).fakeInput
        List<Enterprise> enterprisesList = []

        //Act
        UserManager.registerEnterprise(fakeInput, enterprisesList)
        fakeInput.close()

        //Assert
        return enterprisesList.size() == 1 && enterprisesList.get(0) instanceof Enterprise
    }

    static boolean successfulRegisterCandidate() {
        //Arrange
        Scanner fakeInput = new FakeScanner(
                UsersMock.getCorrectEntriesToCreateOneCandidate()
        ).fakeInput
        List<Candidate> candidatesList = []

        //Act
        UserManager.registerCandidate(fakeInput, candidatesList)
        fakeInput.close()

        //Assert
        return candidatesList.size() == 1 && candidatesList.get(0) instanceof Candidate
    }

    static boolean validateParametersRegisterCandidate() {
        //Arrange
        Scanner fakeInput = new FakeScanner(
                UsersMock.getWrongEntriesToCreateOneCandidate()
        ).fakeInput
        List<Candidate> candidatesList = []

        //Act
        UserManager.registerCandidate(fakeInput, candidatesList)
        fakeInput.close()

        //Assert
        return candidatesList.size() == 1 && candidatesList.get(0) instanceof Candidate
    }

    static boolean successfulRemoveCandidate() {
        //Arrange
        List<Candidate> candidatesList = UsersMock.getCandidatesList()
        Scanner fakeInput = new FakeScanner(["wrong", candidatesList.get(0).getName()]).fakeInput

        //Act
        UserManager.removeCandidate(fakeInput, candidatesList)
        fakeInput.close()

        //Assert
        return candidatesList.empty
    }

    static boolean successfulRemoveEnterprise() {
        //Arrange
        List<Enterprise> enterpriseList = UsersMock.getEnterpriseList()
        Scanner fakeInput = new FakeScanner(["wrong", enterpriseList.get(0).getName()]).fakeInput

        //Act
        UserManager.removeEnterprise(fakeInput, enterpriseList)
        fakeInput.close()

        //Assert
        return enterpriseList.empty
    }


}

