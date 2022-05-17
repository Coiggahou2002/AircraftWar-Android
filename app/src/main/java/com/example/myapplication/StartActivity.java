package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

public class StartActivity extends AppCompatActivity {

    Button easy_button, normal_button, hard_button;
    CheckBox music_check_box;

    private final static String INFO_TAG = "StartActivityInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load GUI & bind Views
        setContentView(R.layout.start_menu);
        easy_button = findViewById(R.id.easy_button);
        normal_button = findViewById(R.id.normal_button);
        hard_button = findViewById(R.id.hard_button);
        music_check_box = findViewById(R.id.music_check);

        // Create Listeners
        easy_button.setOnClickListener(view -> {
            Log.i(INFO_TAG, "Easy Mode Selected");
            playGame();
        });

        normal_button.setOnClickListener(view -> {
            Log.i(INFO_TAG, "Normal Mode Selected");
            playGame();
        });

        hard_button.setOnClickListener(view -> {
            Log.i(INFO_TAG, "Hard Mode Selected");
            playGame();
        });

        music_check_box.setOnClickListener(view -> {
            boolean state = music_check_box.isChecked();
            Log.i(INFO_TAG, "Music is " + state);
        });
    }

    private void playGame() {

    }
}