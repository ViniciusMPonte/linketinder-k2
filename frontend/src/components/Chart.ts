declare function startChart(tag: HTMLCanvasElement, labels: string[], data: number[]): void;

export function initChart(tag: HTMLCanvasElement, labels: string[], data: number[]): void {
    const canvas = tag as HTMLCanvasElement;
    if (canvas) {
        startChart(canvas, labels, data);
    }
}
