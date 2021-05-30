package contract.composition;

import java.util.List;
import java.util.Optional;

interface BookShelve {
    List<Book> findAll();

    void add(Book book);

    Optional<Book> findByIsbn(String isbn);
}


