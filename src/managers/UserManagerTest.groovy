package managers

import entities.Enterprise
import tests.utils.FakeScanner
import tests.mocks.UsersMock

class UserManagerTest {

    def boolean successfulRegisterEnterprise() {
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
}

