package com.example.myapplication.application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class StandingActivity extends AppCompatActivity {

    Button deleteButton, restartButton, quitButton;
    TextView difficultyText;
    TableLayout standingTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViews();
    }

    private void setViews() {
        setContentView(R.layout.standing);
        deleteButton = findViewById(R.id.delete_button);
        restartButton = findViewById(R.id.restart_button);
        quitButton = findViewById(R.id.quit_button);
        difficultyText = findViewById(R.id.difficulty_text);
        standingTable = findViewById(R.id.standing_table);
    }
}
