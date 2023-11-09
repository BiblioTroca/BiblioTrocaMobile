package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FinalizeProfileActivity extends AppCompatActivity {

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.finalize_profile_layout);

        Button btnFinalize = findViewById(R.id.btnFinalizeAccount);
        btnFinalize.setOnClickListener(event -> {
            Intent openProfileActivity = new Intent(this, ProfileActivity.class);
            startActivity(openProfileActivity);
        });
    }
}
