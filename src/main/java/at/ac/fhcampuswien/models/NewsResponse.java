package at.ac.fhcampuswien.models;

import java.util.List;

public class NewsResponse {
    private String status;  // status of http response
    private int totalResults;   // overall number of articles sent back
    private List<Article> articles; // list of articles in response
    private List<Article> url;  // status of url response

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;

    }
    public List<Article> getURL() {
        return url;
    }
}
