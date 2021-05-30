package contract.composition;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;

class InMemoryBookShelveTest {

    @TestFactory
    Collection<DynamicNode> contract() {
        InMemoryBookShelveFixtures fixtures = new InMemoryBookShelveFixtures();
        return new BookShelveContract(fixtures).get();
    }
}
