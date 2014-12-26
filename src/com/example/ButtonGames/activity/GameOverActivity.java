package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.ButtonGames.R;

/**
 * Created by Sarah on 2014-12-24.
 */
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

        if (getIntent().getBooleanExtra("com.example.ButtonGames.winner", false)){
            setContentView(R.layout.game_over_green);
        } else {
            setContentView(R.layout.game_over_purple);
        }

        // Make button transparent
        Button buttonRetry = (Button)findViewById(R.id.buttonRetry);
        buttonRetry.setVisibility(View.VISIBLE);
        buttonRetry.setBackgroundColor(Color.TRANSPARENT);

        // Make button transparent
        Button buttonMainMenu = (Button)findViewById(R.id.buttonMainMenu);
        buttonMainMenu.setVisibility(View.VISIBLE);
        buttonMainMenu.setBackgroundColor(Color.TRANSPARENT);
    }

    public void onRetryButton(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", getIntent().getIntExtra("com.example.ButtonGames.oldObstacle", 0));
        i.putExtra("com.example.ButtonGames.background", getIntent().getIntExtra("com.example.ButtonGames.oldBackground", 0));
        startActivity(i);
        finish();
    }

    public void onMainMenuButton(View view){
        finish();
    }
}
