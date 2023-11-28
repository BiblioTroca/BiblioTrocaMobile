package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.models.BookSimpleDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.lang.reflect.Type;
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

        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

        Future<?> fetchBooks = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url +"/livros")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String listBookResponse = response.body().string();
                    JsonArray listBookArray = gson.fromJson(listBookResponse, JsonArray.class);

                    for (JsonElement bookElement : listBookArray) {
                        JsonObject bookInJson = bookElement.getAsJsonObject();
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
        String url = "https://testelivros-89f74-default-rtdb.firebaseio.com" + "/books/" + "-NkCl5FdFDn37a6md8UZ" + ".json";

        Future<BookCompleteDTO> fetchBookById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String bookResponse = response.body().string();
                    if (bookResponse != null && !bookResponse.isEmpty()) {
                        BookCompleteDTO selectedBook = gson.fromJson(bookResponse, BookCompleteDTO.class);

                        return selectedBook;
                    }
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

        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

        Future<?> fetchBooks = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url + "/livros")
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String listBookResponse = response.body().string();
                    JsonArray jsonArray = JsonParser.parseString(listBookResponse).getAsJsonArray();



                    for (JsonElement element : jsonArray) {
                        // Se cada elemento do array é um objeto, então você pode desserializar diretamente para BookCompleteDTO
                        BookCompleteDTO bookToAdd = gson.fromJson(element, BookCompleteDTO.class);
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

    public void deleteBookById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/livros/" + id ;

        Future<?> deleteBookById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("BookService", "Apagou o livro");
                } else {
                    Log.i("BookService", "Não Funcionou, que droga");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        try {
            deleteBookById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BookCompleteDTO getLivroByid(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/livros/" + id ;

        Log.d("BookService", "URL: " + url);


        Future<BookCompleteDTO> fetchBookById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String bookResponse = response.body().string();
                    Log.d("BookService","Book Response" + bookResponse);
                    BookCompleteDTO selectedBook = gson.fromJson(bookResponse, BookCompleteDTO.class);

                    return selectedBook;
                } else {

                    // Lidar com a resposta não bem-sucedida, por exemplo, logar o código de resposta.
                    Log.e("BookService", "Resposta não bem-sucedida. Código: " + response.code());
                }
            } catch (IOException exception) {
                // Lidar com exceções durante a execução da solicitação.
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




}
