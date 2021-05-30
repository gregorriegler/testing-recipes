package contract.composition;

import org.junit.jupiter.api.DynamicNode;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * reusable contract, to be used in a @TestFactory
 * @see InMemoryBookShelveTest
 */
public class BookShelveContract {

    private final BookShelveFixtures fixtures;

    public BookShelveContract(BookShelveFixtures fixtures) {
        this.fixtures = fixtures;
    }

    public Collection<DynamicNode> get() {
        return List.of(
            dynamicTest("starts empty", () -> {
                    BookShelve emptyShelve = fixtures.emptyShelve();

                    List<Book> books = emptyShelve.findAll();

                    assertThat(books).isEmpty();
                }
            ),
            dynamicTest("can add a book", () -> {
                BookShelve shelve = fixtures.emptyShelve();

                shelve.add(new Book("irrelevant"));

                List<Book> books = shelve.findAll();
                assertThat(books).containsExactly(new Book("irrelevant"));
            }),
            dynamicContainer("can find book by isbn", List.of(
                dynamicTest("no match", () -> {
                    BookShelve shelve = fixtures.emptyShelve();
                    shelve.add(new Book("1"));

                    Optional<Book> book = shelve.findByIsbn("2");

                    assertThat(book).isEmpty();
                }),
                dynamicTest("match", () -> {
                    BookShelve shelve = fixtures.emptyShelve();
                    shelve.add(new Book("1"));

                    Optional<Book> book = shelve.findByIsbn("1");

                    assertThat(book).contains(new Book("1"));
                })
            )));
    }
}
