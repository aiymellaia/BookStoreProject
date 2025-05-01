import service.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        dbManager.close();
    }
}
