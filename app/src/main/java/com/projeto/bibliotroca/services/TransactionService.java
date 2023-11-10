package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.FormBody;
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
}
