package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.services.BookService;

public class BookDetailsActivity extends AppCompatActivity {

    TextView txtAuthorName;
    TextView txtBookCategory;
    TextView txtBookLanguage;
    TextView txtReleaseYear;
    TextView txtBookPublisher;

    TextView txtBookCondition;

    TextView txtBookDescription;

    ImageView btnArrowBack;

    BookCompleteDTO bookToShow;


    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.book_details_layout);

        UIComponents();
        showBookDetails();


        btnArrowBack = findViewById(R.id.btnArrowBack);

        btnArrowBack.setOnClickListener(event -> finish());
    }
        private void UIComponents(){
            txtAuthorName = findViewById(R.id.txtAuthorName);
            txtBookCategory = findViewById(R.id.txtBookCategory);
            txtBookLanguage = findViewById(R.id.txtBookLanguage);
            txtReleaseYear = findViewById(R.id.txtBookReleaseYear);
            txtBookPublisher = findViewById(R.id.txtBookPublisher);
            txtBookCondition = findViewById(R.id.TxtBookCondition);
            txtBookDescription = findViewById(R.id.TxtBookDescription);

            btnArrowBack = findViewById(R.id.btnArrowBack);
        }


        private void showBookDetails() {
            Intent receivedIntentFromItem = getIntent();

            if (receivedIntentFromItem != null) {
                String id = receivedIntentFromItem.getStringExtra("bookId");

                BookService bookService = new BookService();

                bookToShow = bookService.getBookById(id);

                setBookInterface(bookToShow);
            }
}
    private void setBookInterface(BookCompleteDTO book){
            txtAuthorName.setText(book.getAuthor());
            txtBookCategory.setText(book.getCategory());
            txtBookLanguage.setText(book.getLanguage());
            txtReleaseYear.setText(book.getYear());
            txtBookPublisher.setText(book.getPublishingCompany());
            txtBookCondition.setText(book.getState());
            txtBookDescription.setText(book.getDescription());
        }
    }

