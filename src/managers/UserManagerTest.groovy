package managers

import entities.Candidate
import entities.Enterprise
import tests.utils.FakeScanner
import tests.mocks.UsersMock

class UserManagerTest {

    static def boolean successfulRegisterEnterprise() {
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

    static def boolean validateSkillsRegisterEnterprise() {
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

    static def boolean successfulRegisterCandidate() {
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

    static def boolean validateSkillsRegisterCandidate() {
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

    static def boolean successfulRemoveCandidate() {
        //Arrange
        List<Candidate> candidatesList = UsersMock.getCandidatesList()
        Scanner fakeInput = new FakeScanner(["wrong", candidatesList.get(0).getName()]).fakeInput

        //Act
        UserManager.removeCandidate(fakeInput, candidatesList)
        fakeInput.close()

        //Assert
        return candidatesList.empty
    }

    static def boolean successfulRemoveEnterprise() {
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

