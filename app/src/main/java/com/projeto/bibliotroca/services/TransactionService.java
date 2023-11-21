package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.util.List;
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
        String url = GlobalConstants.BASE_URL + "/transactions.json";

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> createNewTransaction = executor.submit(() -> {
            String transactionToAdd = gson.toJson(transaction);
            RequestBody body = RequestBody.create(transactionToAdd, JSON);

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

    public void getListTransaction(List<TransactionDTO> transactions){
         transactions.clear();

         ExecutorService executor = Executors.newSingleThreadExecutor();

         String url = GlobalConstants.BASE_URL + "/transactions.json";

        Future<?> fetchTransactions = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String listTransactionResponse = response.body().string();
                    JsonObject listTransactionInJson = gson.fromJson(listTransactionResponse, JsonObject.class);


                    for (String key : listTransactionInJson.keySet()) {
                        JsonObject transactionInJson = listTransactionInJson.getAsJsonObject(key);
                        TransactionDTO transactionToAdd = gson.fromJson(transactionInJson, TransactionDTO.class);

                        transactions.add(transactionToAdd);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            fetchTransactions.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Corrigir
    public TransactionDTO getTransactionById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = GlobalConstants.BASE_URL+"/transactions/"+id+".json";
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
        String url = GlobalConstants.BASE_URL + "/transactions/" + id + ".json";

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
                    Log.e("TransactionService", "Não Funcionou, que droga");
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
