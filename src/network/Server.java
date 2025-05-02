package network;
import model.Book;
import service.DatabaseManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.List;
import model.User;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private DatabaseManager databaseManager;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            this.databaseManager = new DatabaseManager();
            System.out.println("✅ Сервер запущен на порту " + PORT);
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("🔌 Клиент подключён: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, databaseManager).start();
            } catch (IOException e) {
                System.err.println("Ошибка при подключении клиента: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

class ClientHandler extends Thread {

    private final Socket socket;
    private final DatabaseManager db;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, DatabaseManager db) {
        this.socket = socket;
        this.db = db;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object commandObj = in.readObject();
                if (commandObj == null) break;

                String command = commandObj.toString();
                switch (command) {
                    case "LOGIN" -> handleLogin();
                    case "REGISTER" -> handleRegister();
                    case "GET_BOOKS" -> handleGetBooks();
                    case "GET_BOOKS_BY_REGION" -> handleGetBooksByRegion();
                    case "GET_PURCHASED_BOOKS" -> handleGetPurchasedBooks();
                    case "PURCHASE_BOOK" -> handlePurchaseBook();
                    case "EXIT" -> {
                        socket.close();
                        return;
                    }
                    default -> out.writeObject(false);
                }
            }
        } catch (Exception e) {
            System.out.println("Клиент отключён: " + e.getMessage());
        }
    }

    private void handleLogin() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        User user = db.getUserByCredentials(username, password);
        out.writeObject(user);
    }

    private void handleRegister() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        System.out.println("Регистрация пользователя: " + username + " | Пароль: " + password);

        boolean success = db.createUser(username, password);
        out.writeObject(success);
    }

    private void handleGetBooks() throws IOException {
        List<Book> books = db.getAllBooks();
        out.writeObject(books);
    }

    private void handleGetBooksByRegion() throws IOException, ClassNotFoundException {
        String region = (String) in.readObject();
        List<Book> books = db.getBooksByRegion(region);
        out.writeObject(books);
    }

    private void handleGetPurchasedBooks() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        List<Book> books = db.getPurchasedBooksByUsername(username);
        out.writeObject(books);
    }

    private void handlePurchaseBook() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Book book = (Book) in.readObject();
        boolean success = db.purchaseBook(username, book);
        out.writeObject(success);
    }
}
