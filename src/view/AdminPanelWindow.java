package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Book;
import model.PrintedBook;
import model.EBook;
import service.DatabaseManager;

public class AdminPanelWindow {

    private final Stage stage;
    private final DatabaseManager dbManager = new DatabaseManager();

    public AdminPanelWindow(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Админ-панель: Добавление книги");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField regionField = new TextField();
        TextField priceField = new TextField();
        TextField pagesField = new TextField();
        TextField linkField = new TextField();
        TextField coverField = new TextField();

        Button addPrintedBook = new Button("Добавить печатную книгу");
        Button addEBook = new Button("Добавить eBook");

        ListView<String> statusList = new ListView<>();

        addPrintedBook.setOnAction(e -> {
            Book book = new PrintedBook(
                    titleField.getText(),
                    authorField.getText(),
                    regionField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(pagesField.getText()),
                    coverField.getText()
            );
            dbManager.insertBook(book);
            statusList.getItems().add("✅ Печатная книга добавлена: " + book.getTitle());
        });

        addEBook.setOnAction(e -> {
            Book book = new EBook(
                    titleField.getText(),
                    authorField.getText(),
                    regionField.getText(),
                    Double.parseDouble(priceField.getText()),
                    linkField.getText(),
                    coverField.getText()
            );
            dbManager.insertBook(book);
            statusList.getItems().add("✅ Электронная книга добавлена: " + book.getTitle());
        });

        grid.addRow(0, new Label("Название:"), titleField);
        grid.addRow(1, new Label("Автор:"), authorField);
        grid.addRow(2, new Label("Регион:"), regionField);
        grid.addRow(3, new Label("Цена:"), priceField);
        grid.addRow(4, new Label("Страницы:"), pagesField);
        grid.addRow(5, new Label("Ссылка на eBook:"), linkField);
        grid.addRow(6, new Label("Обложка (URL):"), coverField);
        grid.addRow(7, addPrintedBook, addEBook);
        grid.addRow(8, statusList);


        Scene scene = new Scene(grid, 600, 450);
        stage.setScene(scene);
        stage.show();
    }
}
