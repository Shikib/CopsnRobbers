package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.ButtonGames.R;


public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of banner, fill screens the app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        // Make button transparent
        Button buttonRetry = (Button)findViewById(R.id.buttonRetry);
        buttonRetry.setVisibility(View.VISIBLE);
        buttonRetry.setBackgroundColor(Color.TRANSPARENT);

        // Make button transparent
        Button buttonMainMenu = (Button)findViewById(R.id.buttonHome);
        buttonMainMenu.setVisibility(View.VISIBLE);
        buttonMainMenu.setBackgroundColor(Color.TRANSPARENT);

        SharedPreferences stats = getApplicationContext().getSharedPreferences(
                "com.example.ButtonGames", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = stats.edit();
        editor.putInt("games_played", stats.getInt("games_played", 0) + 1);

        if (getIntent().getBooleanExtra("com.example.ButtonGames.winner", false)){
            if (getIntent().getBooleanExtra("com.example.ButtonGames.cop", false))
                setContentView(R.layout.game_over_police_left);
            else
                setContentView(R.layout.game_over_robber_left);
            editor.putInt("left_won", stats.getInt("left_won", 0) + 1);
        } else {
            if (getIntent().getBooleanExtra("com.example.ButtonGames.cop", false))
                setContentView(R.layout.game_over_police_right);
            else
                setContentView(R.layout.game_over_robber_right);
            editor.putInt("right_won", stats.getInt("right_won", 0) + 1);
        }

        editor.commit();
    }

    public void onRetryButton(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", getIntent().getIntExtra("com.example.ButtonGames.oldObstacle", 0));
        i.putExtra("com.example.ButtonGames.theme", getIntent().getIntExtra("com.example.ButtonGames.oldTheme", 0));
        startActivity(i);
        finish();
    }

    public void onHomeButton(View view){
        finish();
    }
}
