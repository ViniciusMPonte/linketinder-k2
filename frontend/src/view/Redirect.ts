import LoginManager from "../services/LoginManager"

interface RedirectConfig {
    loginManager: LoginManager
}

interface RouteConfig {
    redirectMethod: () => void;
}

export default class Redirect {

    private readonly loginManager


    constructor({loginManager}: RedirectConfig) {
        this.loginManager = loginManager
    }

    routes: Record<string, RouteConfig> = {
        "/": {
            redirectMethod: () => this.redirectAuthenticatedUserByRole(),
        },
        "/candidate/register-candidate.html": {
            redirectMethod: () => this.redirectAuthenticatedUserByRole(),
        },
        "/enterprise/register-enterprise.html": {
            redirectMethod: () => this.redirectAuthenticatedUserByRole(),
        },
        "/candidate/login-candidate.html": {
            redirectMethod: () => this.redirectAuthenticatedUserByRole(),
        },
        "/enterprise/login-enterprise.html": {
            redirectMethod: () => this.redirectAuthenticatedUserByRole(),
        },
        "/candidate/profile.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("candidate"),
        },
        "/enterprise/profile.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("enterprise"),
        },
        "/candidate/employments-list.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("candidate"),
        },
        "/enterprise/candidates-list.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("enterprise"),
        },
        "/enterprise/my-employments.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("enterprise"),
        },
        "/enterprise/register-employment.html": {
            redirectMethod: () => this.redirectIfUnauthorizedRole("enterprise"),
        },
    }

    redirectAuthenticatedUserByRole(): boolean {
        let isRedirected = false
        if (this.loginManager.loggedIn) {
            isRedirected = true
            if (this.loginManager.isCandidate) this.candidateEmploymentsList()
            if (this.loginManager.isEnterprise) this.enterpriseCandidatesList()
        }
        return isRedirected
    }

    redirectIfUnauthorizedRole(expectedRole: string): boolean {
        const isAuthorized = (expectedRole === "candidate" && this.loginManager.isCandidate) ||
            (expectedRole === "enterprise" && this.loginManager.isEnterprise)

        if (!isAuthorized) this.home()
        return !isAuthorized
    }

    home() {
        window.location.href = "/"
    }

    loginCandidate() {
        window.location.href = "/candidate/login-candidate.html"
    }

    loginEnterprise() {
        window.location.href = "/enterprise/login-enterprise.html"
    }

    candidateEmploymentsList() {
        window.location.href = "/candidate/employments-list.html"
    }

    enterpriseCandidatesList() {
        window.location.href = "/enterprise/candidates-list.html"
    }
}