package com.projeto.bibliotroca;

import static com.projeto.bibliotroca.RegisterBookActivity.App_Livros;
import static com.projeto.bibliotroca.RegisterBookActivity.JSON;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.services.BookService;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    private List<BookCompleteDTO> livros = new ArrayList<>();


    private EditText EditBookTitle;
    private EditText EditBookAuthor;
    private EditText EditBookPublisher;
    private EditText EditBookLanguage;
    private EditText EditBookYear;
    private EditText EditBookDescription;

    ImageView btnArrowback;


    private ConstraintLayout constraintNewCondition;
    private ConstraintLayout constraintGoodCondition;
    private ConstraintLayout constraintUsedCondition;
    private Spinner spinner;

    private String selectedCondition = "";

    private String livroId;
    private Gson gson = new Gson();


    BookCompleteDTO livro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.edit_book_layout);

        EditBookTitle = findViewById(R.id.editBooktitle);
        EditBookAuthor = findViewById(R.id.editBookAuthor);
        EditBookPublisher = findViewById(R.id.editPublisher);
        EditBookLanguage = findViewById(R.id.editLanguagem);
        EditBookYear = findViewById(R.id.editBookYear);
        EditBookDescription = findViewById(R.id.editBookDescription);
        constraintGoodCondition = findViewById(R.id.selectableGoodCondition);
        constraintNewCondition = findViewById(R.id.selectableNewCondition);
        constraintUsedCondition = findViewById(R.id.selectableUsedCondition);
        spinner = findViewById(R.id.spinner);

        btnArrowback = findViewById(R.id.btnArrowBack);

        btnArrowback.setOnClickListener(event -> finish());


        livroId = getIntent().getStringExtra("livro_id");
        BookService bookService = new BookService();

        BookCompleteDTO livro = bookService.getBookById(livroId);
        if (livro != null) {
            PreencherCampo(livro);
        } else {
            Log.e("Error", "Livro não encontrado ou é nulo");
        }
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        PreencherCampo(livro);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar();
                carregarFirebase();
            }
        });

    }


    private void atualizar() {
        BookCompleteDTO livro = atualizarLivro();
        atualizar(livro);
    }

    private void PreencherCampo(BookCompleteDTO livro) {

        EditBookTitle.setText(livro.getName());
        EditBookAuthor.setText(livro.getAuthor());
        EditBookPublisher.setText(livro.getPublishingCompany());
        EditBookLanguage.setText(livro.getLanguage());
        EditBookYear.setText(livro.getYear());
        spinner.getSelectedItem();
        EditBookDescription.setText(livro.getDescription());
        definirCondicaoSelecionada(livro.getState());


    }

    private BookCompleteDTO atualizarLivro() {

        BookCompleteDTO livro = new BookCompleteDTO();


        String novoTitulo = EditBookTitle.getText().toString();
        String novoAutor = EditBookAuthor.getText().toString();
        String novaEditora = EditBookPublisher.getText().toString();
        String novaLinguagem = EditBookLanguage.getText().toString();
        String novaDescricao = EditBookDescription.getText().toString();
        String novoAno = EditBookYear.getText().toString();
        String novacategoria = spinner.getSelectedItem().toString();

        BookCompleteDTO livroAtualizado = new BookCompleteDTO();

        livroAtualizado.setId(livroId);
        livroAtualizado.setName(novoTitulo);
        livroAtualizado.setAuthor(novoAutor);
        livroAtualizado.setPublishingCompany(novaEditora);
        livroAtualizado.setLanguage(novaLinguagem);
        livroAtualizado.setDescription(novaDescricao);
        livroAtualizado.setYear(novoAno);
        livroAtualizado.setCategory(novacategoria);
        livroAtualizado.setState(selectedCondition);
        livroAtualizado.setCreatedAt(Instant.now().atZone(ZoneId.of("GMT-3")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        return livro;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Testar Atualizar
     public void atualizar (BookCompleteDTO livros){
            String livroId = livro.getId();
            String url = GlobalConstants.BASE_URL + "/livros/" + livroId + ".json";

            String jsonLivro = gson.toJson(livros);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(jsonLivro, JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    Log.e(App_Livros, "Resposta: " + response.body().string());
                } catch (IOException e) {
                    Log.e(App_Livros, "Erro: ", e);
                    throw new RuntimeException("Erro ao atualizar livro: " + livroId);
                }

                handler.post(() -> {

                });
            });
        }


    private void carregarFirebase () {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            livros.clear();
            OkHttpClient client = new OkHttpClient();
            String url = GlobalConstants.BASE_URL + "/livros.json";

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String resposta = response.body().string();

                if (resposta != null && !resposta.isEmpty()) {
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(resposta);

                    if (element != null && !element.isJsonNull() && element.isJsonObject()) {
                        JsonObject convertedObject = element.getAsJsonObject();

                        for (String chave : convertedObject.keySet()) {
                            JsonElement obj = convertedObject.get(chave);

                            if (obj != null && !obj.isJsonNull() && obj.isJsonObject()) {
                                BookCompleteDTO l = gson.fromJson(obj, BookCompleteDTO.class);
                                livros.add(l);
                            }
                        }
                        Log.e(App_Livros, "Resposta: " + resposta);
                    } else {
                        Log.e(App_Livros, "Resposta do servidor não é um objeto JSON válido.");
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

    private void definirCondicaoSelecionada(String estado) {

        if ("Novo".equals(estado)) {
            constraintNewCondition.performClick();
        } else if ("Bom".equals(estado)) {
            constraintGoodCondition.performClick();
        } else if ("Usado".equals(estado)) {
            constraintUsedCondition.performClick();
        }
    }



    public void handleCheckedRadioItem(View view) {
        ConstraintLayout radioItemGoodcondition = findViewById(R.id.selectableGoodCondition);
        ConstraintLayout radioItemNewcondition = findViewById(R.id.selectableNewCondition);
        ConstraintLayout radioItemOldcondition = findViewById(R.id.selectableUsedCondition);
        View radioCircleNew = findViewById(R.id.radioNewCondition);
        View radioCircleGood = findViewById(R.id.radioGoodCondition);
        View radioCircleBad = findViewById(R.id.radioBadCondition);


        view.setSelected(!view.isSelected());

        boolean isExistsRadioNew = radioItemNewcondition != null && radioCircleNew != null;
        boolean isExistsRadioGood = radioItemGoodcondition != null && radioCircleGood != null;
        boolean isExistsRadioBad = radioItemOldcondition != null && radioCircleBad != null;


        if (isExistsRadioNew) {
            if (view == radioItemNewcondition) {
                radioItemNewcondition.setSelected(true);
                radioCircleNew.setSelected(true);
                selectedCondition="Lido apenas uma ou poucas vezes, sem marcas.";

                if(isExistsRadioGood) {
                    radioItemGoodcondition.setSelected(false);
                    radioCircleGood.setSelected(false);
                }

                if(isExistsRadioBad){
                    radioItemOldcondition.setSelected(false);
                    radioCircleBad.setSelected(false);
                }

                return;
            }

            radioItemNewcondition.setSelected(false);
            radioCircleNew.setSelected(false);

            if(isExistsRadioGood){
                radioItemGoodcondition.setSelected(true);
                radioCircleGood.setSelected(true);
                selectedCondition="Pode ter algumas marcas leves de manuseio, sem rasuras.";
            }
            if(isExistsRadioBad){
                radioItemOldcondition.setSelected(false);
                radioCircleBad.setSelected(false);
            }

        }
        if (isExistsRadioBad) {
            if (view == radioItemOldcondition) {
                assert radioItemNewcondition != null;
                radioItemNewcondition.setSelected(false);
                assert radioCircleNew != null;
                radioCircleNew.setSelected(false);

                if(isExistsRadioGood) {
                    radioItemGoodcondition.setSelected(false);
                    radioCircleGood.setSelected(false);
                }

                radioItemOldcondition.setSelected(true);
                radioCircleBad.setSelected(true);
                selectedCondition="Bastante usado, com marcas de uso e anotações.";

            }
        }
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

}
