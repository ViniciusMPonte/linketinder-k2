package tests.utils

class FakeScanner {

    def responses = []
    def iterator
    public def fakeInput

    FakeScanner(responses) {
        this.responses = responses
        this.iterator = responses.iterator()
        this.fakeInput = new Scanner("")
        this.fakeInput.metaClass.nextLine = {
            ->
            iterator.hasNext() ? iterator.next() : null
        }
    }
}
