import Card from "../components/Card"
import Chart from "../components/Chart"
import Nav from "../components/Nav"
import {ProfileEnterprise, ProfileCandidate} from "../components/Profile"

interface ComponentsConfig {
    card: Card
    chart: Chart
    nav: Nav
    profileEnterprise: ProfileEnterprise
    profileCandidate: ProfileCandidate
}

// export default class Components {
//
//     private readonly card
//     private readonly chart
//     private readonly nav
//     private readonly profileEnterprise
//     private readonly profileCandidate
//
//     constructor({
//                     card,
//                     chart,
//                     nav,
//                     profileEnterprise,
//                     profileCandidate
//                 }:ComponentsConfig) {
//         this.card = card
//         this.chart = chart
//         this.nav = nav
//         this.profileEnterprise = profileEnterprise
//         this.profileCandidate = profileCandidate
//     }
// }

export default class Components {

    public readonly card

    constructor(card: Card) {
        this.card = card
    }
}