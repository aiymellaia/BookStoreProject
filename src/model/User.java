package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String email;
    private String role;
    private List<Book> purchasedBooks;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = "user";
        this.purchasedBooks = new ArrayList<>();
    }

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.purchasedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public List<Book> getPurchasedBooks() { return purchasedBooks; }

    public void addPurchasedBook(Book book) {
        purchasedBooks.add(book);
    }
}
