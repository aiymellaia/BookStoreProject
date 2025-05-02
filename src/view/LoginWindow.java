package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import network.ClientNetwork;

public class LoginWindow {

    private Stage stage;
    private ClientNetwork clientNetwork;

    public LoginWindow(Stage stage, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label userLabel = new Label("Логин:");
        TextField usernameField = new TextField();

        Label passLabel = new Label("Пароль:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Войти");
        Button registerButton = new Button("Зарегистрироваться");

        root.getChildren().addAll(userLabel, usernameField, passLabel, passwordField, loginButton, registerButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = clientNetwork.validateUser(username, password);
            if (user != null) {
                if ("admin".equals(user.getRole())) {
                    new AdminPanelWindow(new Stage()).show();
                } else {
                    new UserMainMenuWindow(new Stage(), username, clientNetwork).show();
                }
                stage.close();
            } else {
                showAlert("Ошибка", "Неверный логин или пароль!");
            }
        });

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Ошибка", "Введите логин и пароль!");
                return;
            }

            boolean success = clientNetwork.register(username, password);
            if (success) {
                showAlert("Успех", "Регистрация прошла успешно. Войдите в систему.");
            } else {
                showAlert("Ошибка", "Пользователь уже существует!");
            }
        });

        Scene scene = new Scene(root, 300, 280);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
