package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String email;
    private List<Book> purchasedBooks;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.purchasedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Book> getPurchasedBooks() { return purchasedBooks; }

    public void addPurchasedBook(Book book) {
        purchasedBooks.add(book);
    }
}
