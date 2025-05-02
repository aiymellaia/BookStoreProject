package model;

public class PrintedBook extends Book implements Purchasable {
    private int numberOfPages;

    public PrintedBook(String title, String author, String region, double price, int numberOfPages, String coverUrl) {
        super(title, author, region, price, "PrintedBook", coverUrl);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String getDetails() {
        return "Печатная книга: " + getTitle() + " — " + getAuthor() + " (" + numberOfPages + " стр.)";
    }

    @Override
    public void purchase(User user) {
        user.addPurchasedBook(this);
    }
}
