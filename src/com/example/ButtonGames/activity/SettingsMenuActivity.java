package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.ButtonGames.R;

/**
 * Created by Sarah on 2014-12-25.
 */
public class SettingsMenuActivity extends Activity {
    public static int theme = 0; // default theme

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

        setContentView(R.layout.settings_menu);
    }

    public void onTheme0Button(View view){
        theme = 0;
        makeToast("Stone theme set");
    }
    public void onTheme1Button(View view){
        theme = 1;
        makeToast("Sand theme set");
    }
    public void onTheme2Button(View view){
        theme = 2;
        makeToast("Grass theme set");
    }
    public void onTheme3Button(View view){
        theme = 3;
        makeToast("Bamboo theme set");
    }

    public void makeToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onBackButton(View view){
        finish();
    }


}
