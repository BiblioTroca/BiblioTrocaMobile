package com.projeto.bibliotroca.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.projeto.bibliotroca.models.BookSimpleDTO;
import com.projeto.bibliotroca.models.UserDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");

    public String createUser(UserDTO user) {
        String url = GlobalConstants.BASE_URL + "/usuarios.json";

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> createNewUser = executor.submit(() -> {
            String userToAdd = gson.toJson(user);
            RequestBody body = RequestBody.create(userToAdd, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(response.isSuccessful()) {
                    String token = response.body().string();

                    return token;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return "";
        });

        try {
            return createNewUser.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO getUserById(String id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Usar o modelo abaixo comentado para a API ORIGINAL
        // String url = GlobalConstants.BASE_URL + "/usuarios/" + id;
        String url = GlobalConstants.BASE_URL + "/usuarios/-Niobnf9HzkwPu8WqJdJ.json";

        Future<UserDTO> fetchUserById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String userResponse = response.body().string();
                    UserDTO selectedUser = gson.fromJson(userResponse, UserDTO.class);

                    return selectedUser;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return null;
        });

        try {
            return fetchUserById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String authenticate(String email, String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = GlobalConstants.BASE_URL + "/autenticar"; // usado apenas com a API

        JsonObject credentialsInJson = new JsonObject();
        credentialsInJson.addProperty("email", email);
        credentialsInJson.addProperty("password", password);

        Future<String> authenticateUser = executor.submit(() -> {
            String credentials = gson.toJson(credentialsInJson);
            RequestBody body = RequestBody.create(credentials, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(response.isSuccessful()) {
                    String token = response.body().string();

                    return token;
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return "";
        });

        try {
            return authenticateUser.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO updatePassword(String email, String newPassword) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        String url = GlobalConstants.BASE_URL + "/alterar-senha"; // usado apenas com a API

        JsonObject credentialsInJson = new JsonObject();
        credentialsInJson.addProperty("email", email);
        credentialsInJson.addProperty("newPassword", newPassword);

        Future<UserDTO> updateUserPassword = executor.submit(() -> {
            String credentials = gson.toJson(credentialsInJson);
            RequestBody body = RequestBody.create(credentials, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String userResponse = response.body().string();
                    UserDTO authenticatedUser = gson.fromJson(userResponse, UserDTO.class);

                    return authenticatedUser;
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }

            return null;
        });

        try {
            return updateUserPassword.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
