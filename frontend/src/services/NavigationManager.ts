import {Candidate} from "../entities/Candidate"
import {Enterprise} from "../entities/Enterprise"

import DatabaseManager from "./DatabaseManager"
import LoginManager from "./LoginManager"

import Chart from "../components/Chart"
import Card from "../components/Card"
import {ProfileEnterprise, ProfileCandidate} from "../components/Profile"
import Nav from "../components/Nav"

import CandidateFormsManager from "../view/forms/CandidateFormsManager"
import EnterpriseFormsManager from "../view/forms/EnterpriseFormsManager"
import EmploymentFormsManager from "../view/forms/EmploymentFormsManager"

import {entityFactories, coreServices, validationServices, uiServices, components} from "../main"

const dbManager = new DatabaseManager()
const loginManager = new LoginManager()

const nav = new Nav()

const dependencies = {entityFactories, coreServices, validationServices, uiServices}
const candidateFormsManager = new CandidateFormsManager(dependencies)
const enterpriseFormsManager = new EnterpriseFormsManager(dependencies)
const employmentFormsManager = new EmploymentFormsManager(dependencies)


export default class NavigationManager {

    router(): void {

        this.insertNav()
        this.activeNavListener()

        const path = window.location.pathname
        switch (path) {
            // case '/':
            //     this.redirectIfLogged()
            //     PublicPages.activeCandidateCreateFormListener()
            //     break;
            case "/":
                if (!this.redirectIfLogged()) break
                break
            case "/candidate/register-candidate.html":
                if (!this.redirectIfLogged()) candidateFormsManager.activeCandidateCreateFormListener()
                break
            case "/enterprise/register-enterprise.html":
                if (!this.redirectIfLogged()) enterpriseFormsManager.activeEnterpriseCreateFormListener()
                break
            case "/enterprise/register-employment.html":
                if (!this.redirectIfNotLogged("enterprise")) employmentFormsManager.activeEmploymentCreateFormListener()
                break
            case "/candidate/login-candidate.html":
                if (!this.redirectIfLogged()) candidateFormsManager.activeCandidateLoginFormListener()
                break
            case "/enterprise/login-enterprise.html":
                if (!this.redirectIfLogged()) enterpriseFormsManager.activeEnterpriseLoginFormListener()
                break
            case "/enterprise/candidates-list.html":
                if (!this.redirectIfNotLogged("enterprise")) this.buildEnterpriseCandidatesList()
                break
            case "/enterprise/profile.html":
                if (!this.redirectIfNotLogged("enterprise")) this.buildEnterpriseProfile()
                break
            case "/candidate/profile.html":
                if (!this.redirectIfNotLogged("candidate")) this.buildCandidateProfile()
                break
            case "/enterprise/my-employments.html":
                if (!this.redirectIfNotLogged("enterprise")) this.buildEnterpriseMyEmploymentsList()
                break
            case "/candidate/employments-list.html":
                if (!this.redirectIfNotLogged("candidate")) this.buildCandidateEmploymentsList()
                break
            default:
                console.log("Rota não encontrada: Página 404")
                break
        }
    }

    insertNav() {
        const body = document.querySelector("body")
        if (!body) return
        body.insertAdjacentHTML("afterbegin", nav.get())
    }

    innerHTMLInject(tag: HTMLElement | null, output: string): void {
        if (tag) {
            tag.innerHTML += output
        }
    }

    activeNavListener() {
        const logOutBtn = document.querySelector("#logout-btn")
        if (!logOutBtn) return

        logOutBtn.addEventListener("click", (event) => {
            if (loginManager.loggedIn) loginManager.logOut()
        })

        const resetDBBtn = document.querySelector("#reset-db-btn")
        if (!resetDBBtn) return

        resetDBBtn.addEventListener("click", (event) => {
            if (loginManager.loggedIn) loginManager.logOut()
            dbManager.reset()
        })
    }


    buildEnterpriseCandidatesList() {
        let chartTag = document.getElementById("myChart") as HTMLCanvasElement
        if (chartTag) {
            if (dbManager.candidates == null) return
            let skillCounts = Chart.countCandidateSkills(dbManager.candidates)
            let keys = Object.keys(skillCounts)
            let values = Object.values(skillCounts)
            Chart.build(chartTag, keys, values)
        }

        if (dbManager.candidates == null) return
        dbManager.candidates.forEach(candidate => {
            if (true) candidate.name = "<span class=\"blur\">Hidden Name<\span>"
            const card = components.card.candidatesForEnterprise(candidate.id)
            if (card == undefined) return
            this.innerHTMLInject(document.querySelector("#candidates-list"), card)
        })
    }

    buildCandidateEmploymentsList() {
        if (dbManager.employments == null) return
        dbManager.employments.forEach(employment => {
            if (true) employment.name = "<span class=\"blur\">Hidden Name<\span>"
            const card = components.card.employmentForCandidate(employment.id, false)
            if (card == undefined) return
            this.innerHTMLInject(document.querySelector("#employments-list"), card)
        })
    }

    buildEnterpriseMyEmploymentsList() {

        if (!loginManager.isEnterprise) {
            window.location.href = "/enterprise/register-enterprise.html"
            return
        }

        let enterpriseLogged: Enterprise = loginManager.loggedIn as Enterprise
        let enterpriseId: number = enterpriseLogged.id

        if (dbManager.employments == null) return

        let isEmptyEmployment = true
        dbManager.employments.forEach(employment => {
            if (employment.enterpriseId == enterpriseId) {
                isEmptyEmployment = false
                const card = components.card.employmentForEnterprise(employment.id, false)
                if (card == undefined) return
                this.innerHTMLInject(document.querySelector("#employments-list"), card)
            }
        })
        if (isEmptyEmployment) {
            this.innerHTMLInject(document.querySelector("#employments-list"), "<h1>Nenhuma vaga encontrada, crie uma vaga antes!</h1>")
        }
    }

    buildEnterpriseProfile() {
        if (!loginManager.isEnterprise) {
            window.location.href = "/enterprise/register-enterprise.html"
            return
        }

        let enterpriseLogged: Enterprise = loginManager.loggedIn as Enterprise
        let profileEnterprise = new ProfileEnterprise(enterpriseLogged)
        this.innerHTMLInject(document.querySelector("#enterprise-profile"), profileEnterprise.get())
    }

    buildCandidateProfile() {
        if (!loginManager.isCandidate) {
            window.location.href = "/candidate/register-candidate.html"
            return
        }

        let candidateLogged: Candidate = loginManager.loggedIn as Candidate
        let profileCandidate = new ProfileCandidate(candidateLogged)
        this.innerHTMLInject(document.querySelector("#candidate-profile"), profileCandidate.get())
    }

    redirectIfLogged(): boolean {
        let ifLogged = false
        if (loginManager.loggedIn) {
            ifLogged = true
            if (loginManager.isCandidate) window.location.href = "/candidate/employments-list.html"
            if (loginManager.isEnterprise) window.location.href = "/enterprise/candidates-list.html"
        }
        return ifLogged
    }

    redirectIfNotLogged(user: string): boolean {
        let ifLogged = false

        if (user == "candidate" && loginManager.isCandidate) return ifLogged
        if (user == "enterprise" && loginManager.isEnterprise) return ifLogged

        ifLogged = true
        window.location.href = "/"
        return ifLogged
    }
}