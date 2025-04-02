import {Candidate, CandidateConfig} from "../entities/Candidate"
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise"
import {Employment, EmploymentConfig} from "../entities/Employment"
import ValidationForms from "../services/ValidationForms";

import DatabaseManager from "../services/DatabaseManager"

const dbManager = new DatabaseManager()
import LoginManager from "../services/LoginManager";

const loginManager = new LoginManager()

import Chart from "../components/Chart";
import Card from "../components/Card";
import {ProfileEnterprise, ProfileCandidate} from "../components/Profile";
import Nav from "../components/Nav";

const nav = new Nav()

export default class PublicPages {

    activeCandidateCreateFormListener() {

        const form = document.querySelector('.card-body')
        if (!form) return
        const formBtn = document.querySelector('.card-body #create-candidate-btn')
        if (!formBtn) return

        formBtn.addEventListener('click', (event) => {
            event.preventDefault()
            const newCandidateData = this.getInputForNewCandidate()

            const isEmptyField = Object.values(newCandidateData).some(value => value.toString().trim() === '');

            if (isEmptyField) {
                alert('Por favor, preencha todos os campos obrigatórios!');
                return;
            }

            let isItValid = true
            for (const [key, value] of Object.entries(newCandidateData)) {
                const validateKey: string = key as string
                const result = ValidationForms.validate(validateKey, String(value));

                if(!result){
                    alert(ValidationForms.validationFailMessageCandidate(validateKey))
                    isItValid = false
                }
            }
            if (!isItValid) return

            let candidatesWithSameEmail = dbManager.candidates?.filter(candidate =>
                candidate.email == newCandidateData.email
            )

            if (candidatesWithSameEmail != undefined && candidatesWithSameEmail.length == 0) {
                dbManager.addCandidate(new Candidate(newCandidateData))
                alert('Cadastro realizado com sucesso!')
                window.location.href = '/candidate/login-candidate.html';
            } else if (candidatesWithSameEmail != undefined && candidatesWithSameEmail.length > 0) {
                alert('Já existe um usuário com mesmo e-mail.')
            }

        })

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