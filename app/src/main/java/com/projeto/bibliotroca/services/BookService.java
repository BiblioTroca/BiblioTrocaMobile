package com.projeto.bibliotroca.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.BookSimpleDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookService {
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    private static final String URL = GlobalConstants.BASE_URL + "/livros.json";

    public void getListBook(List<BookSimpleDTO> books) {
        books.clear();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> fetchBooks = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(URL)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String listBookResponse = response.body().string();
                    JsonObject listBookInJson = gson.fromJson(listBookResponse, JsonObject.class);

                    for (String key : listBookInJson.keySet()) {
                        JsonObject bookInJson = listBookInJson.getAsJsonObject(key);
                        BookSimpleDTO bookToAdd = gson.fromJson(bookInJson, BookSimpleDTO.class);

                        books.add(bookToAdd);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            fetchBooks.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
