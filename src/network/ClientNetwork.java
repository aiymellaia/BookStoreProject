package network;

import java.io.*;
import java.net.Socket;

import static java.io.FileDescriptor.in;
import static java.io.FileDescriptor.out;

public class ClientNetwork {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientNetwork() {
        try {
            socket = new Socket("localhost", 12345);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAllBooks() {
        output.println("GET_BOOKS");
        return readResponse();
    }

    public String getBooksByRegion(String region) {
        output.println("GET_BOOKS_BY_REGION " + region);
        return readResponse();
    }

    public String buyBook(String title) {
        output.println("BUY_BOOK " + title);
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при покупке книги.";
        }
    }

    public boolean login(String username, String password) {
        output.println("LOGIN " + username + " " + password);
        try {
            String response = input.readLine();
            return response.equals("LOGIN_SUCCESS");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exit() {
        try {
            output.println("EXIT");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readResponse() {
        StringBuilder response = new StringBuilder();
        try {
            String line;
            while (!(line = input.readLine()).equals("END")) {
                response.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public boolean register(String username, String password) {
        try {
            out.writeObject("register");
            out.writeObject(username);
            out.writeObject(password);
            return (Boolean) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void purchaseBook(String username, int bookId) {
        try {
            out.writeObject("purchaseBook");
            out.writeObject(username);
            out.writeObject(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
