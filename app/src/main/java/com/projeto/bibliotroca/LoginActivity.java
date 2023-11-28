package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.services.UserService;
import com.projeto.bibliotroca.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    EditText inputEmail;
    EditText inputPassword;
    Button btnLoginAccount;
    TextView btnForgotPassword;
    TextView btnRegisterNow;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.login_layout);

        initializeUIComponents();

        btnLoginAccount.setOnClickListener(event -> {
            prepareCredentialsToAuthenticate();

            Intent openNavigationActivity = new Intent(this, NavigationActivity.class);
            startActivity(openNavigationActivity);
        });

        btnForgotPassword.setOnClickListener(event -> {
            Intent openChangePasswordActivity = new Intent(this, ChangePasswordActivity.class);
            startActivity(openChangePasswordActivity);
        });

        btnRegisterNow.setOnClickListener(event -> {
            Intent openRegisterAccountActivity = new Intent(this, RegisterAccountActivity.class);
            startActivity(openRegisterAccountActivity);
        });
    }

    private void initializeUIComponents() {
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        btnLoginAccount = findViewById(R.id.btnLoginAccount);

        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnRegisterNow = findViewById(R.id.btnRegisterNow);
    }

    private void prepareCredentialsToAuthenticate() {
        UserService userService = new UserService();

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        String token = userService.authenticate(email, password);

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.setToken(token);
    }
}
