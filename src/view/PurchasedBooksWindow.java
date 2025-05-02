package view;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import network.ClientNetwork;

import java.util.List;

public class PurchasedBooksWindow {

    private Stage stage;
    private String username;
    private ClientNetwork clientNetwork;

    public PurchasedBooksWindow(Stage stage, String username, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.username = username;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        stage.setTitle("Мои покупки");

        ListView<String> listView = new ListView<>();
        List<Book> books = clientNetwork.getPurchasedBooks(username);
        for (Book book : books) {
            listView.getItems().add(book.getDetails());
        }

        VBox vbox = new VBox(10, listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
