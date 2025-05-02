package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InitDatabase {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/BookStore";
        String user = "postgres";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100),
                    email VARCHAR(100) UNIQUE
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS books (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(255),
                    author VARCHAR(255),
                    region VARCHAR(100),
                    price DOUBLE PRECISION,
                    type VARCHAR(20),
                    numberOfPages INT,
                    downloadLink TEXT,
                    coverUrl TEXT
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS purchases (
                    id SERIAL PRIMARY KEY,
                    user_id INT REFERENCES users(id),
                    book_id INT REFERENCES books(id)
                );
            """);

            System.out.println("✅ Таблицы успешно созданы!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
