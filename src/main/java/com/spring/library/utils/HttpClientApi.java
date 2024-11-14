package com.spring.library.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApi {
    HttpRequest request;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;

    public String getData(String url) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
