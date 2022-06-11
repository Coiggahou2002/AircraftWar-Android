package com.example.myapplication.application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.myapplication.Config;
import com.example.myapplication.R;
import com.example.myapplication.network.GameHandler;
import com.example.myapplication.network.GameHandlerNaiveImpl;

public class StartActivity extends AppCompatActivity {

    Intent gameIntent;
    GameHandler networkHandler;

    String username;

    Button easyButton, normalButton, hardButton, onlineButton;
    CheckBox musicCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 请在完成MatchHandler实现后，将此处替换为实现类
        networkHandler = new GameHandlerNaiveImpl();

        username = getIntent().getStringExtra(Config.USERNAME);

        gameIntent = new Intent(StartActivity.this, GameActivity.class);
        gameIntent.putExtra(Config.USERNAME, username);

        setViews();
        createListeners();
    }

    private void setViews() {
        setContentView(R.layout.start_menu);
        easyButton = findViewById(R.id.easy_button);
        normalButton = findViewById(R.id.normal_button);
        hardButton = findViewById(R.id.hard_button);
        onlineButton = findViewById(R.id.online_button);
        musicCheckBox = findViewById(R.id.music_check);
    }

    private void createListeners() {
        easyButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Easy Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 0);
            onQuit();
        });

        normalButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Normal Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 1);
            onQuit();
        });

        hardButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Hard Mode Selected");
            gameIntent.putExtra(Config.DIFFICULTY, 2);
            onQuit();
        });

        onlineButton.setOnClickListener(view -> {
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Online Mode Selected");
            tryMatch();
        });
    }

    private void tryMatch() {
        if (networkHandler.tryMatch(username)) {
            gameIntent.putExtra(Config.DIFFICULTY, 1);
            gameIntent.putExtra(Config.ONLINE, 1);
            gameIntent.putExtra(Config.NETWORK_HANDLER, networkHandler);
            onQuit();
        }
        else {
            // TODO: 此处回头加个GUI文本提示
            Log.i(Config.START_ACTIVITY_INFO_TAG, "Online Mode Failed");
        }
    }

    private void onQuit() {
        // Get Music State
        gameIntent.putExtra(Config.MUSIC_ENABLE, musicCheckBox.isChecked());
        Log.i(Config.START_ACTIVITY_INFO_TAG, "Music: " + musicCheckBox.isChecked());

        // Goto Game
        startActivity(gameIntent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return true;
    }
}