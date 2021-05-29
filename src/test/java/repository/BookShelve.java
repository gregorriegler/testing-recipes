package repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// repository.BookShelve (innerhalb des Boundaries)
// - starts empty
// - can add a book (isbne)
// - can find a book by isbn
// - can find all books
// - can remove a book
interface BookShelve {
    List<Book> findAll();

    void add(Book book);

    Optional<Book> findByIsbn(String isbn);
}

class Book {
    public final String isbn;

    public Book(String isbn) {
        this.isbn = isbn;
    }
}

// man kann den Test wiederverwenden
// vererbung in test vielleicht grauslich?
//  -> könnte create shelve über Dynamic Tests lösen (composition over inheritance)
// wenn man den H2 wegschiebt, wie hat er dann Zugriff auf den repository.ContractTest?
// wie fügen wir neue funktionalität für neue consumer hinzu? könnte ISP machen, kleinere fakes je nach consumer
abstract class ContractTest {

    @Test
    void starts_empty() {
        BookShelve shelve = emptyShelve();

        List<Book> books = shelve.findAll();

        assertThat(books).isEmpty();
    }

    @Test
    void can_add_a_book() {
        BookShelve shelve = emptyShelve();

        shelve.add(new Book("irrelevant"));

        List<Book> books = shelve.findAll();
        assertThat(books).hasSize(1);
        // TODO check book is the same
    }

    @Nested
    class can_find_book_by_isbn {

        @Test
        void no_match() {
            BookShelve shelve = emptyShelve();
            shelve.add(new Book("1"));

            Optional<Book> book = shelve.findByIsbn("2");

            assertThat(book).isEmpty();
        }

        @Test
        void match() {
            BookShelve shelve = emptyShelve();
            shelve.add(new Book("1"));

            Optional<Book> book = shelve.findByIsbn("1");

            assertThat(book).isNotEmpty();
            assertThat(book.get().isbn).isEqualTo("1");
        }

    }

    protected abstract BookShelve emptyShelve();
}


