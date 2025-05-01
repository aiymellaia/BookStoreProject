package ui;

import client.ClientNetwork;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserMainMenuWindow {

    private Stage stage;
    private String username;
    private ClientNetwork clientNetwork;

    public UserMainMenuWindow(Stage stage, String username, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.username = username;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        Button browseRegionsButton = new Button("📚 Просмотреть книги по регионам");
        Button myPurchasesButton = new Button("🛒 Мои покупки");
        Button logoutButton = new Button("🔑 Выйти");

        browseRegionsButton.setPrefWidth(300);
        myPurchasesButton.setPrefWidth(300);
        logoutButton.setPrefWidth(300);

        browseRegionsButton.setOnAction(e -> {
            RegionSelectionWindow regionSelectionWindow = new RegionSelectionWindow(stage, username, clientNetwork);
            regionSelectionWindow.show();
        });

        myPurchasesButton.setOnAction(e -> {
            MyPurchasesWindow purchasesWindow = new MyPurchasesWindow(stage, username, clientNetwork);
            purchasesWindow.show();
        });

        logoutButton.setOnAction(e -> {
            LoginWindow loginWindow = new LoginWindow(stage, clientNetwork);
            loginWindow.show();
        });

        VBox root = new VBox(20, browseRegionsButton, myPurchasesButton, logoutButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Главное меню пользователя");
        stage.setScene(scene);
        stage.show();
    }
}
