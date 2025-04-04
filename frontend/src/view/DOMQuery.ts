import {CandidateConfig} from "../entities/Candidate";
import {EnterpriseConfig} from "../entities/Enterprise"

export default class DOMQuery {

    getCreateCandidateButton(){
        return document.querySelector('.card-body #create-candidate-btn')
    }

    getCreateEnterpriseButton(){
        return document.querySelector(".card-body #create-enterprise-btn")
    }

    getInputForNewCandidate(){
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
        return newCandidateData
    }

    getInputForNewEnterprise(){
        const newEnterpriseData: EnterpriseConfig = {
            name: (document.getElementById("enterprise-name-input") as HTMLInputElement)?.value || "",
            email: (document.getElementById("enterprise-email-input") as HTMLInputElement)?.value || "",
            password: (document.getElementById("enterprise-password-input") as HTMLInputElement)?.value || "",
            country: (document.getElementById("enterprise-country-input") as HTMLInputElement)?.value || "",
            state: (document.getElementById("enterprise-state-input") as HTMLInputElement)?.value || "",
            cep: (document.getElementById("enterprise-cep-input") as HTMLInputElement)?.value || "",
            description: (document.getElementById("enterprise-description-input") as HTMLInputElement)?.value || "",
            cnpj: (document.getElementById("enterprise-cnpj-input") as HTMLInputElement)?.value || "",
        }
        return newEnterpriseData
    }

    getLoginCandidateButton(){
        return document.querySelector(".card-body #login-candidate-btn")
    }

    getInputForLoginCandidate(){
        const loginData: CandidateConfig = {
            email: (document.getElementById("candidate-email-input") as HTMLInputElement)?.value || "",
            password: (document.getElementById("candidate-password-input") as HTMLInputElement)?.value || "",
        }
        return loginData
    }

}