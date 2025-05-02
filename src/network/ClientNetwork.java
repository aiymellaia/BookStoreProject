// ClientNetwork.java
package network;

import model.Book;
import model.EBook;
import model.PrintedBook;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientNetwork {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientNetwork() {
        try {
            socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User validateUser(String username, String password) {
        try {
            out.writeObject("LOGIN");
            out.writeObject(username);
            out.writeObject(password);
            Object response = in.readObject();
            if (response instanceof User user) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String username, String password) {
        try {
            out.writeObject("REGISTER");
            out.writeObject(username);
            out.writeObject(password);
            return (Boolean) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean purchaseBook(String username, Book book) {
        try {
            out.writeObject("PURCHASE_BOOK");
            out.writeObject(username);
            out.writeObject(book);
            return (Boolean) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooks() {
        try {
            out.writeObject("GET_BOOKS");
            return (List<Book>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Book> getBooksByRegion(String region) {
        try {
            out.writeObject("GET_BOOKS_BY_REGION");
            out.writeObject(region);
            return (List<Book>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Book> getPurchasedBooks(String username) {
        try {
            out.writeObject("GET_PURCHASED_BOOKS");
            out.writeObject(username);
            return (List<Book>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void exit() {
        try {
            out.writeObject("EXIT");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
