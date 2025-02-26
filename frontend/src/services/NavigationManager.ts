import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import {Employment, EmploymentConfig} from "../entities/Employment"

import DatabaseManager from "./DatabaseManager"

const dbManager = new DatabaseManager()
import LoginManager from "./LoginManager";

const loginManager = new LoginManager()

import Chart from "../components/Chart";
import Card from "../components/Card";
import {ProfileEnterprise, ProfileCandidate} from "../components/Profile";
import Nav from "../components/Nav";

const nav = new Nav()

export default class NavigationManager {

    router(): void {

        this.insertNav()
        this.activeNavListener()

        const path = window.location.pathname;
        switch (path) {
            case '/':
                if (!this.redirectIfLogged()) this.activeCandidateCreateFormListener()
                break;
            case '/candidate/register-candidate.html':
                if (!this.redirectIfLogged()) this.activeCandidateCreateFormListener()
                break;
            case '/enterprise/register-enterprise.html':
                if (!this.redirectIfLogged()) this.activeEnterpriseCreateFormListener()
                break;
            case '/enterprise/register-employment.html':
                if (!this.redirectIfLogged()) this.activeEmploymentCreateFormListener()
                break;
            case '/candidate/login-candidate.html':
                if (!this.redirectIfLogged()) this.activeCandidateLoginFormListener()
                break;
            case '/enterprise/login-enterprise.html':
                if (!this.redirectIfLogged()) this.activeEnterpriseLoginFormListener()
                break;
            case '/enterprise/candidates-list.html':
                if (!this.redirectIfNotLogged('enterprise')) this.buildEnterpriseCandidatesList()
                break;
            case '/enterprise/profile.html':
                if (!this.redirectIfNotLogged('enterprise')) this.buildEnterpriseProfile()
                break;
            case '/candidate/profile.html':
                if (!this.redirectIfNotLogged('candidate')) this.buildCandidateProfile()
                break;
            case '/enterprise/my-employments.html':
                if (!this.redirectIfNotLogged('enterprise')) this.buildEnterpriseMyEmploymentsList()
                break;
            case '/candidate/employments-list.html':
                if (!this.redirectIfNotLogged('candidate')) this.buildCandidateEmploymentsList()
                break;
            default:
                console.log('Rota não encontrada: Página 404');
                break;
        }
    }

    insertNav() {
        const body = document.querySelector('body');
        if (!body) return
        body.insertAdjacentHTML('afterbegin', nav.get());
    }

    innerHTMLInject(tag: HTMLElement | null, output: string): void {
        if (tag) {
            tag.innerHTML += output
        }
    }

    activeNavListener() {
        const logOutBtn = document.querySelector('#logout-btn')
        if (!logOutBtn) return

        logOutBtn.addEventListener('click', (event) => {
            if (loginManager.loggedIn) loginManager.logOut()
        })

        const resetDBBtn = document.querySelector('#reset-db-btn')
        if (!resetDBBtn) return

        resetDBBtn.addEventListener('click', (event) => {
            if (loginManager.loggedIn) loginManager.logOut()
            dbManager.reset()
        })
    }

    activeCandidateCreateFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #create-candidate-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const newCandidateData: CandidateConfig = {
                name: (document.getElementById('candidate-name-input') as HTMLInputElement)?.value || '',
                email: (document.getElementById('candidate-email-input') as HTMLInputElement)?.value || '',
                password: (document.getElementById('candidate-password-input') as HTMLInputElement)?.value || '',
                country: (document.getElementById('candidate-country-input') as HTMLInputElement)?.value || '',
                state: (document.getElementById('candidate-state-input') as HTMLInputElement)?.value || '',
                cep: (document.getElementById('candidate-cep-input') as HTMLInputElement)?.value || '',
                skills: (document.getElementById('candidate-skills-input') as HTMLInputElement)?.value?.split(', ') || [],
                description: (document.getElementById('candidate-description-input') as HTMLInputElement)?.value || '',
                cpf: (document.getElementById('candidate-cpf-input') as HTMLInputElement)?.value || '',
                age: Number((document.getElementById('candidate-age-input') as HTMLInputElement)?.value) || 0,
            }

            const isEmptyField = Object.values(newCandidateData).some(value => value.toString().trim() === '');

            if (isEmptyField) {
                alert('Por favor, preencha todos os campos obrigatórios!');
                return;
            }

            let candidatesWithSameEmail = dbManager.candidates?.filter(candidate =>
                candidate.email == newCandidateData.email
            )

            if (candidatesWithSameEmail != undefined && candidatesWithSameEmail.length == 0) {
                dbManager.addCandidate(new Candidate(newCandidateData))
                alert('Cadastro realizado com sucesso!')
                window.location.href = '/candidate/login-candidate.html';
            } else if (candidatesWithSameEmail != undefined && candidatesWithSameEmail.length > 0) {
                alert('Já existe um usuário com mesmo e-mail.')
            }

        })

    }

    activeEnterpriseCreateFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #create-enterprise-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const newEnterpriseData: EnterpriseConfig = {
                name: (document.getElementById('enterprise-name-input') as HTMLInputElement)?.value || '',
                email: (document.getElementById('enterprise-email-input') as HTMLInputElement)?.value || '',
                password: (document.getElementById('enterprise-password-input') as HTMLInputElement)?.value || '',
                country: (document.getElementById('enterprise-country-input') as HTMLInputElement)?.value || '',
                state: (document.getElementById('enterprise-state-input') as HTMLInputElement)?.value || '',
                cep: (document.getElementById('enterprise-cep-input') as HTMLInputElement)?.value || '',
                description: (document.getElementById('enterprise-description-input') as HTMLInputElement)?.value || '',
                cnpj: (document.getElementById('enterprise-cnpj-input') as HTMLInputElement)?.value || '',
            }

            const isEmptyField = Object.values(newEnterpriseData).some(value => value.toString().trim() === '');

            if (isEmptyField) {
                alert('Por favor, preencha todos os campos obrigatórios!');
                return;
            }

            let enterprisesWithSameEmail = dbManager.enterprises?.filter(enterprise =>
                enterprise.email == newEnterpriseData.email
            )

            if (enterprisesWithSameEmail != undefined && enterprisesWithSameEmail.length == 0) {
                dbManager.addEnterprise(new Enterprise(newEnterpriseData))
                alert('Cadastro realizado com sucesso!')
                window.location.href = '/enterprise/login-enterprise.html';
            } else if (enterprisesWithSameEmail != undefined && enterprisesWithSameEmail.length > 0) {
                alert('Já existe um usuário com mesmo e-mail.')
            }
        })

    }

    activeEmploymentCreateFormListener() {

        if (!loginManager.isEnterprise) {
            alert('`Precisa estar logado como empresa para acessar essa página`')
            window.location.href = '/enterprise/login-enterprise.html';
        }

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #create-employment-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()

            let enterpriseLogged: Enterprise = loginManager.loggedIn as Enterprise
            let enterpriseId: number = enterpriseLogged.id

            const newEmploymentData: EmploymentConfig = {
                name: (document.getElementById('employment-name-input') as HTMLInputElement)?.value || '',
                description: (document.getElementById('employment-description-input') as HTMLInputElement)?.value || '',
                skills: (document.getElementById('employment-skills-input') as HTMLInputElement)?.value?.split(', ') || [],
                enterpriseId: enterpriseId
            }

            dbManager.addEmployment(new Employment(newEmploymentData))
            alert('Cadastro realizado com sucesso!')
            window.location.href = '/enterprise/candidates-list.html';
        })

    }

    activeCandidateLoginFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #login-candidate-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const loginCandidateData: CandidateConfig = {
                email: (document.getElementById('candidate-email-input') as HTMLInputElement)?.value || '',
                password: (document.getElementById('candidate-password-input') as HTMLInputElement)?.value || '',
            }

            let candidatesFiltered = dbManager.candidates?.filter(candidate =>
                candidate.email == loginCandidateData.email && candidate.password == loginCandidateData.password
            )

            if (candidatesFiltered == undefined || candidatesFiltered.length == 0) {
                alert('Usuário não encontrado')
            } else if (candidatesFiltered.length > 1) {
                alert('Usuário repetido')
            } else if (candidatesFiltered.length == 1) {
                loginManager.logIn(candidatesFiltered[0])
                window.location.href = '/candidate/employments-list.html';
            }
        })
    }

    activeEnterpriseLoginFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #login-enterprise-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const loginEnterpriseData: EnterpriseConfig = {
                email: (document.getElementById('enterprise-email-input') as HTMLInputElement)?.value || '',
                password: (document.getElementById('enterprise-password-input') as HTMLInputElement)?.value || '',
            }

            let enterprisesFiltered = dbManager.enterprises?.filter(enterprise =>
                enterprise.email == loginEnterpriseData.email && enterprise.password == loginEnterpriseData.password
            )

            if (enterprisesFiltered == undefined || enterprisesFiltered.length == 0) {
                alert('Usuário não encontrado')
            } else if (enterprisesFiltered.length > 1) {
                alert('Usuário repetido')
            } else if (enterprisesFiltered.length == 1) {
                loginManager.logIn(enterprisesFiltered[0])
            }
            if (loginManager.isEnterprise) window.location.href = '/enterprise/candidates-list.html';
        })
    }

    buildEnterpriseCandidatesList() {
        let chartTag = document.getElementById('myChart') as HTMLCanvasElement
        if (chartTag) {
            if (dbManager.candidates == null) return
            let skillCounts = Chart.countCandidateSkills(dbManager.candidates)
            let keys = Object.keys(skillCounts)
            let values = Object.values(skillCounts)
            Chart.build(chartTag, keys, values);
        }

        if (dbManager.candidates == null) return
        dbManager.candidates.forEach(candidate => {
                if (true) candidate.name = '<span class="blur">Hidden Name<\span>'
                const cardComponent = new Card(candidate.params, 'candidate');
                this.innerHTMLInject(document.querySelector('#candidates-list'), cardComponent.getCard());
            }
        )
    }

    buildCandidateEmploymentsList() {
        if (dbManager.employments == null) return
        dbManager.employments.forEach(employment => {
                if (true) employment.name = '<span class="blur">Hidden Name<\span>'
                const cardComponent = new Card(employment.params, 'employment', employment.enterpriseId);
                this.innerHTMLInject(document.querySelector('#employments-list'), cardComponent.getCard());
            }
        )
    }

    buildEnterpriseMyEmploymentsList() {

        if (!loginManager.isEnterprise) {
            window.location.href = '/enterprise/register-enterprise.html';
            return
        }

        let enterpriseLogged: Enterprise = loginManager.loggedIn as Enterprise
        let enterpriseId: number = enterpriseLogged.id

        if (dbManager.employments == null) return

        let isEmptyEmployment = true
        dbManager.employments.forEach(employment => {
            if (employment.enterpriseId == enterpriseId) {
                isEmptyEmployment = false
                const cardComponent = new Card(employment.params, 'employment', employment.enterpriseId);
                this.innerHTMLInject(document.querySelector('#employments-list'), cardComponent.getCard(false));
            }
        })
        if (isEmptyEmployment) {
            this.innerHTMLInject(document.querySelector('#employments-list'), '<h1>Nenhuma vaga encontrada, crie uma vaga antes!</h1>');
        }
    }

    buildEnterpriseProfile() {
        if (!loginManager.isEnterprise) {
            window.location.href = '/enterprise/register-enterprise.html';
            return
        }

        let enterpriseLogged: Enterprise = loginManager.loggedIn as Enterprise
        let profileEnterprise = new ProfileEnterprise(enterpriseLogged)
        this.innerHTMLInject(document.querySelector('#enterprise-profile'), profileEnterprise.get());
    }

    buildCandidateProfile() {
        if (!loginManager.isCandidate) {
            window.location.href = '/candidate/register-candidate.html';
            return
        }

        let candidateLogged: Candidate = loginManager.loggedIn as Candidate
        let profileCandidate = new ProfileCandidate(candidateLogged)
        this.innerHTMLInject(document.querySelector('#candidate-profile'), profileCandidate.get());
    }

    redirectIfLogged(): boolean {
        let ifLogged = false
        if (loginManager.loggedIn) {
            ifLogged = true
            if (loginManager.isCandidate) window.location.href = '/candidate/employments-list.html';
            if (loginManager.isEnterprise) window.location.href = '/enterprise/candidates-list.html';
        }
        return ifLogged
    }

    redirectIfNotLogged(user:string): boolean {
        let ifLogged = false

        if(user == 'candidate' && loginManager.isCandidate) return ifLogged
        if(user == 'enterprise' && loginManager.isEnterprise) return ifLogged

        ifLogged = true
        window.location.href = '/';
        return ifLogged
    }
}