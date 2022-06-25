package at.ac.fhcampuswien.models;
import java.lang.module.ModuleDescriptor.Builder;

public class Article {
    private String author;
    private String title;
    private Source source;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    public Article(String author, String title, Source source, String description, String url, String urlToImage, String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.source = source;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }


    public Source getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "Title: " + getTitle() + "\n" +
                        "Author: " + getAuthor() + "\n"
                        //+ "; Source: " + getSource().getName() + "\n"
                        + "Desc (length): " + getDescription().length() + "\n Desc: " + getDescription() + "\n";
    }
}