package tests

import managers.UserManagerTest


static void main(String[] args) {

    def test = new UserManagerTest()
    test.successfulRegisterEnterprise()

    //print "\033[H\033[2J"
}

