package network;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class ClientApp {

    private Stage stage;
    private ClientNetwork clientNetwork;
    private String username;
    private ListView<Book> bookList;

    public ClientApp(Stage stage, ClientNetwork clientNetwork, String username) {
        this.stage = stage;
        this.clientNetwork = clientNetwork;
        this.username = username;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Button showAllBooksButton = new Button("Показать все книги");
        Button showBooksByRegionButton = new Button("Показать книги по региону");
        Button buyBookButton = new Button("Купить выбранную книгу");
        Button exitButton = new Button("Выйти");

        root.getChildren().addAll(showAllBooksButton, showBooksByRegionButton, buyBookButton, exitButton);

        showAllBooksButton.setOnAction(e -> showAllBooks());
        showBooksByRegionButton.setOnAction(e -> showBooksByRegion());
        buyBookButton.setOnAction(e -> {
            if (bookList == null) {
                showAlert("Ошибка", "Сначала открой список книг!");
                return;
            }
            Book selectedBook = bookList.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                clientNetwork.purchaseBook(username, selectedBook);
                showAlert("Покупка", "Вы купили книгу: " + selectedBook.getTitle());
            } else {
                showAlert("Ошибка", "Выберите книгу для покупки!");
            }
        });

        exitButton.setOnAction(e -> {
            clientNetwork.exit();
            stage.close();
        });

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Клиент Book Store");
        stage.setScene(scene);
        stage.show();
    }

    private void showAllBooks() {
        Stage bookStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Доступные книги:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        bookList = new ListView<>();
        ObservableList<Book> books = FXCollections.observableArrayList(
                clientNetwork.getAllBooks()
        );
        bookList.setItems(books);

        bookList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getTitle() + " (" + item.getRegion() + ")");
            }
        });

        ImageView coverView = new ImageView();
        coverView.setFitWidth(200);
        coverView.setPreserveRatio(true);

        bookList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection.getCoverUrl() != null) {
                try {
                    Image image = new Image(newSelection.getCoverUrl());
                    coverView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        root.getChildren().addAll(title, bookList, coverView);

        Scene scene = new Scene(root, 400, 600);
        bookStage.setTitle("Просмотр Книг");
        bookStage.setScene(scene);
        bookStage.show();
    }

    private void showBooksByRegion() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Выберите регион");
        dialog.setHeaderText(null);
        dialog.setContentText("Введите регион:");
        dialog.showAndWait().ifPresent(region -> {
            List<Book> books = clientNetwork.getBooksByRegion(region);
            if (books.isEmpty()) {
                showAlert("Книги региона " + region, "Нет доступных книг.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Book b : books) {
                    sb.append(b.getDetails()).append("\n");
                }
                showAlert("Книги региона " + region, sb.toString().trim());
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
