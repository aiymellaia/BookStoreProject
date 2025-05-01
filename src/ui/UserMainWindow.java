import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserMainWindow extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("BookStore");

        Button btnSelectRegion = new Button("Выбрать регион");
        btnSelectRegion.setOnAction(e -> showRegionSelectionWindow());

        Button btnMyPurchases = new Button("Мои покупки");
        btnMyPurchases.setOnAction(e -> showPurchasedBooksWindow());

        VBox vbox = new VBox(10, btnSelectRegion, btnMyPurchases);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegionSelectionWindow() {
        RegionSelectionWindow regionSelectionWindow = new RegionSelectionWindow();
        regionSelectionWindow.start(new Stage());
    }

    private void showPurchasedBooksWindow() {
        PurchasedBooksWindow purchasedBooksWindow = new PurchasedBooksWindow();
        purchasedBooksWindow.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
