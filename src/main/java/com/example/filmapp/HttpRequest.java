package com.example.filmapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;

public class HttpRequest
{
    String url;

    public HttpRequest(String url) {
        this.url = url;
    }

    public JSONObject Fetch() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMjFhNDZmZTAzMDYzMTkxYzIxZmRlNDFjZDNjNGY3YSIsIm5iZiI6MTc0MjkwNDcyNS44MjMsInN1YiI6IjY3ZTI5ZDk1MTZhM2M1YzIyNGYwNjUzMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XkcGqKO9RPDcTHisYqAWnXG__T3ZQuggbDABN8xYv5Q")
                .build();

        Response response = client.newCall(request).execute();

        return (JSONObject) JSONValue.parse(response.body().string());
    }
}
