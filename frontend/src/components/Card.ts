export default class Card {
    getCard(): string {

        console.log("carregou modulo Card")

        let name:string = "hidden name"
        let state:string = "São Paulo"
        let country:string = "BR"
        let skills:string[] = ["Javascript"]
        let description:string = "With supporting text below as a natural lead-in to additional content."


        return `
        <div class="card">
            <div class="card-body">
                <div class="d-flex align-items-center">
                    <i class="bi bi-person-fill fs-1 me-2"></i>
                    <h5 class="card-title">${name}</h5>
                </div>
                <div class="d-flex align-items-center">
                    <i class="bi bi-pin-map-fill  fs-6 me-2"></i>
                    <p class="m-0">${state} - ${country}</p>
                </div>
                ${
                    skills.map(skill => {
                        return `<span class="badge text-bg-secondary">${skill}</span>`
                        
                    })
                }
                <p class="card-text">${description}</p>
                <div class="d-grid">
                    <button class="btn btn-primary" type="button">Curtir candidato</button>
                </div>
            </div>
        </div>
        `
    }
}


