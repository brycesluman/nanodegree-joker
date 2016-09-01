package com.example;

import java.io.InputStream;

/**
 * Created by bryce on 8/15/16.
 */
public class Response {
    private InputStream body;

    public Response(InputStream body) {
        this.body = body;
    }

    public InputStream getBody() {
        return body;
    }
}

