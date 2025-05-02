// Main.java
import javafx.application.Application;
import javafx.stage.Stage;
import network.ClientNetwork;
import view.LoginWindow;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ClientNetwork clientNetwork = new ClientNetwork();
        LoginWindow loginWindow = new LoginWindow(primaryStage, clientNetwork);
        loginWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}