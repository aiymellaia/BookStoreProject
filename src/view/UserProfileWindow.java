package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class UserProfileWindow {

    private Stage stage;
    private User user;

    public UserProfileWindow(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() {
        stage.setTitle("Профиль пользователя");

        Label nameLabel = new Label("Имя: " + user.getName());
        Label emailLabel = new Label("Email: " + user.getEmail());

        VBox layout = new VBox(10, nameLabel, emailLabel);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);
        stage.show();
    }
}
