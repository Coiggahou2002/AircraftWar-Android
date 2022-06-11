package com.example.myapplication.application;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Config;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandingActivity extends AppCompatActivity {

    Button restartButton, quitButton;
    TextView difficultyText;
    ListView standingList;

    StandingListAdapter listAdapter;
    List<StandingEntry> standingData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViews();
        setDifficultyText();
        setListContent();

        createListeners();
    }

    private void setViews() {
        setContentView(R.layout.standing);
        restartButton = findViewById(R.id.restart_button);
        quitButton = findViewById(R.id.quit_button);
        difficultyText = findViewById(R.id.difficulty_text);
        standingList = findViewById(R.id.standing_list);

        standingList.setDivider(new ColorDrawable(Color.GRAY));
        standingList.setDividerHeight(4);
    }

    private void setDifficultyText() {
        if (getIntent().getIntExtra(Config.ONLINE, 0) == 1) {
            difficultyText.setText(R.string.online_mode);
            difficultyText.append(
                    "\nYour Score: "
                    + getIntent().getIntExtra(Config.SCORE, 0)
                    + "\nOpponent Score: "
                    + getIntent().getIntExtra(Config.OPPONENT_SCORE, 0)
            );
        }
        else {
            switch (getIntent().getIntExtra(Config.DIFFICULTY, 0)) {
                case 0:
                    difficultyText.setText(R.string.easy_mode);
                    break;
                case 1:
                    difficultyText.setText(R.string.normal_mode);
                    break;
                default:
                    difficultyText.setText(R.string.hard_mode);
                    break;
            }
            difficultyText.append("\nYour Score: " + getIntent().getIntExtra(Config.SCORE, 0));
        }
    }

    private void setListContent() {
        prepareData();

        listAdapter = new StandingListAdapter(StandingActivity.this, standingData);
        standingList.setAdapter(listAdapter);
    }

    private void prepareData() {
        standingData = new ArrayList<>();
        standingData.add(new StandingEntry("TestName", "123", "TestTime"));
        standingData.add(new StandingEntry("TestNameNNNNN", "234", "TestTimeTTTTT"));
    }

    private void createListeners() {
        restartButton.setOnClickListener(v -> {
            startActivity(new Intent(StandingActivity.this, StartActivity.class)
                    .putExtra(Config.USERNAME, getIntent().getStringExtra(Config.USERNAME))
            );
            finish();
        });
        quitButton.setOnClickListener(v -> finish());
    }
}
