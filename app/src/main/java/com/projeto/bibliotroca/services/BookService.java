package com.projeto.bibliotroca.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.BookCompleteDTO;
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

    public void getListBook(List<BookSimpleDTO> books) {
        books.clear();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = GlobalConstants.BASE_URL + "/books.json";

        Future<?> fetchBooks = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
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
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BookCompleteDTO getBookById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Usar o modelo abaixo comentado para a API ORIGINAL
        // String url = GlobalConstants.BASE_URL + "/books-details/" + id;
        String url = GlobalConstants.BASE_URL + "/books-details/-Niobnf9HzkwPu8WqJdJ.json";

        Future<BookCompleteDTO> fetchBookById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String bookResponse = response.body().string();
                    BookCompleteDTO selectedBook = gson.fromJson(bookResponse, BookCompleteDTO.class);

                    return selectedBook;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return null;
        });

        try {
            return fetchBookById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getListBookFull(List<BookCompleteDTO> books) {
        books.clear();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = GlobalConstants.BASE_URL + "/books.json";

        Future<?> fetchBooks = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String listBookResponse = response.body().string();
                    JsonObject listBookInJson = gson.fromJson(listBookResponse, JsonObject.class);

                    for (String key : listBookInJson.keySet()) {
                        JsonObject bookInJson = listBookInJson.getAsJsonObject(key);
                        BookCompleteDTO bookToAdd = gson.fromJson(bookInJson, BookCompleteDTO.class);
                        books.add(bookToAdd);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            fetchBooks.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
