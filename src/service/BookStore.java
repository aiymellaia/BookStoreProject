package service;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookStore {
    private List<Book> books;

    public BookStore() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> findBooksByRegion(String region) {
        return books.stream()
                .filter(book -> book.getRegion().equalsIgnoreCase(region))
                .collect(Collectors.toList());
    }
}
