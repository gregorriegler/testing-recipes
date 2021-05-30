package contract.composition;

public class InMemoryBookShelveFixtures implements BookShelveFixtures {
    @Override
    public BookShelve emptyShelve() {
        return new InMemoryBookShelve();
    }
}
