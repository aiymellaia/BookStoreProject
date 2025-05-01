package service;

import model.Book;
import model.EBook;
import model.PrintedBook;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5433/BookStore";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к базе данных установлено.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- КНИГИ ----------------

    // C - Create (Добавить книгу)
    public void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author, region, price, type, numberOfPages, downloadLink) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getRegion());
            pstmt.setDouble(4, book.getPrice());

            if (book instanceof PrintedBook printedBook) {
                pstmt.setString(5, "PrintedBook");
                pstmt.setInt(6, printedBook.getNumberOfPages());
                pstmt.setNull(7, Types.VARCHAR);
            } else if (book instanceof EBook eBook) {
                pstmt.setString(5, "EBook");
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setString(7, eBook.getDownloadLink());
            } else {
                pstmt.setString(5, "Unknown");
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.VARCHAR);
            }

            pstmt.executeUpdate();
            System.out.println("Книга добавлена успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // R - Read (Получить все книги)
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String region = rs.getString("region");
                double price = rs.getDouble("price");

                if ("PrintedBook".equalsIgnoreCase(type)) {
                    int numberOfPages = rs.getInt("numberOfPages");
                    books.add(new PrintedBook(title, author, region, price, numberOfPages));
                } else if ("EBook".equalsIgnoreCase(type)) {
                    String downloadLink = rs.getString("downloadLink");
                    books.add(new EBook(title, author, region, price, downloadLink));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // U - Update (Обновить книгу по ID)
    public void updateBook(int id, Book book) {
        String sql = "UPDATE books SET title=?, author=?, region=?, price=?, type=?, numberOfPages=?, downloadLink=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getRegion());
            pstmt.setDouble(4, book.getPrice());

            if (book instanceof PrintedBook printedBook) {
                pstmt.setString(5, "PrintedBook");
                pstmt.setInt(6, printedBook.getNumberOfPages());
                pstmt.setNull(7, Types.VARCHAR);
            } else if (book instanceof EBook eBook) {
                pstmt.setString(5, "EBook");
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setString(7, eBook.getDownloadLink());
            } else {
                pstmt.setString(5, "Unknown");
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.VARCHAR);
            }

            pstmt.setInt(8, id);
            pstmt.executeUpdate();
            System.out.println("Книга обновлена успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // D - Delete (Удалить книгу по ID)
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Книга удалена успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- ПОЛЬЗОВАТЕЛИ ----------------

    // C - Create (Добавить пользователя)
    public void insertUser(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();
            System.out.println("Пользователь добавлен успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // R - Read (Получить всех пользователей)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(name, email));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // U - Update (Обновить пользователя по ID)
    public void updateUser(int id, User user) {
        String sql = "UPDATE users SET name=?, email=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Пользователь обновлен успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // D - Delete (Удалить пользователя по ID)
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Пользователь удален успешно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение списка книг по региону
    public List<Book> getBooksByRegion(String region) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE region = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, region);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String type = rs.getString("type");
                if (type.equals("Printed")) {
                    int numberOfPages = rs.getInt("numberOfPages");
                    books.add(new PrintedBook(title, author, region, price, numberOfPages));
                } else {
                    String downloadLink = rs.getString("downloadLink");
                    books.add(new EBook(title, author, region, price, downloadLink));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Получение списка купленных книг пользователя
    public List<Book> getPurchasedBooks(int userId) {
        List<Book> purchasedBooks = new ArrayList<>();
        String sql = "SELECT b.title, b.author, b.price, b.type, b.numberOfPages, b.downloadLink " +
                "FROM books b " +
                "JOIN purchases p ON b.id = p.book_id " +
                "WHERE p.user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String type = rs.getString("type");
                if (type.equals("Printed")) {
                    int numberOfPages = rs.getInt("numberOfPages");
                    purchasedBooks.add(new PrintedBook(title, author, "Unknown", price, numberOfPages));
                } else {
                    String downloadLink = rs.getString("downloadLink");
                    purchasedBooks.add(new EBook(title, author, "Unknown", price, downloadLink));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchasedBooks;
    }

    // Закрыть соединение
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Соединение с базой данных закрыто.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
