import LoginManager from "../services/LoginManager";

const loginManager = new LoginManager()

export default class Nav {


    constructor() {

    }

    get(): string {

        return `
            <nav class="navbar navbar-expand-lg bg-info">
                <div class="container-fluid">
                    <a class="navbar-brand text-light" href="#">Linketinder</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            ${this.home}
                            ${this.candidateOptions}
                            ${this.enterpriseOptions}
                            <li class="nav-item">
                                <a id="reset-db-btn" class="nav-link text-light" aria-current="page" href="/">Resetar DB</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0 pe-3">
                        ${this.loginAndRegisterOptions}
                        ${this.logOut}
                        </ul>
                    </div>
                </div>
            </nav>
        `
    }

    get loginAndRegisterOptions(): string {
        if (loginManager.loggedIn != null) return ''

        return `
                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0 pe-3">
                        
                            <li class="nav-item dropdown">
                                <a class="nav-link text-light dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Cadastrar
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="/candidate/register-candidate.html">Candidato</a></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="/enterprise/register-enterprise.html">Empresa</a></li>
                                </ul>
                            </li>
            
                            <li class="nav-item dropdown">
                                <a class="nav-link text-light dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Login
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="/candidate/login-candidate.html">Candidato</a></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="/enterprise/login-enterprise.html">Empresa</a></li>
                                </ul>
                            </li>
                        </ul>
        
        `
    }

    get candidateOptions(): string {
        if (!loginManager.isCandidate) return ''

        return `
                            <li class="nav-item">
                                <a class="nav-link text-light" aria-current="page" href="/candidate/employments-list.html">Vagas</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-light" aria-current="page" href="/candidate/profile.html">Perfil</a>
                            </li>        
        `

    }

    get enterpriseOptions(): string {
        if (!loginManager.isEnterprise) return ''

        return `
                            <li class="nav-item">
                                <a class="nav-link text-light" aria-current="page" href="/enterprise/candidates-list.html">Candidatos</a>
                            </li>
            
            
                            <li class="nav-item dropdown">
                                <a class="nav-link text-light dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Minhas Vagas
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="/enterprise/my-employments.html">Minhas Vagas</a></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="/enterprise/register-employment.html">Cadastrar Nova Vaga</a>
                                    </li>
                                </ul>
                            </li>
            
            
                            <li class="nav-item">
                                <a class="nav-link text-light" aria-current="page" href="/enterprise/profile.html">Perfil</a>
                            </li>  
        `

    }

    get logOut(): string {
        if (!loginManager.loggedIn) return ''
        return `
            <li class="nav-item">
                <a id="logout-btn" class="nav-link text-light" aria-current="page" href="/">Log Out</a>
            </li>
        `
    }

    get home(): string {
        if (loginManager.loggedIn) return ''
        return `
        <li class="nav-item">
            <a class="nav-link text-light active" aria-current="page" href="/">Home</a>
        </li>
        `
    }

}


