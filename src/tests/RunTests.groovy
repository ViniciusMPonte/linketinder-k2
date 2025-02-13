package tests

import managers.UserManagerTest


static void main(String[] args) {

    def test = new UserManagerTest()
    println test.successfulRegisterEnterprise() ? "passou" : "falhou"
    println test.successfulRegisterCandidate() ? "passou" : "falhou"



    //print "\033[H\033[2J"
}

