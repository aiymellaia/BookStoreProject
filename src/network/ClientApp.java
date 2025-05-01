package ui;

import javafx.application.Application;
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
import network.ClientNetwork;

public class ClientApp extends Application {

    private ClientNetwork clientNetwork;

    @Override
    public void start(Stage primaryStage) {
        clientNetwork = new ClientNetwork();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Button showAllBooksButton = new Button("Показать все книги");
        Button showBooksByRegionButton = new Button("Показать книги по региону");
        Button buyBookButton = new Button("Купить книгу");
        Button registerButton = new Button("Зарегистрироваться");
        Button exitButton = new Button("Выйти");

        root.getChildren().addAll(showAllBooksButton, showBooksByRegionButton, buyBookButton, exitButton);

        showAllBooksButton.setOnAction(e -> showAllBooks());
        showBooksByRegionButton.setOnAction(e -> showBooksByRegion());
        buyBookButton.setOnAction(e -> {
            Book selectedBook = bookList.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                clientNetwork.purchaseBook(currentUsername, selectedBook.getId());
                showAlert("Покупка", "Вы купили книгу: " + selectedBook.getTitle());
            } else {
                showAlert("Ошибка", "Выберите книгу для покупки!");
            }
        });
        registerButton.setOnAction(e -> {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    if (username.isEmpty() || password.isEmpty()) {
                        showAlert("Ошибка", "Поля не должны быть пустыми!");
                        return;
                    }
                    boolean registered = clientNetwork.register(username, password);
                    if (registered) {
                        showAlert("Успех", "Вы успешно зарегистрировались!");
                    } else {
                        showAlert("Ошибка", "Пользователь уже существует.");
                    }
                });

        exitButton.setOnAction(e -> {
            clientNetwork.exit();
            primaryStage.close();
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Клиент Book Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showAllBooks() {
        Stage bookStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Доступные книги:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<Book> bookList = new ListView<>();
        ObservableList<Book> books = FXCollections.observableArrayList(
                clientNetwork.getAllBooks()
        );
        bookList.setItems(books);

        // Для отображения названия книги
        bookList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " (" + item.getRegion() + ")");
                }
            }
        });

        // Область для показа обложки
        ImageView coverView = new ImageView();
        coverView.setFitWidth(200);
        coverView.setPreserveRatio(true);

        // При выборе книги показываем обложку
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
            String books = clientNetwork.getBooksByRegion(region);
            showAlert("Книги региона " + region, books);
        });
    }

    private void buyBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Покупка книги");
        dialog.setHeaderText(null);
        dialog.setContentText("Введите название книги для покупки:");
        dialog.showAndWait().ifPresent(title -> {
            String result = clientNetwork.buyBook(title);
            showAlert("Результат покупки", result);
        });
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
