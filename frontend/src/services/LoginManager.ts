import {Candidate} from "../entities/Candidate";
import {Enterprise} from "../entities/Enterprise";

export default class LoginManager {

    constructor() {
        if (!localStorage.getItem('logged_in')) {
            localStorage.setItem('logged_in', JSON.stringify({}))
        }
    }

    get loggedIn(): Candidate | Enterprise | null {
        let user: Candidate | Enterprise | null = null;

        const storedLoggedIn = localStorage.getItem('logged_in');

        if (storedLoggedIn) {
            let unknown = JSON.parse(storedLoggedIn)
            if ('cnpj' in unknown){
                user =  new Enterprise(unknown)
            }
            if ('cpf' in unknown){
                user = new Candidate(unknown)
            }
        }

        return user ? user : null
    }

    logIn(user: Candidate | Enterprise){
        if (localStorage.getItem('logged_in') !== '{}') {
            alert('Já há um usuário logado')
            return
        }
        localStorage.setItem('logged_in', JSON.stringify(user))
        alert('Login realizado com sucesso!')
    }

    logOut(){
        localStorage.setItem('logged_in', JSON.stringify({}))
    }

    get isCandidate(): boolean {
        const user = this.loggedIn;
        return user instanceof Candidate;
    }

    get isEnterprise(): boolean {
        const user = this.loggedIn;
        return user instanceof Enterprise;
    }

}