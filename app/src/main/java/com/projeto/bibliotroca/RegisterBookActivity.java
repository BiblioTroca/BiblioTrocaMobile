package com.projeto.bibliotroca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.models.SellerDTO;
import com.projeto.bibliotroca.utils.GlobalConstants;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



 String url = "GlobalConstants.BASE_URL" + "/books.json";

 public static final MediaType JSON = MediaType.get("application/json");

 public static final String App_Livros = "App Livros";

 ImageView btnArrowback;

 private List<BookCompleteDTO> livros = new ArrayList<>();

 private EditText BookTitle;
 private EditText BookAuthor;
 private EditText BookPublisher;
 private EditText BookLanguage;
 private EditText BookYear;
 private EditText BookDescription;

 private Spinner spinner;

 private ConstraintLayout constraintNewCondition;
 private ConstraintLayout constraintGoodCondition;
 private ConstraintLayout constraintUsedCondition;


 private Button btnCadastrar;

 private Gson gson = new Gson();


 private String selectedCondition = "";



 void buildStyles() {
  getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
 }

 @Override
 protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  buildStyles();
  setContentView(R.layout.register_book_layout);

  BookTitle = findViewById(R.id.txtBooktitle);
  BookAuthor = findViewById(R.id.txtBookAuthor);
  BookPublisher = findViewById(R.id.txtPublisher);
  BookLanguage = findViewById(R.id.TxtLanguagem);
  BookYear = findViewById(R.id.txtBookYear);
  BookDescription = findViewById(R.id.txtBookDescription);
  constraintGoodCondition = findViewById(R.id.selectableGoodCondition);
  constraintNewCondition = findViewById(R.id.selectableNewCondition);
  constraintUsedCondition = findViewById(R.id.selectableUsedCondition);
  btnCadastrar = findViewById(R.id.btnRegister);
  spinner = findViewById(R.id.spinner);

  btnArrowback = findViewById(R.id.btnArrowBack);
  btnArrowback.setOnClickListener(event -> finish());

  btnCadastrar.setOnClickListener(e -> salvar());
  carregarFirebase();



  ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  spinner.setAdapter(adapter);
  spinner.setOnItemSelectedListener(this);
 }



 private BookCompleteDTO gerarLivro() {

  BookCompleteDTO livro = new BookCompleteDTO();

  livro.setName(BookTitle.getText().toString());
  livro.setAuthor(BookAuthor.getText().toString());
  livro.setCategory(spinner.getSelectedItem().toString());
  livro.setLanguage(BookLanguage.getText().toString());
  livro.setYear(BookYear.getText().toString());
  livro.setPublishingCompany(BookPublisher.getText().toString());
  livro.setState(selectedCondition);
  livro.setDescription(BookDescription.getText().toString());
  livro.setId(UUID.randomUUID().toString());
  livro.setCreatedAt(Instant.now().atZone(ZoneId.of("GMT-3")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

  SellerDTO seller = new SellerDTO();

  seller.setName("Alvaro");
  seller.setLocation("Itaquera");
  seller.getAvaliationsNumber();
  seller.getAverageRating();


  livro.setSeller(seller);

  Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

  return livro;
 }

  private void salvar() {
   BookCompleteDTO livro = gerarLivro();
   SalvarLivro(livro);
  }


 @Override
 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  String text = parent.getItemAtPosition(position).toString();
  Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
 }

 @Override
 public void onNothingSelected(AdapterView<?> parent) {

 }

//colocar isso em service, mas funciona
 public void SalvarLivro( BookCompleteDTO livros ) {
  String jsonLivros = gson.toJson(livros);

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());
  executor.execute(() -> {
   OkHttpClient client = new OkHttpClient();

   RequestBody body = RequestBody.create(jsonLivros, JSON);
   Request request = new Request.Builder()
           .url(url)
           .post(body)
           .build();
   try (Response response = client.newCall(request).execute()) {
    Log.e(App_Livros, "Resposta: " + response.body().string());
   } catch (IOException e) {
    Log.e(App_Livros, "Erro: ", e);
    throw new RuntimeException(String.valueOf(livros));
   }

   handler.post(() -> {

   });
  });
 }

//Analisar se isso é realmente necessario
 private void carregarFirebase() {
  ExecutorService executor = Executors.newSingleThreadExecutor();
  executor.execute(() -> {
   livros.clear();
   OkHttpClient client = new OkHttpClient();
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

}


}
