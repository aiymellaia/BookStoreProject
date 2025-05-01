import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import service.DatabaseManager;
import ui.BooksListWindow;

import java.util.List;

public class RegionSelectionWindow extends Application {
    private DatabaseManager dbManager = new DatabaseManager();

    private List<String> regions = List.of("Регион 1", "Регион 2", "Регион 3"); // Пример

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Выбор региона");

        VBox vbox = new VBox(10);
        for (String region : regions) {
            Button regionButton = new Button(region);
            regionButton.setOnAction(e -> showBooksByRegion(region));
            vbox.getChildren().add(regionButton);
        }

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showBooksByRegion(String region) {
        // Получаем книги по региону из базы данных
        List<Book> books = dbManager.getBooksByRegion(region);
        BooksListWindow booksListWindow = new BooksListWindow(books);
        booksListWindow.start(new Stage());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
