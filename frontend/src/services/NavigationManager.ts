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
import ContentBuilder from "../view/ContentBuilder"

const dbManager = new DatabaseManager()
const loginManager = new LoginManager()

const nav = new Nav()

const dependencies = {entityFactories, coreServices, validationServices, uiServices, components}
const candidateFormsManager = new CandidateFormsManager(dependencies)
const enterpriseFormsManager = new EnterpriseFormsManager(dependencies)
const employmentFormsManager = new EmploymentFormsManager(dependencies)

const contentBuilder = new ContentBuilder(dependencies)

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
                if (!this.redirectIfNotLogged("enterprise")) contentBuilder.enterpriseCandidatesList()
                break
            case "/enterprise/profile.html":
                if (!this.redirectIfNotLogged("enterprise")) contentBuilder.enterpriseProfile()
                break
            case "/candidate/profile.html":
                if (!this.redirectIfNotLogged("candidate")) contentBuilder.candidateProfile()
                break
            case "/enterprise/my-employments.html":
                if (!this.redirectIfNotLogged("enterprise")) contentBuilder.enterpriseMyEmploymentsList()
                break
            case "/candidate/employments-list.html":
                if (!this.redirectIfNotLogged("candidate")) contentBuilder.candidateEmploymentsList()
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