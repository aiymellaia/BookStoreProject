package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.ClientNetwork;

public class LoginWindow extends Application {

    private ClientNetwork clientNetwork;

    @Override
    public void start(Stage primaryStage) {
        clientNetwork = new ClientNetwork();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label userLabel = new Label("Логин:");
        TextField usernameField = new TextField();

        Label passLabel = new Label("Пароль:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Войти");

        root.getChildren().addAll(userLabel, usernameField, passLabel, passwordField, loginButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean success = clientNetwork.login(username, password);
            if (success) {
                new ClientApp(clientNetwork).start(new Stage());
                primaryStage.close();
            } else {
                showAlert("Ошибка", "Неверный логин или пароль!");
            }
        });

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
