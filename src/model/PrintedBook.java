package model;

public class PrintedBook extends Book implements Purchasable {

    private int numberOfPages;

    public PrintedBook(String title, String author, String region, double price, int numberOfPages, String coverUrl) {
        super(title, author, region, price, "Printed", coverUrl);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String getDetails() {
        return "Printed Book: " + getTitle() + " by " + getAuthor() + " (" + numberOfPages + " pages)";
    }

    @Override
    public void purchase(User user) {
        user.addPurchasedBook(this);
    }
}
