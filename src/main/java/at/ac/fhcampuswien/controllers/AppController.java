package at.ac.fhcampuswien.controllers;

import at.ac.fhcampuswien.api.NewsApi;
import at.ac.fhcampuswien.downloader.Downloader;
import at.ac.fhcampuswien.enums.*;
import at.ac.fhcampuswien.models.Article;
import at.ac.fhcampuswien.models.NewsResponse;
import at.ac.fhcampuswien.models.Source;

import java.util.*;
import java.util.stream.Collectors;

public class AppController {
    private List<Article> articles;
   private static AppController instance = null;

    private AppController() {
    }
    public static AppController getInstance(){
        if(instance == null){
            instance = new AppController();
        }
        return instance;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }


    // Method is needed for exercise 4 - ignore for exercise 3 solution
    // returns number of downloaded article urls

    public List<Article> getTopHeadlinesAustria() {
        NewsApi api = new NewsApi.Builder()
                .endpoint(Endpoint.TOP_HEADLINES)
                .q("corona")
                .build();
        articles = new ArrayList<>();
        try {
            NewsResponse response = api.requestData();
            articles = response.getArticles();
        } catch (NewsAPIException e) {
            System.out.println( "An error occurred while fetching articles: " + e.getMessage() );
        }

        return articles;
    }

    public int downloadURLs(Downloader downloader) throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );
        // TODO extract urls from articles with java stream

        List<String> urls = articles.stream()
                .map( Article::getUrl )
                .filter(Objects::nonNull)
                .toList();


        return downloader.process( urls );
    }

    /**
     * gets the size of last fetched articles
     *
     * @return size of article list
     */
    public int getArticleCount() {
        if ( articles != null ) {
            return articles.size();
        }
        return 0;
    }

    /**
     * get the top headlines from austria via newsapi
     *
     * @return article list
     */


    /**
     * returns all articles that do contain "bitcoin"
     * in their title from newsapi
     *
     * @return filtered list
     */
    public List<Article> getAllNewsBitcoin() {
        NewsApi api = new NewsApi.Builder()
                .endpoint(Endpoint.EVERYTHING)
                .q("bitcoin")
                .build();
        articles = new ArrayList<>();
        try {
            NewsResponse response = api.requestData();
            articles = response.getArticles();
        } catch (NewsAPIException e) {
            System.out.println( "An error occurred while fetching articles: " + e.getMessage() );
        }

        return articles;
    }


    public String getProviderWithMostArticles() throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );

        return articles.stream()
                .map( Article::getSource )
                .collect( Collectors.groupingBy( Source::getName ) )
                .entrySet()
                .stream()
                .max( Comparator.comparingInt( o -> o.getValue().size() ) )
                .map( stringListEntry -> stringListEntry.getKey() + " " + stringListEntry.getValue().size() )
                .orElseThrow( () -> new NewsAPIException( "problem with analysing data in getProviderWithMostArticles" ) );
    }

    public String getLongestNameOfAuthors() throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );

        return articles.stream()
                .map( Article::getAuthor )
                .filter( Objects::nonNull )
                .max( Comparator.comparing( String::length ) )
                .orElseThrow( () -> new NewsAPIException( "problem with analysing data in getLongestNameOfAuthors" ) );
    }

    public long getCountArticlesNYTimes() throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );

        return articles.stream()
                .filter( e -> e.getSource().getName().equals( "New York Times" ) )
                .count();
    }

    public List<Article> getArticlesShorterThan(int length) throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );

        return articles.stream()
                .filter( article -> article.getTitle().length() <= length )
                .collect( Collectors.toList() );
    }

    public List<Article> sortArticlesByContentLength() throws NewsAPIException {
        if ( articles == null )
            throw new NewsAPIException( "Load data first" );

        return articles.stream()
                .sorted( Comparator.comparing( Article::getDescription ) )
                .sorted( Comparator.comparingInt( a -> a.getDescription().length() ) )
                .collect( Collectors.toList() );


    }

    /**
     * filters a given article list based on a query
     *
     * @param query    to filter by
     * @param articles list to filter
     * @return filtered list
     */
    private static List<Article> filterList(String query, List<Article> articles) {
        List<Article> filtered = new ArrayList<>();
        for ( Article i : articles ) {
            if ( i.getTitle().toLowerCase().contains( query ) ) {
                filtered.add( i );
            }
        }
        return filtered;
    }


}
