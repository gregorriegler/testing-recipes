package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryShelveTest extends ContractTest {

    @Override
    protected BookShelve emptyShelve() {
        // 3 Ansätze:
        // - Mockito (schnell)
        // - Anonymous inner class oder lambda (minimaler Code)
        //   Ich will das TestApi und die Testautomatisierung und das Building treiben
        // - Fake, InmemoryRepository
        //   Ich schreibe sofort ein InmemoryRepository weil ich weiß, dass das in anderen integrativen Tests vorkommen würde.
        return new BookShelve() {

            private final List<Book> books = new ArrayList<>();

            @Override
            public List<Book> findAll() {
                return books;
            }

            @Override
            public void add(Book book) {
                books.add(book);
            }

            // Möglichkeiten für no match
            //  - null
            //  - throw exception
            //  - Optional
            //  - Empty List
            //  - MaybeBook (wie Optional aber Domainspezifisch)
            //  - NullBook (Null Object)
            //  - ResultObject (enthält z.b. Query und FehlerMessage und Ergebnis)
            @Override
            public Optional<Book> findByIsbn(String isbn) {
                // durch diese logik wird es zu einem Fake, davor war es nur ein Stub
                return books.stream()
                    .filter(book -> isbn.equals(book.isbn))
                    .findFirst();
            }
        };
    }
}