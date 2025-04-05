export default class Notification {
    successRegistration() {
        alert("Cadastro realizado com sucesso!")
    }

    repeatedEmailResgistrationError(){
        alert("Já existe um usuário com mesmo e-mail.")
    }

    userNotFound(){
        alert("Usuário não encontrado")
    }

    repeatedUser(){
        alert("Usuário repetido")
    }

    unauthorizedAccess() {
        alert("`Precisa estar logado para acessar essa página`")
    }
}