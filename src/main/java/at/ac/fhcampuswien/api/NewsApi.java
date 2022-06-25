package at.ac.fhcampuswien.api;

import at.ac.fhcampuswien.controllers.NewsAPIException;
import at.ac.fhcampuswien.enums.*;
import at.ac.fhcampuswien.models.NewsResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class NewsApi {
    public static final String DELIMITER = "&";
    private static final String URL = "https://newsapi.org/v2/%s?q=%s&apiKey=%s";
    private static final String API_KEY = Dotenv.load().get("API_TOKEN");   // read token from .env file -> add .env to .gitignore!!!
    private final OkHttpClient client = new OkHttpClient();

    private final Endpoint endpoint;
    private final String q;
    private final String qInTitle;
    private final Country sourceCountry;
    private final Category sourceCategory;
    private final String domains;
    private final String excludeDomains;
    private final String from;
    private final String to;
    private final Language language;
    private final SortBy sortBy;
    private final String pageSize;
    private final String page;

    //the private constructor
    private NewsApi(Builder builder){
        this.endpoint = builder.endpoint;
        this.q = builder.q;
        this.qInTitle = builder.qInTitle;
        this.sourceCountry = builder.sourceCountry;
        this.sourceCategory = builder.sourceCategory;
        this.domains = builder.domains;
        this.excludeDomains = builder.excludeDomains;
        this.from = builder.from;
        this.to =builder.to;
        this.language = builder.language;
        this.sortBy = builder.sortBy;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    // a nested class inside NewsApi class
    public static class Builder{

        private Endpoint endpoint;
        private String q;
        private String qInTitle;
        private Country sourceCountry;
        private Category sourceCategory;
        private String domains;
        private String excludeDomains;
        private String from;
        private String to;
        private Language language;
        private SortBy sortBy;
        private String pageSize;
        private String page;

        public Builder(){
            this.endpoint = endpoint;
            this.q = q;
            /*this.language = language;
            this.qInTitle = qInTitle;
            this.sourceCountry = sourceCountry;
            this.sourceCategory = sourceCategory;
            this.domains = domains;
            this.excludeDomains = excludeDomains;
            this.from = from;
            this.to = to;
            this.sortBy = sortBy;
            this.pageSize = pageSize;
            this.page = page;*/
        }

        // each function needs to return the Builder itself
        public Builder endpoint(Endpoint endpoint) {
            this.endpoint = endpoint;
            return this;
        }
        public Builder language(Language language) {
            this.language = language;
            return this;
        }
        public Builder q(String q) {
            this.q = q;
            return this;
        }
        public Builder qInTitle(String qInTitle) {
            this.qInTitle = qInTitle;
            return this;
        }
        public Builder sourceCountry(Country sourceCountry){
            this.sourceCountry = sourceCountry;
            return this;
        }
        public Builder sourceCategory(Category sourceCategory){
            this.sourceCategory = sourceCategory;
            return this;
        }
        public Builder domains(String domains) {
            this.domains = domains;
            return this;
        }
        public Builder excludeDomains(String excludeDomains) {
            this.excludeDomains = excludeDomains;
            return this;
        }
        public Builder from(String from) {
            this.from = from;
            return this;
        }
        public Builder to(String to) {
            this.to = to;
            return this;
        }
        public Builder sortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }
        public Builder pageSize(String pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        public Builder page(String page) {
            this.page = page;
            return this;
        }

        // returns a new Object of our UrlString
        public NewsApi build(){
            return new NewsApi(this);
        }

    }


    public String getQ() {
        return q;
    }

    public String getqInTitle() {
        return qInTitle;
    }

    public Country getSourceCountry() {
        return sourceCountry;
    }

    public Category getSourceCategory() {
        return sourceCategory;
    }

    public String getDomains() {
        return domains;
    }

    public String getExcludeDomains() {
        return excludeDomains;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Language getLanguage() {
        return language;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    //the old constructovic
    /*public NewsApi(String q, Endpoint endpoint){
        this.client = new OkHttpClient();
        this.q = q;
        this.endpoint = endpoint;
    }

    public NewsApi(String q, Country country, Endpoint endpoint){
        this.client = new OkHttpClient();
        this.q = q;
        this.sourceCountry = country;
        this.endpoint = endpoint;
    }

    public NewsApi(String q, String qInTitle, Country sourceCountry, Category sourceCategory, String domains, String excludeDomains, String from, String to, Language language, SortBy sortBy, String pageSize, String page, Endpoint endpoint) {
        this(q, endpoint);
        this.qInTitle = qInTitle;
        this.sourceCountry = sourceCountry;
        this.sourceCategory = sourceCategory;
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.page = page;
    }*/

    private String buildUrl(){
        String urlbase = String.format(URL, getEndpoint().getValue(), getQ(), API_KEY);

        StringBuilder sb = new StringBuilder(urlbase);

        if(getFrom() != null){
            sb.append(DELIMITER).append("from=").append(getFrom());
        }
        if(getTo() != null){
            sb.append(DELIMITER).append("to=").append(getTo());
        }
        if(getPage() != null){
            sb.append(DELIMITER).append("page=").append(getPage());
        }
        if(getPageSize() != null){
            sb.append(DELIMITER).append("pageSize=").append(getPageSize());
        }
        if(getLanguage() != null){
            sb.append(DELIMITER).append("language=").append(getLanguage());
        }
        if(getSourceCountry() != null){
            sb.append(DELIMITER).append("country=").append(getSourceCountry());
        }
        if(getSourceCategory() != null){
            sb.append(DELIMITER).append("category=").append(getSourceCategory());
        }
        if(getDomains() != null){
            sb.append(DELIMITER).append("domains=").append(getDomains());
        }
        if(getExcludeDomains() != null){
            sb.append(DELIMITER).append("excludeDomains=").append(getExcludeDomains());
        }
        if(getqInTitle() != null){
            sb.append(DELIMITER).append("qInTitle=").append(getqInTitle());
        }
        if(getSortBy() != null){
            sb.append(DELIMITER).append("sortBy=").append(getSortBy());
        }
        return sb.toString();
    }

    public NewsResponse requestData() throws NewsAPIException {
        String url = buildUrl();

        Request request = new Request.Builder()
                .url(HttpUrl.get(URL))
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            NewsResponse apiResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), NewsResponse.class); // parse the json response to NewsResponse

            if(apiResponse.getStatus().equals("ok")){   // http status code ok - 200
                return apiResponse;
            } else {
                throw new NewsAPIException(this.getClass() + ": http status not ok. Status is: " + apiResponse.getStatus());
            }
        } catch (JsonSyntaxException e){
            throw new NewsAPIException(this.getClass() + ": problems when parsing JSON response");
        } catch (IOException e) {
            throw new NewsAPIException(e.getMessage());
        }
    }
}
