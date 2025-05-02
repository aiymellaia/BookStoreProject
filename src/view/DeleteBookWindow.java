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
        stage.setTitle("Удаление книги");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label instruction = new Label("Введите название книги для удаления:");
        TextField titleField = new TextField();
        Button deleteButton = new Button("❌ Удалить");
        ListView<String> status = new ListView<>();

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

        Scene scene = new Scene(root, 400, 250);
        stage.setScene(scene);
        stage.show();
    }
}
