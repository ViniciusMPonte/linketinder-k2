import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import {Employment, EmploymentConfig} from "../entities/Employment"

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
                country: (document.getElementById('enterprise-country-input') as HTMLInputElement)?.value || '',
                state: (document.getElementById('enterprise-state-input') as HTMLInputElement)?.value || '',
                cep: (document.getElementById('enterprise-cep-input') as HTMLInputElement)?.value || '',
                description: (document.getElementById('enterprise-description-input') as HTMLInputElement)?.value || '',
                cnpj: (document.getElementById('enterprise-cnpj-input') as HTMLInputElement)?.value || '',
            }

            dbManager.addEnterprise(new Enterprise(newEnterpriseData))

            alert('Cadastro realizado com sucesso!')
        })

    }

    activeEmploymentCreateFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #create-employment-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const newEmploymentData: EmploymentConfig = {
                name: (document.getElementById('employment-name-input') as HTMLInputElement)?.value || '',
                description: (document.getElementById('employment-description-input') as HTMLInputElement)?.value || '',
                skills: (document.getElementById('employment-skills-input') as HTMLInputElement)?.value?.split(', ') || [],
            }

            dbManager.addEmployment(new Employment(newEmploymentData))

            alert('Cadastro realizado com sucesso!')
        })

    }

}