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
import com.projeto.bibliotroca.utils.AlertModal;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText inputEmail;
    EditText inputNewPassword;
    EditText inputConfirmNewPassword;
    Button btnChangePassword;
    TextView btnLoginNow;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.change_password);

        initializeUIComponents();

        btnChangePassword.setOnClickListener(event -> prepareCredentialsToUpdate());

        btnLoginNow.setOnClickListener(event -> {
            Intent openLoginActivity = new Intent(this, LoginActivity.class);
            startActivity(openLoginActivity);
        });
    }

    private void initializeUIComponents() {
        inputEmail = findViewById(R.id.inputEmail);
        inputNewPassword = findViewById(R.id.inputNewPassword);
        inputConfirmNewPassword = findViewById(R.id.inputConfirmNewPassword);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLoginNow = findViewById(R.id.btnLoginNow);
    }

    private void prepareCredentialsToUpdate() {
        UserService userService = new UserService();

        String email = inputEmail.getText().toString();
        String newPassword = inputNewPassword.getText().toString();
        String confirmNewPassword = inputConfirmNewPassword.getText().toString();

        if (newPassword.equals(confirmNewPassword)) {
            userService.updatePassword(email, newPassword);

            Intent openNavigationActivity = new Intent(this, NavigationActivity.class);
            startActivity(openNavigationActivity);
        } else {
            AlertModal.show(this, "As senhas digitadas são diferentes!");

            inputNewPassword.setText("");
            inputConfirmNewPassword.setText("");
        }
    }
}
