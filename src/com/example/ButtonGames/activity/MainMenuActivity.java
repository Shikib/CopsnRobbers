package com.example.ButtonGames.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
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
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        // Set what is displayed to main.xml in layout-land
        setContentView(R.layout.main);

        // Make button transparent
        Button theButton = (Button)findViewById(R.id.button);
        theButton.setVisibility(View.VISIBLE);
        theButton.setBackgroundColor(Color.TRANSPARENT);

        // Get width and height of board from display
        Point size = new Point();
        this.getWindowManager().getDefaultDisplay().getRealSize(size);
    }

    // When play button is pressed, start new SimpleTagActivity with selected obstacle and background
    public void onPlayButton(View view){
        startActivity(new Intent(this, SelectionMenuActivity.class));
    }

    public void onSettingsButton(View view){
        startActivity(new Intent(this, SettingsMenuActivity.class));
    }

}
