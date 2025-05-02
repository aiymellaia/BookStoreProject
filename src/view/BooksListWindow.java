package view;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

import java.util.List;

public class BooksListWindow {

    private Stage stage;
    private List<Book> books;

    public BooksListWindow(List<Book> books) {
        this.stage = new Stage();
        this.books = books;
    }

    public void show() {
        stage.setTitle("Книги региона");

        ListView<String> listView = new ListView<>();
        for (Book book : books) {
            listView.getItems().add(book.getDetails());
        }

        VBox vbox = new VBox(10, listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
