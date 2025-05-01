import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class BooksListWindow extends Application {
    private List<Book> books;

    public BooksListWindow(List<Book> books) {
        this.books = books;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Книги региона");

        ListView<String> listView = new ListView<>();
        for (Book book : books) {
            listView.getItems().add(book.getDetails());
        }

        VBox vbox = new VBox(10, listView);
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
