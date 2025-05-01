package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_db_name";
    private static final String USER = "your_db_user";
    private static final String PASSWORD = "your_db_password";

    public boolean authenticate(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Если нашли пользователя, значит всё ок
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Проверим, есть ли уже пользователь с таким именем
            String checkSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // Такой пользователь уже есть
            }

            // Если нет — регистрируем
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void purchaseBook(String username, int bookId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO purchases (username, book_id, purchase_date) VALUES (?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
