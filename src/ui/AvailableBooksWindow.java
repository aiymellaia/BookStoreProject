package ui;

import client.ClientNetwork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class AvailableBooksWindow {

    private Stage stage;
    private String username;
    private ClientNetwork clientNetwork;
    private String region;

    public AvailableBooksWindow(Stage stage, String username, ClientNetwork clientNetwork, String region) {
        this.stage = stage;
        this.username = username;
        this.clientNetwork = clientNetwork;
        this.region = region;
    }

    public void show() {
        ListView<Book> booksListView = new ListView<>();
        ObservableList<Book> items = FXCollections.observableArrayList();

        // Получение всех книг выбранного региона от сервера
        List<Book> books = clientNetwork.getBooksByRegion(region);
        items.addAll(books);
        booksListView.setItems(items);

        booksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getDetails() + " — " + book.getPrice() + " ₸");
                }
            }
        });

        Button buyButton = new Button("🛒 Купить");
        buyButton.setDisable(true); // По умолчанию кнопка выключена

        // Включаем кнопку только если выбрали книгу
        booksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            buyButton.setDisable(newSelection == null);
        });

        buyButton.setOnAction(e -> {
            Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = clientNetwork.purchaseBook(username, selectedBook);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Успех", "Книга успешно куплена!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось купить книгу.");
                }
            }
        });

        Button backButton = new Button("🔙 Назад");
        backButton.setOnAction(e -> {
            RegionSelectionWindow regionSelection = new RegionSelectionWindow(stage, username, clientNetwork);
            regionSelection.show();
        });

        HBox buttonBox = new HBox(10, buyButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(booksListView);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("📚 Книги региона: " + region);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
