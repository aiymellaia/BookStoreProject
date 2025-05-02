package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import network.ClientNetwork;

import java.util.List;

public class RegionSelectionWindow {

    private Stage stage;
    private String username;
    private ClientNetwork clientNetwork;

    private List<String> regions = List.of("Регион 1", "Регион 2", "Регион 3");

    public RegionSelectionWindow(Stage stage, String username, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.username = username;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        VBox vbox = new VBox(10);

        for (String region : regions) {
            Button regionButton = new Button(region);
            regionButton.setOnAction(e -> showBooksByRegion(region));
            vbox.getChildren().add(regionButton);
        }

        Scene scene = new Scene(vbox, 300, 250);
        stage.setTitle("Выбор региона");
        stage.setScene(scene);
        stage.show();
    }

    private void showBooksByRegion(String region) {
        List<Book> books = clientNetwork.getBooksByRegion(region);
        BooksListWindow booksListWindow = new BooksListWindow(books);
        booksListWindow.show(); // метод show() должен быть в BooksListWindow
    }
}
