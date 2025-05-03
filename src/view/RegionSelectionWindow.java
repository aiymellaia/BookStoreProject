package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        stage.setTitle("📍 Выбор региона");

        // Основной контейнер
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Arial';");

        // Заголовок окна
        Label title = new Label("Выберите регион:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Кнопки выбора региона
        for (String region : regions) {
            Button regionButton = new Button(region);
            regionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
            regionButton.setOnAction(e -> showBooksByRegion(region));
            vbox.getChildren().add(regionButton);
        }

        // Добавление заголовка и кнопок в интерфейс
        vbox.getChildren().add(0, title);

        // Сцена
        Scene scene = new Scene(vbox, 350, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void showBooksByRegion(String region) {
        List<Book> books = clientNetwork.getBooksByRegion(region);
        BooksListWindow booksListWindow = new BooksListWindow(books);
        booksListWindow.show(); // метод show() должен быть в BooksListWindow
    }
}
