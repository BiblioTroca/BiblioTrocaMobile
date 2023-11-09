package com.projeto.bibliotroca;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.fragments.modal_variants.RequestExchangeModalFragment;

public class SelectedExchangeLibraryActivity  extends AppCompatActivity {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.selected_exchange_library_layout);

        ImageView btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        Button btnRequestExchange = findViewById(R.id.btnRequestExchange);
        btnRequestExchange.setOnClickListener(event -> {
            RequestExchangeModalFragment modal = new RequestExchangeModalFragment();
            modal.show(getSupportFragmentManager(), "requestExchangeModal");
        });
    }
}
