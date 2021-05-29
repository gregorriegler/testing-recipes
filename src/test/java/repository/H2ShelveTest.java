package repository;

import java.util.List;
import java.util.Optional;

public class H2ShelveTest extends ContractTest {

    @Override
    protected BookShelve emptyShelve() {
        return new BookShelveRepository();
    }

    private static class BookShelveRepository implements BookShelve {
        @Override
        public List<Book> findAll() {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public void add(Book book) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public Optional<Book> findByIsbn(String isbn) {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }
}