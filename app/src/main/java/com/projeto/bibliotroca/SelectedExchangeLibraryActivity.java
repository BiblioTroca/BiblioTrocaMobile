package com.projeto.bibliotroca;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SelectedExchangeLibraryActivity  extends AppCompatActivity {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.selected_exchange_library_layout);

        Button btnRequestExchange = findViewById(R.id.btnRequestExchange);
        btnRequestExchange.setOnClickListener(event -> {
            RequestExchangeModalFragment modal = new RequestExchangeModalFragment();
            modal.show(getSupportFragmentManager(), "requestExchangeModal");
        });
    }
}
