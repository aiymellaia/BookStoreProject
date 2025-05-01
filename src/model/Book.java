package model;

import java.io.Serializable;

public abstract class Book implements Serializable {
    private String title;
    private String author;
    private String region;
    private double price;
    private String type;
    private String coverUrl;


    public Book(String title, String author, String region, double price, String type,  String coverUrl) {
        this.title = title;
        this.author = author;
        this.region = region;
        this.price = price;
        this.type = type;
        this.coverUrl = coverUrl;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getRegion() { return region; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public String getCoverUrl() { return coverUrl; }

    public abstract String getDetails();
}
