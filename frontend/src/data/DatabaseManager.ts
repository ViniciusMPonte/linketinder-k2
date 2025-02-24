import {Candidate, CandidateConfig} from "../entities/Candidate";
import CandidatesData from "./CandidatesData";

export default class DatabaseManager {
    constructor() {
        if (!localStorage.getItem('db_candidates')) {
            localStorage.setItem('db_candidates', JSON.stringify(CandidatesData.getInfos()))
        }
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
}