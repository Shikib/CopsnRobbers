package com.example.ButtonGames.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.ButtonGames.R;


import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get rid of banner, fill screens the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        // Set what is displayed to main.xml in layout-land
        setContentView(R.layout.main);

        Typeface tf = Typeface.createFromAsset(getAssets(), "abadi_condensed_xtrabold.ttf");

        Button playButton = (Button) findViewById(R.id.buttonPlay);
        playButton.setBackgroundResource(R.drawable.button_background);
        playButton.setTypeface(tf);
        playButton.setTextColor(Color.BLACK);

        Button settingsButton = (Button) findViewById(R.id.buttonSettings);
        settingsButton.setBackgroundResource(R.drawable.button_background);
        settingsButton.setTypeface(tf);
        settingsButton.setTextColor(Color.BLACK);

        Button statsButton = (Button) findViewById(R.id.buttonStats);
        statsButton.setBackgroundResource(R.drawable.button_background);
        statsButton.setTypeface(tf);
        statsButton.setTextColor(Color.BLACK);


    }

    // When play button is pressed, start new SimpleTagActivity with selected obstacle and background
    public void onPlayButton(View view) {
        startActivity(new Intent(this, SelectionMenuActivity.class));
    }

    public void onStatisticsButton(View view) {
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    public void onSettingsButton(View view){
        startActivity(new Intent(this, SettingsMenuActivity.class));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("gameStarted")) {
            Intent i = new Intent(MainMenuActivity.this, SimpleTagActivity.class);
            MainMenuActivity.this.startActivity(i);
        }
    }

}
