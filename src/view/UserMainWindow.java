package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.ClientNetwork;

public class UserMainWindow {

    private Stage stage;
    private String username;
    private ClientNetwork clientNetwork;

    public UserMainWindow(Stage stage, String username, ClientNetwork clientNetwork) {
        this.stage = stage;
        this.username = username;
        this.clientNetwork = clientNetwork;
    }

    public void show() {
        Button btnSelectRegion = new Button("Выбрать регион");
        btnSelectRegion.setOnAction(e -> new RegionSelectionWindow(stage, username, clientNetwork).show());

        Button btnMyPurchases = new Button("Мои покупки");
        btnMyPurchases.setOnAction(e -> new PurchasedBooksWindow(stage, username, clientNetwork).show());

        VBox vbox = new VBox(10, btnSelectRegion, btnMyPurchases);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle("Главное меню пользователя");
        stage.setScene(scene);
        stage.show();
    }
}
