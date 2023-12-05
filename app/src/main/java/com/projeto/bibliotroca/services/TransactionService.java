package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionService {
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");

    public void createTransaction(TransactionDTO transaction) {
//        String url = GlobalConstants.BASE_URL + "/transactions.json";
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/transacoes";

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> createNewTransaction = executor.submit(() -> {
            String transactionToAdd = gson.toJson(transaction);
            RequestBody body = RequestBody.create(transactionToAdd, JSON);
            Log.i("TESTANDO_TRANSACTION", transactionToAdd);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(response.isSuccessful())
                    Log.i("TransactionService" , "Transaction created");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            createNewTransaction.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<List<TransactionDTO>> getListTransaction() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/transacoes";

        return CompletableFuture.supplyAsync(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("Response", "Dto:" + response);

                if (response.isSuccessful()) {
                    String listTransactionResponse = response.body().string();
                    Log.d("Response", "Dto:" + listTransactionResponse);
                    JsonArray listTransactionInJson = gson.fromJson(listTransactionResponse, JsonArray .class);
                    Log.d("Response", "Dto:" + listTransactionInJson);

                    List<TransactionDTO> transactions = new ArrayList<>();


                    for (JsonElement jsonElement : listTransactionInJson) {
                        TransactionDTO transactionToAdd = gson.fromJson(jsonElement, TransactionDTO.class);
                        transactions.add(transactionToAdd);
                        Log.d("TransactionDTO", "TransactionDTO: " + transactionToAdd.toString());

                    }



                    return transactions;
                } else {
                    throw new IOException("Erro na requisição: " + response.code());
                }
            } catch (IOException exception) {
                throw new RuntimeException("Exceção durante a requisição: " + exception.getMessage());
            }
        }, executor);
    }


    // Corrigir
    public TransactionDTO getTransactionById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/transacoes/" + id;

       // String url = GlobalConstants.BASE_URL+"/transactions/" + id + ".json";

        Log.d("Name", "Name:" + id);

        Future<TransactionDTO> fetchTransactionById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("Response", "Dto:" + response);

                if (response.isSuccessful()) {
                    String transactionResponse = response.body().string();
                    TransactionDTO selectedTransaction = gson.fromJson(transactionResponse, TransactionDTO.class);
                    Log.d("TransactionService", "e: " + selectedTransaction);
                    Log.d("TransactionResponse", "Dto:" + transactionResponse);
                    return selectedTransaction;
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return null;
        });
        try {
            return fetchTransactionById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // TESTAR
    public void deleteTransactionById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = GlobalConstants.BASE_URL + "/transactions/" + id + ".json";

        Future<?> deleteTransactionById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("TransactionService", "Apagou a transação");
                } else {
                    Log.i("TransactionService", "Não Funcionou, que droga");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        try {
            deleteTransactionById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // TESTAR e corrigir o JSON (Olhar Buyer e Seller, avaliação, fragmentos)
    public void updateTransactionById(String id, String newStatus) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/transacoes/" + id;

        //String url = GlobalConstants.BASE_URL + "/transactions/" + id + ".json";

        Future<?> updateTransactionStatusById = executor.submit(() -> {
            String updatedStatusJson = "{\"status\": \"" + newStatus + "\"}";
            RequestBody body = RequestBody.create(updatedStatusJson, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.i("TransactionService", "Atualizou");
                } else {
                    Log.e("TransactionService", "Não Funcionou, que droga" + response.code());
                    Log.e("TransactionService", "Não Funcionou, que droga" + response.body().string());
                    Log.e("TransactionService", "Não Funcionou, que droga" + response.message());
                    Log.d("TransactionService", "Não Funcionou, que droga" + response);

                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        try {
            updateTransactionStatusById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
