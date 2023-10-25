package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.profile_layout);

        Button btnUpdateAccount = findViewById(R.id.btnUpdateAccount);
        btnUpdateAccount.setOnClickListener(event -> {
            Intent openSelectedExchange = new Intent(this, SelectedExchangeActivity.class);
            startActivity(openSelectedExchange);
        });
    }
}
