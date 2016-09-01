package com.example;

import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by bryce on 8/15/16.
 */
public class Request implements Callable<Response> {
    private URL url;

    public Request(URL url) {
        this.url = url;
    }

    @Override
    public Response call() throws Exception {
        return new Response(url.openStream());
    }
}
