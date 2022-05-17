package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

public class StartActivity extends AppCompatActivity {

    Button easy_button, normal_button, hard_button;
    CheckBox music_check_box;

    Intent game_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load GUI & bind Views
        setContentView(R.layout.start_menu);
        easy_button = findViewById(R.id.easy_button);
        normal_button = findViewById(R.id.normal_button);
        hard_button = findViewById(R.id.hard_button);
        music_check_box = findViewById(R.id.music_check);

        // Init Intent
        game_intent = new Intent(StartActivity.this, GameActivity.class);

        // Create Listeners
        easy_button.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Easy Mode Selected");
            game_intent.putExtra(Config.DIFFICULTY, 0);
            playGame();
        });

        normal_button.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Normal Mode Selected");
            game_intent.putExtra(Config.DIFFICULTY, 1);
            playGame();
        });

        hard_button.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Hard Mode Selected");
            game_intent.putExtra(Config.DIFFICULTY, 2);
            playGame();
        });
    }

    private void playGame() {
        // Get Music State
        game_intent.putExtra(Config.MUSIC_ENABLE, music_check_box.isChecked());
        Log.i(Config.START_ACTIVITY_INFO_TAG, "Music: " + music_check_box.isChecked());

        // Goto Game
        startActivity(game_intent);
    }
}