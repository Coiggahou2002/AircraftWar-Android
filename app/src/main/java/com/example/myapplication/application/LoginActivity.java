package com.example.myapplication.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Config;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Intent startIntent;

    TextView loginTitle;
    Button loginButton, registerButton;
    TextInputEditText usernameEditor, passwordEditor;
    Editable username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startIntent = new Intent(LoginActivity.this, StartActivity.class);

        setViews();
        createListeners();
    }

    private void setViews() {
        setContentView(R.layout.login);
        loginTitle = findViewById(R.id.login_title);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        usernameEditor = findViewById(R.id.username_input);
        passwordEditor = findViewById(R.id.password_input);
    }

    private void createListeners() {
        usernameEditor.setOnEditorActionListener((v, actionId, event) -> {
            if (
                event != null &&
                (event.getAction() == KeyEvent.ACTION_DOWN) &&
                (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(usernameEditor.getWindowToken(), 0);
                return true;
            }
            return false;
        });
        passwordEditor.setOnEditorActionListener((v, actionId, event) -> {
            if (
                event != null &&
                        (event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            ) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(passwordEditor.getWindowToken(), 0);
                return true;
            }
            return false;
        });
        loginButton.setOnClickListener(v -> {
            if (getEditorContent()) {
                onQuit();
            }
        });
        registerButton.setOnClickListener(v -> {
            if (getEditorContent()) {
                onQuit();
            }
        });
    }

    private boolean getEditorContent() {
        username = usernameEditor.getText();
        password = passwordEditor.getText();
        if (username == null || username.toString().equals("")) {
            loginTitle.setText(R.string.empty_username);
            return false;
        }
        if(password == null || password.toString().equals("")) {
            loginTitle.setText(R.string.empty_password);
            return false;
        }
        return true;
    }

    private void onQuit() {
        Log.i(Config.LOGIN_ACTIVITY_INFO_TAG, username.toString());

        startIntent.putExtra(Config.USERNAME, username.toString());
        startActivity(startIntent);
        finish();
    }
}
