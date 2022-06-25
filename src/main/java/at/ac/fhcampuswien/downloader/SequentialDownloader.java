package at.ac.fhcampuswien.downloader;

import at.ac.fhcampuswien.controllers.NewsAPIException;

import java.util.Date;
import java.util.List;

// Class is needed for exercise 4 - ignore for exercise 3 solution
public class SequentialDownloader extends Downloader {

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsAPIException{
        int count = 0;

        long start = new Date().getTime();
    for (String url : urls) {
        try {
            String fileName = saveUrl2File(url);
            //System.out.println(saveUrl2File(url));
            if(fileName != null)
                count++;
        } catch (NewsAPIException e){
            System.err.println(e.getMessage());
            throw new NewsAPIException(e.getMessage());
        } catch (Exception e){
            throw new NewsAPIException("Different problem occurred in " + this.getClass().getName() + ". Message: " + e.getMessage());
        }
    }
        long zeitFuerdownload = new Date().getTime() - start;
        System.out.println("The Sequential downloader took us "+zeitFuerdownload+" milliseconds or around "+zeitFuerdownload/1000+" seconds for " + count+ " files");
        return count;
    }
}
