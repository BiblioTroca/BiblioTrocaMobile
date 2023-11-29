package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.WishlistDTO;
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

public class WishlistService {

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");

    public void createWish(WishlistDTO wish) {
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> createNewWish = executor.submit(() -> {
           String wishToAdd = gson.toJson(wish);
           RequestBody body = RequestBody.create(wishToAdd, JSON);
           Request request = new Request.Builder()
                   .url(url + "/desejos")
                   .post(body)
                   .build();

           try {
               Response response = client.newCall(request).execute();

               if (response.isSuccessful()) {
                   Log.i("WishlistService", "Wish created");
               }

           } catch (IOException exception) {
               exception.printStackTrace();
           }
        });

        try {
            createNewWish.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getWishlist(List<WishlistDTO> wishlist) {
        wishlist.clear();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

        Future<?> fetchWishlist = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url + "/desejos")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String wishlistResponse = response.body().string();
                    JsonArray wishlistArray = gson.fromJson(wishlistResponse, JsonArray.class);

                    for (JsonElement wishElement : wishlistArray) {
                        JsonObject wishJson = wishElement.getAsJsonObject();
                        WishlistDTO wishToAdd = gson.fromJson(wishJson, WishlistDTO.class);

                        wishlist.add(wishToAdd);
                    }
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            fetchWishlist.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WishlistDTO getWishById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/desejos/" + id;

        Future<WishlistDTO> fetchWishById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String wishResponse = response.body().string();

                    if (wishResponse != null && !wishResponse.isEmpty()) {
                        WishlistDTO selectedWish = gson.fromJson(wishResponse, WishlistDTO.class);
                        return selectedWish;
                    }
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return null;
        });

        try {
            return fetchWishById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWishById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/desejos/" + id;

        Future<?> updateWishById = executor.submit(() -> {
            String updatedWish = gson.toJson(id);
            RequestBody body = RequestBody.create(updatedWish, JSON);
            Request request = new Request.Builder()
                   .url(url)
                   .put(body)
                   .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("WishlistService", "Desejo atualizado.");
                } else {
                    Log.i("WishlistService", "Erro ao atualizar.");
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        try {
            updateWishById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWishById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca" + "/desejos/" + id;

        Future<?> deleteWishById = executor.submit(() -> {
           Request request = new Request.Builder()
                   .url(url)
                   .delete()
                   .build();

           try {
               Response response = client.newCall(request).execute();

               if (response.isSuccessful()) {
                   Log.i("WishlistService", "Apagou o desejo.");
               } else {
                   Log.i("WishlistService", "Erro ao deletar.");
               }

           } catch (IOException exception) {
               exception.printStackTrace();
           }
        });

        try {
            deleteWishById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
