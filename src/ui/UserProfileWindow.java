import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserProfileWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Профиль пользователя");

        VBox vbox = new VBox(10);
        Label labelName = new Label("Имя пользователя: Иван Иванов");
        Label labelEmail = new Label("Email: ivan@example.com");
        Label labelPurchasedBooks = new Label("Купленные книги: \nКнига 1\nКнига 2");

        vbox.getChildren().addAll(labelName, labelEmail, labelPurchasedBooks);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
