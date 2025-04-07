import {Candidate, CandidateConfig} from "../entities/Candidate";
import CandidatesData from "../data/CandidatesData";
import {Enterprise, EnterpriseConfig} from "../entities/Enterprise";
import EnterprisesData from "../data/EnterprisesData";
import {Employment, EmploymentConfig} from "../entities/Employment";
import EmploymentsData from "../data/EmploymentsData";


export default class DatabaseManager {
    constructor() {
        if (!localStorage.getItem('db_candidates')) {
            localStorage.setItem('db_candidates', JSON.stringify(CandidatesData.getInfos()))
        }

        if (!localStorage.getItem('db_enterprises')) {
            localStorage.setItem('db_enterprises', JSON.stringify(EnterprisesData.getInfos()))
        }

        if (!localStorage.getItem('db_employments')) {
            this.enterprises

            if(this.enterprises == null) return
            const enterpriseIds = this.enterprises.map(enterprise => enterprise.id)
            localStorage.setItem('db_employments', JSON.stringify(EmploymentsData.getInfos(enterpriseIds)))
        }
    }

    reset(){
        localStorage.setItem('db_candidates', JSON.stringify(CandidatesData.getInfos()))
        localStorage.setItem('db_enterprises', JSON.stringify(EnterprisesData.getInfos()))
        if(this.enterprises == null) return
        const enterpriseIds = this.enterprises.map(enterprise => enterprise.id)
        localStorage.setItem('db_employments', JSON.stringify(EmploymentsData.getInfos(enterpriseIds)))
    }

    set candidates(candidates: Candidate[]) {
        localStorage.setItem('db_candidates', JSON.stringify(candidates))
    }

    get candidates(): Candidate[] | null {
        let candidatesData: Candidate[] | null = null;

        const storedCandidates = localStorage.getItem('db_candidates');
        if (storedCandidates) {
            candidatesData = JSON.parse(storedCandidates) as Candidate[];
        }

        let candidates: Candidate[] = []

        if (candidatesData) {
            candidatesData.forEach((candidateData) => {
                candidates.push(new Candidate(candidateData))
            });
        }

        return candidates ? candidates : null
    }

    addCandidate(candidate: Candidate): void {
        let candidates = this.candidates
        if (candidates === null) return
        candidates.push(candidate)
        this.candidates = candidates
    }

    getCandidate(id: number): Candidate | void {
        let candidates = this.candidates
        if (candidates === null) return
        return candidates.filter(candidate => candidate.id === id)[0]
    }

    updateCandidate(id: number, candidate: CandidateConfig): void {
        let candidates = this.candidates
        if (candidates === null) return

        let candidateIndex = candidates.findIndex(candidate => candidate.id === id);
        if (candidateIndex === -1) return

        let updateCandidate = candidates[candidateIndex];

        for (const [key, value] of Object.entries(candidate)) {
            updateCandidate.updateWithKeyString(key, value);
        }
        candidates[candidateIndex] = updateCandidate
        this.candidates = candidates
    }

    removeCandidate(id: number): void {
        let candidates = this.candidates
        if (candidates === null) return
        this.candidates = candidates.filter(candidate => candidate.id !== id);
    }

    set enterprises(enterprises: Enterprise[]) {
        localStorage.setItem('db_enterprises', JSON.stringify(enterprises))
    }

    get enterprises(): Enterprise[] | null {
        let enterprisesData: Enterprise[] | null = null;

        const storedEnterprise = localStorage.getItem('db_enterprises');
        if (storedEnterprise) {
            enterprisesData = JSON.parse(storedEnterprise) as Enterprise[];
        }

        let enterprises: Enterprise[] = []

        if (enterprisesData) {
            enterprisesData.forEach((enterpriseData) => {
                enterprises.push(new Enterprise(enterpriseData))
            });
        }

        return enterprises ? enterprises : null
    }

    addEnterprise(enterprise: Enterprise): void {
        let enterprises = this.enterprises
        if (enterprises === null) return
        enterprises.push(enterprise)
        this.enterprises = enterprises
    }

    getEnterprise(id: number) {
        const enterprises = this.enterprises
        if (enterprises === null) return undefined

        const result = enterprises.filter(enterprise => enterprise.id === id)

        if (result.length > 0){
            return result[0]
        } else {
            return undefined
        }
    }

    updateEnterprise(id: number, enterprise: EnterpriseConfig): void {
        let enterprises = this.enterprises
        if (enterprises === null) return

        let enterpriseIndex = enterprises.findIndex(enterprise => enterprise.id === id);
        if (enterpriseIndex === -1) return

        let updateEnterprise = enterprises[enterpriseIndex];

        for (const [key, value] of Object.entries(enterprise)) {
            updateEnterprise.updateWithKeyString(key, value);
        }
        enterprises[enterpriseIndex] = updateEnterprise
        this.enterprises = enterprises
    }

    removeEnterprise(id: number): void {
        let enterprises = this.enterprises
        if (enterprises === null) return
        this.enterprises = enterprises.filter(enterprise => enterprise.id !== id);
    }

    set employments(employments: Employment[]) {
        localStorage.setItem('db_employments', JSON.stringify(employments))
    }

    get employments(): Employment[] | null {
        let employmentsData: Employment[] | null = null;

        const storedEmployment = localStorage.getItem('db_employments');
        if (storedEmployment) {
            employmentsData = JSON.parse(storedEmployment) as Employment[];
        }

        let employments: Employment[] = []

        if (employmentsData) {
            employmentsData.forEach((employmentData) => {
                employments.push(new Employment(employmentData))
            });
        }

        return employments ? employments : null
    }

    addEmployment(employment: Employment): void {
        let employments = this.employments
        if (employments === null) return
        employments.push(employment)
        this.employments = employments
    }

    getEmployment(id: number){
        let employments = this.employments
        if (employments === null) return undefined

        const result = employments.filter(employment => employment.id === id)

        if (result.length > 0){
            return result[0]
        } else {
            return undefined
        }
    }

    updateEmployment(id: number, employment: EmploymentConfig): void {
        let employments = this.employments
        if (employments === null) return

        let employmentIndex = employments.findIndex(employment => employment.id === id);
        if (employmentIndex === -1) return

        let updateEmployment = employments[employmentIndex];

        for (const [key, value] of Object.entries(employment)) {
            updateEmployment.updateWithKeyString(key, value);
        }
        employments[employmentIndex] = updateEmployment
        this.employments = employments
    }

    removeEmployment(id: number): void {
        let employments = this.employments
        if (employments === null) return
        this.employments = employments.filter(employment => employment.id !== id);
    }
}