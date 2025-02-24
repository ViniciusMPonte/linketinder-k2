import {Candidate, CandidateConfig} from "../entities/Candidate"
import DatabaseManager from "../data/DatabaseManager"
const dbManager = new DatabaseManager()

export default class NavigationManager {

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
                country: (document.getElementById('candidate-country-input') as HTMLInputElement)?.value || '',
                state: (document.getElementById('candidate-state-input') as HTMLInputElement)?.value || '',
                cep: (document.getElementById('candidate-cep-input') as HTMLInputElement)?.value || '',
                skills: (document.getElementById('candidate-skills-input') as HTMLInputElement)?.value?.split(', ') || [],
                description: (document.getElementById('candidate-description-input') as HTMLInputElement)?.value || '',
                cpf: (document.getElementById('candidate-cpf-input') as HTMLInputElement)?.value || '',
                age: Number((document.getElementById('candidate-age-input') as HTMLInputElement)?.value) || 0,
            }

            dbManager.addCandidate(new Candidate(newCandidateData))
            
            alert('Cadastro realizado com sucesso!')
        })

    }

}