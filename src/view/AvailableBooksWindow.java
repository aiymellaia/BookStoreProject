package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Book;
import network.ClientNetwork;

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

        List<Book> books = clientNetwork.getBooksByRegion(region);
        items.addAll(books);
        booksListView.setItems(items);

        booksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                setText((empty || book == null) ? null : book.getDetails() + " — " + book.getPrice() + " ₸");
            }
        });

        Button buyButton = new Button("🛒 Купить");
        buyButton.setDisable(true);

        booksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            buyButton.setDisable(newSel == null);
        });

        buyButton.setOnAction(e -> {
            Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = clientNetwork.purchaseBook(username, selectedBook);
                showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                        success ? "Успех" : "Ошибка",
                        success ? "Книга успешно куплена!" : "Не удалось купить книгу.");
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