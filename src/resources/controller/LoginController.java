package resources.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import network.ClientNetwork;
import view.AdminPanelWindow;
import view.UserMainMenuWindow;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private final ClientNetwork clientNetwork = new ClientNetwork();

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> login());
        registerButton.setOnAction(e -> register());
    }

    private void login() {
        String username = loginField.getText();
        String password = passwordField.getText();
        User user = clientNetwork.validateUser(username, password);
        if (user != null) {
            if ("admin".equals(user.getRole())) {
                new AdminPanelWindow(new Stage()).show();
            } else {
                new UserMainMenuWindow(new Stage(), username, clientNetwork).show();
            }
            getStage().close();
        } else {
            showAlert("Ошибка", "Неверный логин или пароль!");
        }
    }

    private void register() {
        String username = loginField.getText();
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
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Stage getStage() {
        return (Stage) loginField.getScene().getWindow();
    }
}
