package network;

import model.Book;
import service.DatabaseManager;

import java.io.*;
import java.net.*;
import java.util.List;

import static java.io.FileDescriptor.in;
import static java.io.FileDescriptor.out;

public class Server {

    private static final int PORT = 12345; // Порт для сокет-соединения
    private ServerSocket serverSocket;
    private DatabaseManager databaseManager;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            this.databaseManager = new DatabaseManager();
            System.out.println("Сервер запущен на порту " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                // Ожидаем подключения клиента
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключен новый клиент: " + clientSocket.getInetAddress());

                // Создаем новый поток для обслуживания клиента
                new ClientHandler(clientSocket, databaseManager).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private DatabaseManager databaseManager;
    private boolean authenticated = false;


    public ClientHandler(Socket socket, DatabaseManager databaseManager) {
        this.socket = socket;
        this.databaseManager = databaseManager;
    }

    @Override
    public void run() {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String message;
            if (!authenticated) {
                if (message.startsWith("LOGIN")) {
                    String[] parts = message.split(" ");
                    String username = parts[1];
                    String password = parts[2];
                    if (userService.authenticate(username, password)) {
                        output.println("LOGIN_SUCCESS");
                        authenticated = true;
                    } else {
                        output.println("LOGIN_FAIL");
                    }
                }
                continue;
            }

            while ((message = input.readLine()) != null) {
                if (message.equals("GET_BOOKS")) {
                    // Получаем все книги из базы данных
                    List<Book> books = databaseManager.getAllBooks();
                    for (Book book : books) {
                        output.println(book.getDetails()); // Отправляем клиенту детали книги
                    }
                }
                else if (message.equals("register")) {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    boolean success = userService.register(username, password);
                    out.writeObject(success);
                }
                else if (message.equals("purchaseBook")) {
                    String username = (String) in.readObject();
                    int bookId = (Integer) in.readObject();
                    userService.purchaseBook(username, bookId);
                }


                // Можно добавить другие команды, например, покупка книги
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
