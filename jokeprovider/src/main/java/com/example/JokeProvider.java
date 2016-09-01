package com.example;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JokeProvider {
    public static String getJoke() {
        // Have one (or more) threads ready to do the async tasks. Do this during startup of your app.
        ExecutorService executor = Executors.newFixedThreadPool(1);

// Fire a request.
        try {
            Future<Response> response = executor.submit(new Request(new URL("http://api.icndb.com/jokes/random")));

            InputStream body = response.get().getBody();
        } catch (MalformedURLException | ExecutionException | InterruptedException e ) {

        } finally {
            executor.shutdown();
            return "dynamic joke string";
        }

// Do your other tasks here (will be processed immediately, current thread won't block).
// ...

// Get the response (here the current thread will block until response is returned).

// ...

// Shutdown the threads during shutdown of your app.

    }
    public static String getStaticJoke() {
        return "Aliens DO indeed exist. They just know better than to visit a planet that Chuck Norris is on.";
    }
}
