package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.DatabaseManager;

public class DeleteBookWindow {

    private final Stage stage;
    private final DatabaseManager dbManager = new DatabaseManager();

    public DeleteBookWindow(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("❌ Удаление книги");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Arial';");

        // Инструкция
        Label instruction = new Label("Введите название книги для удаления:");
        instruction.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Поле для ввода
        TextField titleField = new TextField();
        titleField.setPromptText("Название книги");
        titleField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-border-color: #d3d3d3;");

        // Кнопка удаления
        Button deleteButton = new Button("❌ Удалить");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-border-radius: 5px;");

        // Список статуса
        ListView<String> status = new ListView<>();
        status.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d3d3d3; -fx-border-width: 1px; -fx-font-size: 14px;");

        // Обработчик события для кнопки
        deleteButton.setOnAction(e -> {
            String title = titleField.getText();
            boolean success = dbManager.deleteBookByTitle(title);
            if (success) {
                status.getItems().add("✅ Книга удалена: " + title);
            } else {
                status.getItems().add("⚠ Книга не найдена или ошибка.");
            }
        });

        root.getChildren().addAll(instruction, titleField, deleteButton, status);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
