package com.projeto.bibliotroca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.fragments.modal_variants.RequestExchangeModalFragment;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.services.BookService;

public class SelectedExchangeLibraryActivity extends AppCompatActivity {
    TextView txtBookName;
    TextView txtAuthor;
    TextView txtCategory;
    TextView txtLanguage;
    TextView txtYear;
    TextView txtPublishingCompany;
    TextView txtState;
    TextView txtDescription;
    TextView txtSellerName;
    TextView txtAverageRating;
    TextView txtAvaliationsNumber;
    TextView txtLocation;
    TextView txtBeforeNewExchange;
    ImageView btnArrowBack;
    Button btnWhatsapp;
    Button btnRequestExchange;
    BookCompleteDTO bookToShow;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.selected_exchange_library_layout);

        initializeUIComponents();

        showBookDetails();

        btnArrowBack.setOnClickListener(event -> finish());

        btnWhatsapp.setOnClickListener(event -> {
            String whatsappURL = "https://api.whatsapp.com/send?phone=55" + bookToShow.getSeller().getPhoneNumber();

            Intent openWhatsapp = new Intent(Intent.ACTION_VIEW);
            openWhatsapp.setData(Uri.parse(whatsappURL));

            startActivity(openWhatsapp);
        });

        btnRequestExchange.setOnClickListener(event -> {
            RequestExchangeModalFragment modal = new RequestExchangeModalFragment();
            modal.show(getSupportFragmentManager(), "requestExchangeModal");
        });
    }

    private void initializeUIComponents() {
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtCategory = findViewById(R.id.txtCategory);
        txtLanguage = findViewById(R.id.txtLanguage);
        txtYear = findViewById(R.id.txtYear);
        txtPublishingCompany = findViewById(R.id.txtPuslishingCompany);
        txtState = findViewById(R.id.txtState);
        txtDescription = findViewById(R.id.txtDescription);
        txtSellerName = findViewById(R.id.txtSellerName);
        txtAverageRating = findViewById(R.id.txtAverageRating);
        txtAvaliationsNumber = findViewById(R.id.txtAvaliationsNumber);
        txtLocation = findViewById(R.id.txtLocation);
        txtBeforeNewExchange = findViewById(R.id.txtBeforeNewExchange);

        btnArrowBack = findViewById(R.id.btnArrowBack);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnRequestExchange = findViewById(R.id.btnRequestExchange);
    }

    private void showBookDetails() {
        Intent receivedIntentFromItem = getIntent();

        if (receivedIntentFromItem != null) {
            String id = receivedIntentFromItem.getStringExtra("bookId");

            BookService bookService = new BookService();

            bookToShow = bookService.getLivroByid(id);

            setBookDataInTheInterface(bookToShow);
        }
    }

    private void setBookDataInTheInterface(BookCompleteDTO book) {

        if (book != null) {
            String author = "por " + book.getAuthor();
            String seller = "Enviado por " ;
            if (book.getSeller() != null) {
                if (book.getSeller().getName() != null) {
                    seller += book.getSeller().getName();
                }

                if (book.getSeller().getSurname() != null) {
                    seller += " " + book.getSeller().getSurname();
                }
            } else {

                seller += "Nome do vendedor indisponível";
            }
            double averageRating = book.getSeller().getAverageRating();
            String averageRatingString = (averageRating != 0) ? String.valueOf(averageRating) : "N/A";

            //String averageRating = String.valueOf(book.getSeller().getAverageRating());
            String avaliationsNumber = "(" + String.valueOf(book.getSeller().getAvaliationsNumber()) + ")";
            String beforeNewExchange = "Negocie diretamente com " + seller + " e defina os detalhes da troca antes de prosseguir com a solicitação. Toque abaixo para iniciar a conversa.";

            txtBookName.setText(book.getName());
            txtAuthor.setText(author);
            txtCategory.setText(book.getCategory());
            txtLanguage.setText(book.getLanguage());
            txtYear.setText(book.getYear());
            txtPublishingCompany.setText(book.getPublishingCompany());
            txtState.setText(book.getState());
            txtDescription.setText(book.getDescription());
            txtSellerName.setText(seller);
            txtAverageRating.setText(averageRatingString);
            txtAvaliationsNumber.setText(avaliationsNumber);
            txtLocation.setText(book.getSeller().getLocation());
            txtBeforeNewExchange.setText(beforeNewExchange);
        } else {
            Toast.makeText(this, "Error: Book is null", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
