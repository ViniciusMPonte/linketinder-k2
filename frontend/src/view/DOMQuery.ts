import {CandidateConfig} from "../entities/Candidate";

export default class DOMQuery {

    getCreateButton(){
        return document.querySelector('.card-body #create-candidate-btn')
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
}