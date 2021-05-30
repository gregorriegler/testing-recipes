package contract.composition;

import java.util.Objects;

class Book {
    public final String isbn;

    public Book(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "{\n" +
            "  \"@class\": \"Book\",\n" +
            "  \"isbn\": \"" + isbn + "\",\n" +
            "}";
    }
}
