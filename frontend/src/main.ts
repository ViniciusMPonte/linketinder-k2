import Card from './components/Card';

console.log("entrou na main")

const cardComponent = new Card();

innerHTMLInject(document.querySelector('.card'), cardComponent.getCard());

function innerHTMLInject(tag: HTMLElement | null, output: string): void {
    console.log("chamou innerHTMLInject")

    const tagTarget = tag as HTMLElement | null;
    if (tagTarget) {
        tagTarget.innerHTML = output;
        console.log("incluiu no html atraves do innerHTMLInject")
    }
}
