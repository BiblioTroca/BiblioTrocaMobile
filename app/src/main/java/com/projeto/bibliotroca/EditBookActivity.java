package com.projeto.bibliotroca;

import static com.projeto.bibliotroca.RegisterBookActivity.App_Livros;
import static com.projeto.bibliotroca.RegisterBookActivity.JSON;

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


    private BookCompleteDTO livro;

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
        carregarDetalhesDoLivro(livroId);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        PreencherCampo(livro);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar(livro);
                carregarBanco();
            }
        });

    }


    private void carregarDetalhesDoLivro(String livroId){
        BookService bookService = new BookService();
        livro= bookService.getLivroByid(livroId);

        if (livro != null){
            PreencherCampo(livro);
        }else{
            Log.e("EditBookActivity", "Livro não encontrado ou é nulo");
        }
    }

    private class AtualizarLivroTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Obtenha as informações do livro atualizado
                BookCompleteDTO livroAtualizado = atualizarLivro();

                // Execute a atualização no banco de dados
                atualizarLivro(livroAtualizado);

                return true; // Indica que a atualização foi bem-sucedida
            } catch (Exception e) {
                Log.e(App_Livros, "Erro ao atualizar o livro.", e);
                return false; // Indica que ocorreu um erro durante a atualização
            }
        }

        @Override
        protected void onPostExecute(Boolean sucesso) {
            super.onPostExecute(sucesso);

            if (sucesso) {
                // Atualize a interface do usuário conforme necessário após a conclusão da atualização.
                carregarBanco();
                Toast.makeText(EditBookActivity.this, "Livro atualizado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditBookActivity.this, "Falha ao atualizar o livro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void atualizar(BookCompleteDTO livro) {
        new AtualizarLivroTask().execute();
    }


    private void PreencherCampo(BookCompleteDTO livro) {

                if (livro != null) {

                    EditBookTitle.setText(livro.getName());
                    EditBookAuthor.setText(livro.getAuthor());
                    EditBookPublisher.setText(livro.getPublishingCompany());
                    EditBookLanguage.setText(livro.getLanguage());
                    EditBookYear.setText(livro.getYear());
                    spinner.getSelectedItem();
                    EditBookDescription.setText(livro.getDescription());
                    definirCondicaoSelecionada(livro.getState());

                }else {
            Log.e("EditBookActivity", "Livro é nulo. Verifique a inicialização do objeto.");
        }
    }
    private BookCompleteDTO atualizarLivro() {

        String novoTitulo = EditBookTitle.getText().toString();
        String novoAutor = EditBookAuthor.getText().toString();
        String novaEditora = EditBookPublisher.getText().toString();
        String novaLinguagem = EditBookLanguage.getText().toString();
        String novaDescricao = EditBookDescription.getText().toString();
        String novoAno = EditBookYear.getText().toString();
        String novacategoria = spinner.getSelectedItem().toString();

        BookCompleteDTO livros = new BookCompleteDTO();


        livros.setName(novoTitulo);
        livros.setAuthor(novoAutor);
        livros.setPublishingCompany(novaEditora);
        livros.setLanguage(novaLinguagem);
        livros.setDescription(novaDescricao);
        livros.setYear(novoAno);
        livros.setCategory(novacategoria);
        livros.setState(selectedCondition);
        livros.setCreatedAt(Instant.now().atZone(ZoneId.of("GMT-3")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        return livros;
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
    private void atualizarLivro(BookCompleteDTO id) {
        String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";
        String jsonLivro = gson.toJson(id);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonLivro, JSON);
        Request request = new Request.Builder()
                .url(url+"/livros"+ id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Log.i(App_Livros, "Livro atualizado com sucesso.");
            } else {
                Log.e(App_Livros, "Falha ao atualizar o livro. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            Log.e(App_Livros, "Erro ao fazer a chamada de rede para atualizar o livro.", e);
        }
    }




    private void carregarBanco () {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            livros.clear();
            OkHttpClient client = new OkHttpClient();
            String url = "https://serverbibliotroca-production.up.railway.app/api/v1/bibliotroca";

            Request request = new Request.Builder()
                    .url(url+ "/livros")
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
