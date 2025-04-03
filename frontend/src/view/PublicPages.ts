import {Candidate, CandidateConfig} from "../entities/Candidate"
import DatabaseManager from "../services/DatabaseManager"
import CandidateValidation from "../services/validation/CandidateValidation"
import DatabaseValidation from "../services/validation/DatabaseValidation"

const dbManager = new DatabaseManager()

export default class PublicPages {

    static activeCandidateCreateFormListener() {
        const createButton = PublicPages.getCreateButton()
        if (!createButton) return
        createButton.addEventListener('click', PublicPages.handleCandidateCreation.bind(this))
    }

    private static getCreateButton(){
        return document.querySelector('.card-body #create-candidate-btn')
    }

    private static handleCandidateCreation(event: Event) {
        event.preventDefault()

        try {
            const candidateData = PublicPages.getInputForNewCandidate()

            if (!PublicPages.isValidCandidate(candidateData)) {
                return
            }

            dbManager.addCandidate(new Candidate(candidateData))
            PublicPages.notifySuccessAndRedirect()
        } catch (error) {
            console.log(error)
        }
    }

    private static getInputForNewCandidate(){
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

    private static isValidCandidate(data: CandidateConfig): boolean {
        const isValid = CandidateValidation.checkRegistrationData(data)
        if (!isValid) return false

        const isDuplicatedEmail = DatabaseValidation.checkDuplicateCandidateEmail(dbManager.candidates, data)
        if (isDuplicatedEmail) return false

        return true
    }

    private static notifySuccessAndRedirect() {
        alert('Cadastro realizado com sucesso!')
        window.location.href = '/candidate/login-candidate.html'
    }

}