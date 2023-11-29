package com.projeto.bibliotroca;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.projeto.bibliotroca.models.WishlistDTO;
import com.projeto.bibliotroca.services.WishlistService;

import org.jetbrains.annotations.Nullable;

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

public class EditWishlistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final MediaType JSON = MediaType.get("application/json");

    public static final String App_Livros = "App Livros";

    private List<WishlistDTO> wishlist = new ArrayList<>();

    ImageView btnArrowBack;

    private EditText editWishTitle;
    private EditText editWishAuthor;

    private Spinner spinner;

    private Button btnUpdateWish;

    private WishlistDTO wish;

    private String wishId;

    private Gson gson = new Gson();

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wishlist_layout);
        buildStyles();

        editWishTitle = findViewById(R.id.editWishTitle);
        editWishAuthor = findViewById(R.id.editWishAuthor);

        spinner = findViewById(R.id.spinner);

        btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        wishId = getIntent().getStringExtra("wish_id");
        loadWishInfo(wishId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fillInForm(wish);

        btnUpdateWish = findViewById(R.id.btnUpdateWish);
        btnUpdateWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(wish);
                loadDatabase();
            }
        });
    }

    private void loadWishInfo(String id) {
        WishlistService wishlistService = new WishlistService();
        wish = wishlistService.getWishById(id);

        if (wish != null) {
            fillInForm(wish);
        } else {
            Log.e("EditWishlistActivity", "Desejo nulo ou não encontrado.");
        }
    }

    private class UpdateWishTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                WishlistDTO updatedWish = updateWish();
                updateWish(updatedWish);

                return true;
            } catch (Exception e) {
                Log.e(App_Livros, "Erro ao atualizar desejo.");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                loadDatabase();
                Toast.makeText(EditWishlistActivity.this, "Desejo atualizado com sucesso.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditWishlistActivity.this, "Falha ao atualizar desejo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void update(WishlistDTO wish) {
        new UpdateWishTask().execute();
    }

    private void fillInForm(WishlistDTO wish) {
        if (wish != null) {
            editWishTitle.setText(wish.getName());
            editWishAuthor.setText(wish.getAuthor());
            spinner.getSelectedItem();
        } else {
            Log.e("EditWishlistActivity", "Desejo nulo.");
        }
    }

    private WishlistDTO updateWish() {
        String newTitle = editWishTitle.getText().toString();
        String newAuthor = editWishAuthor.getText().toString();
        String newCategory = spinner.getSelectedItem().toString();

        WishlistDTO wishlist = new WishlistDTO();

        wishlist.setId(wishId);
        wishlist.setName(newTitle);
        wishlist.setAuthor(newAuthor);
        wishlist.setCategory(newCategory);

        return wishlist;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Teste atualizar
    public void updateWish(WishlistDTO id) {
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";
        String jsonWish = gson.toJson(id);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(jsonWish, JSON);
        Request request = new Request.Builder()
                .url(url + "/desejos/" + id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Log.i(App_Livros, "Desejo atualizado com sucesso.");
            } else {
                Log.e(App_Livros, "Falha ao atualizar desejo. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            Log.e(App_Livros, "Erro ao fazer chamada de rede para atualizar desejo.", e);
        }
    }

    private void loadDatabase() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            wishlist.clear();

            OkHttpClient client = new OkHttpClient();

            String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

            Request request = new Request.Builder()
                    .url(url + "/desejos")
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
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