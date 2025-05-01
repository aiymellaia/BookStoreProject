import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import service.DatabaseManager;

import java.util.List;

public class PurchasedBooksWindow extends Application {
    private int userId = 1; // Для примера, будет получен из текущей сессии пользователя
    private DatabaseManager dbManager = new DatabaseManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Мои покупки");

        ListView<String> listView = new ListView<>();
        List<Book> purchasedBooks = dbManager.getPurchasedBooks(userId);
        for (Book book : purchasedBooks) {
            listView.getItems().add(book.getDetails());
        }

        VBox vbox = new VBox(10, listView);
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
