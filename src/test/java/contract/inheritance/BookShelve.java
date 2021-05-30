package contract.inheritance;

import java.util.List;
import java.util.Optional;

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


