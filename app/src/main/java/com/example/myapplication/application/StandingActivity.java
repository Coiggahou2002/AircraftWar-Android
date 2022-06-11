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

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class StandingActivity extends AppCompatActivity {

    Button restartButton, quitButton;
    TextView difficultyText;
    ListView standingList;

    StandingListAdapter listAdapter;
    List<StandingEntry> standingData;

    Intent restartIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restartIntent = new Intent(StandingActivity.this, StartActivity.class);

        setViews();

        createListeners();
        setListContent();
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

    private void createListeners() {
        restartButton.setOnClickListener(v -> {
            startActivity(restartIntent);
            finish();
        });
        quitButton.setOnClickListener(v -> {
            finish();
        });
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
}
