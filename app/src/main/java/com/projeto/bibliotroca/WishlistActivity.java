package com.projeto.bibliotroca;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.projeto.bibliotroca.models.WishlistDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

 public class WishlistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

    public static final MediaType JSON = MediaType.get("application/json");

    public static final String App_Livros = "App Livros";

    private List<WishlistDTO> wishlist = new ArrayList<>();

    ImageView btnArrowBack;

    private EditText wishTitle;
    private EditText wishAuthor;

    private Spinner spinner;

    private Button btnRegisterWish;

    private Gson gson = new Gson();

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.wishlist_layout);

        wishTitle = findViewById(R.id.txtWishTitle);
        wishAuthor = findViewById(R.id.txtWishAuthor);

        spinner = findViewById(R.id.spinner);

        btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        btnRegisterWish = findViewById(R.id.btnRegisterWish);
        btnRegisterWish.setOnClickListener(e -> save());
        loadDatabase();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private WishlistDTO generateWish() {
        WishlistDTO wish = new WishlistDTO();

        wish.setName(wishTitle.getText().toString());
        wish.setAuthor(wishAuthor.getText().toString());
        wish.setCategory(spinner.getSelectedItem().toString());

        Toast.makeText(this, "Desejo cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

        return wish;
    }

    private void save() {
        WishlistDTO wish = generateWish();
        saveWish(wish);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Service
    public void saveWish(WishlistDTO wishlist) {
        String jsonWishlist = gson.toJson(wishlist);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(jsonWishlist, JSON);
            Request request = new Request.Builder()
                    .url(url + "/desejos")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                Log.e(App_Livros, "Resposta: " + response.body().string());
            } catch (IOException e) {
                Log.e(App_Livros, "Erro: ", e);
                throw new RuntimeException(String.valueOf(wishlist));
            }

            handler.post(() -> {

            });
        });
    }

    //Análise se necessário
    private void loadDatabase() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            wishlist.clear();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url + "/desejos")
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()){
                String serverResponse = response.body().string();

                if (serverResponse != null && !serverResponse.isEmpty()) {
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(serverResponse);

                    if (element != null && !element.isJsonNull() && element.isJsonObject()) {
                        JsonObject convertedObject = element.getAsJsonObject();

                        for (String keyW : convertedObject.keySet()) {
                            JsonElement obj = convertedObject.get(keyW);

                            if (obj != null && !obj.isJsonNull() && obj.isJsonObject()) {
                                WishlistDTO wish = gson.fromJson(obj, WishlistDTO.class);
                                wishlist.add(wish);
                            }
                        }

                        Log.e(App_Livros, "Resposta: " + serverResponse);

                    } else {
                        Log.e(App_Livros, "Resposta do servidor não é um objeto Json válido.");
                    }

                } else {
                    Log.e(App_Livros, "Resposta do servidor vazia ou nula.");
                }

            } catch (IOException e) {
                Log.e(App_Livros, "Erro: ", e);
                throw new RuntimeException(e);
            }
        });
    }
}
