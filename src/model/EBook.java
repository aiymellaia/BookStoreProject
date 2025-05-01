package model;

public class EBook extends Book implements Purchasable {

    private String downloadLink;

    public EBook(String title, String author, String region, double price, String downloadLink, String coverUrl) {
        super(title, author, region, price, "EBook", coverUrl);
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    @Override
    public String getDetails() {
        return "E-Book: " + getTitle() + " by " + getAuthor() + " [Download: " + downloadLink + "]";
    }

    @Override
    public void purchase(User user) {
        user.addPurchasedBook(this);
    }
}
