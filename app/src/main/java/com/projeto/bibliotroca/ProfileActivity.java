package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.fragments.modal_variants.DeleteAccountModalFragment;

public class ProfileActivity extends AppCompatActivity {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.profile_layout);

        ImageView btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        Button btnUpdateAccount = findViewById(R.id.btnUpdateAccount);
        btnUpdateAccount.setOnClickListener(event -> {
            Intent openNavigationActivity = new Intent(this, NavigationActivity.class);
            startActivity(openNavigationActivity);
        });

        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(event -> {
            DeleteAccountModalFragment modal = new DeleteAccountModalFragment();
            modal.show(getSupportFragmentManager(), "deleteAccountModal");
        });
    }
}
