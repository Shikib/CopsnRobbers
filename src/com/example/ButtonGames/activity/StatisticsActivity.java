package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.ButtonGames.R;

/**
 * Created by hp on 26/12/2014.
 */
public class StatisticsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
    }

    public void onResetStatistics(View view) {
        SharedPreferences stats = getApplicationContext().getSharedPreferences(
                "com.example.ButtonGames", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = stats.edit();
        editor.putInt("games_played", 0);
        editor.putInt("left_won", 0);
        editor.putInt("right_won", 0);

        editor.commit();
        initTextView();
    }

    public void onBackButton(View view) {
        finish();
    }
}
