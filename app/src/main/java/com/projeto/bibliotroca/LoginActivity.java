package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white500));
        View decorView = getWindow().getDecorView();
        int flags = decorView.getSystemUiVisibility(); // retrieve current flags
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flags
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.login_activity);

        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(event -> {
            Intent nextStepSignUp = new Intent(this, FinalizeProfileActivity.class);
            startActivity(nextStepSignUp);
        });
    }
}
