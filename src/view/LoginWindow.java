package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import network.ClientNetwork;

import java.io.IOException;

public class LoginWindow {

    private Stage stage;
    private ClientNetwork clientNetwork;

    public LoginWindow(Stage stage, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}