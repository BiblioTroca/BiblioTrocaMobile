package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.projeto.bibliotroca.models.UserDTO;
import com.projeto.bibliotroca.services.UserService;
import com.projeto.bibliotroca.utils.SessionManager;

import java.util.UUID;

public class RegisterAccountActivity extends AppCompatActivity {
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPhone;
    EditText inputCEP;
    Button btnCreateAccount;
    TextView btnLoginNow;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.register_account_layout);

        initializeUIComponents();

        btnCreateAccount.setOnClickListener(event -> {
            prepareNewUserToAdd();

            Intent openNavigationActivity = new Intent(this, NavigationActivity.class);
            startActivity(openNavigationActivity);
        });

        btnLoginNow.setOnClickListener(event -> {
            Intent openLoginActivity = new Intent(this, LoginActivity.class);
            startActivity(openLoginActivity);
        });
    }

    private void initializeUIComponents() {
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputPhone = findViewById(R.id.inputPhone);
        inputCEP = findViewById(R.id.inputCEP);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLoginNow = findViewById(R.id.btnLoginNow);
    }

    private void prepareNewUserToAdd() {
        UserService userService = new UserService();
        UserDTO user = new UserDTO();

        String userId = UUID.randomUUID().toString();

        user.setId(userId);
        user.setFirstName(inputFirstName.getText().toString());
        user.setLastName(inputLastName.getText().toString());
        user.setEmail(inputEmail.getText().toString());
        user.setPassword(inputPassword.getText().toString());
        user.setPhoneNumber(inputPhone.getText().toString());
        user.setLocation(inputCEP.getText().toString());

        String token = userService.createUser(user);

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.setToken(token);
    }
}
