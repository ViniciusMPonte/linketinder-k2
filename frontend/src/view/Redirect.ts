import LoginManager from "../services/LoginManager"

export default class Redirect {

    private readonly loginManager

    constructor(loginManager:LoginManager) {
        this.loginManager = loginManager
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

    home(){
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