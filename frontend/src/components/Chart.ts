import {Candidate} from "../entities/Candidate";

declare function startChart(tag: HTMLCanvasElement, labels: string[], data: number[]): void;

export default class Chart {

    static build(tag: HTMLCanvasElement, labels: string[], data: number[]): void {
        const canvas = tag as HTMLCanvasElement;
        if (canvas) {
            startChart(canvas, labels, data);
        }
    }

    static countCandidateSkills(candidates: Candidate[]): Record<string, number> {
        const skillCounts: Record<string, number> = {};

        for (const candidate of candidates) {
            const uniqueSkills = new Set(candidate.skills);
            for (const skill of uniqueSkills) {
                skillCounts[skill] = (skillCounts[skill] || 0) + 1;
            }
        }

        return skillCounts;
    }

}