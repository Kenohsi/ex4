package at.ac.fhcampuswien.downloader;


import at.ac.fhcampuswien.api.NewsApi;
import at.ac.fhcampuswien.controllers.NewsAPIException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;

// Class is needed for exercise 4 - ignore for exercise 3 solution
public class ParallelDownloader extends Downloader {

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsAPIException {


        int count = 0;

        List<Callable<String>> callables = new ArrayList<>();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool( availableProcessors );

        long start = new Date().getTime();



        for ( int i = 0, countURLs = urls.size() ; i < countURLs ; i++ ) {
            String url = urls.get( i );
            Callable<String> thread = () -> {
                Thread.sleep( 100 );

                try {
                    return saveUrl2File( url );
                } catch (Exception e) {

                    return e.getMessage();
                }
            };
            callables.add( thread );
        }
        try {
            List<Future<String>> allFutures = executorService.invokeAll( callables );

            for ( int i = 0, allFuturesSize = allFutures.size() ; i < allFuturesSize ; i++ ) {
                Future<String> fileName = allFutures.get( i );
                if ( fileName != null )
                    count++;

                try {
                    String result = fileName.get();

                   // System.out.println( result );
                }catch (NullPointerException e){


                    System.err.println("Filename is NULL- go check it out"+e.getMessage()+e.getCause());
                }


            }


        } catch (ExecutionException e) {
            System.err.println(e.getMessage()+e.getCause());

        }
         catch (InterruptedException e) {
             System.err.println( e.getMessage() );

         }
        long zeitFuerdownload = new Date().getTime() - start;
        System.out.println("The Paralelldownloader took us just "+zeitFuerdownload+" milliseconds or around "+zeitFuerdownload/1000+" seconds for " + count+ " files");

        return count;

    }
}