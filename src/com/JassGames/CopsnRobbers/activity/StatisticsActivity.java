package com.JassGames.CopsnRobbers.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.JassGames.CopsnRobbers.R;


public class StatisticsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        hideNavigation();
        setContentView(R.layout.statistics_screen);
        initTextView();
    }

    public void hideNavigation() {
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

        setContentView(R.layout.statistics_screen);

        initTextView();
    }

    public void initTextView() {
        TextView text = (TextView) findViewById(R.id.statistics_view);
        SharedPreferences stats = getApplicationContext().getSharedPreferences(
                "com.example.ButtonGames", Context.MODE_PRIVATE);

        text.setTextSize(20);

        text.setText("Games Played: " + stats.getInt("games_played", 0) + " times\n");
        text.append("Left Side Won: " + stats.getInt("left_won", 0) + " times\n");
        text.append("Right Side Won: " + stats.getInt("right_won", 0) + " times\n");
        text.append("Total Captures: " + stats.getInt("total_captures", 0) + " times\n");
        text.append("Left Captures: " + stats.getInt("left_captures", 0) + " times\n");
        text.append("Right Captures: " + stats.getInt("right_captures", 0) + " times\n");
        text.append("Average Time: " + stats.getInt("average_capture_frame", 0)/10 + " seconds\n");
        text.append("Total Escapes: " + stats.getInt("total_escapes", 0) + " times\n");
        text.append("Left Escapes: " + stats.getInt("left_escapes", 0) + " times\n");
        text.append("Right Escapes: " + stats.getInt("right_escapes", 0) + " times\n");


        Typeface tf = Typeface.createFromAsset(getAssets(), "abadi_condensed_xtrabold.ttf");
        text.setTypeface(tf);
        text.setTextColor(Color.WHITE);
        text.setTextSize(25f);

        Button backButton = (Button) findViewById(R.id.buttonBack);
        backButton.setBackgroundResource(R.drawable.button_background);
        backButton.setTypeface(tf);
        backButton.setTextColor(Color.BLACK);

        Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setBackgroundResource(R.drawable.button_background);
        resetButton.setTypeface(tf);
        resetButton.setTextColor(Color.BLACK);
    }

    public void onResetStatistics(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog));
        builder.setMessage("Are you sure you want to reset all your stats?")
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences stats = getApplicationContext().getSharedPreferences(
                                "com.example.ButtonGames", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = stats.edit();
                        editor.putInt("games_played", 0);
                        editor.putInt("left_won", 0);
                        editor.putInt("right_won", 0);
                        editor.putInt("total_captures", 0);
                        editor.putInt("left_captures", 0);
                        editor.putInt("right_captures", 0);
                        editor.putInt("average_capture_frame", 0);
                        editor.putInt("total_escapes", 0);
                        editor.putInt("left_escapes", 0);
                        editor.putInt("right_captures", 0);

                        editor.commit();
                        initTextView();

                        dialog.cancel();
                        hideNavigation();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        hideNavigation();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackButton(View view) {
        finish();
    }
}
