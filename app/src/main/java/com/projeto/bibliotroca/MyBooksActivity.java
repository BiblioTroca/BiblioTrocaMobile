package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class MyBooksActivity  extends AppCompatActivity {

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.mybooks_layout);


        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(event->{
            Intent bookdetails = new Intent(this, BookDetailsActivity.class);
            startActivity(bookdetails);
        });


        ImageButton btnAddBook = findViewById(R.id.btn_addbook);
        btnAddBook.setOnClickListener(event ->{
            Intent openmybooks = new Intent(this, RegisterBookActivity.class);
            startActivity(openmybooks);
        });


        ImageButton btnDeleteBook = findViewById(R.id.btn_deletebook);
        btnDeleteBook.setOnClickListener(event ->{
            DeleteBookModalFragment modal = new DeleteBookModalFragment();
            modal.show(getSupportFragmentManager(),"deletebookmodal");

        });


    }


}
