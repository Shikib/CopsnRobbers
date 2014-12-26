package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.ButtonGames.R;

/**
 * Created by Sarah on 2014-12-25.
 */
public class SelectionMenuActivity extends Activity{
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

        setContentView(R.layout.selection_menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("inSelectionMenu", true);
    }

    public void onMap0Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 0);
        i.putExtra("com.example.ButtonGames.theme", SettingsMenuActivity.theme);
        startActivity(i);
        finish();
    }
    public void onMap1Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 1);
        i.putExtra("com.example.ButtonGames.theme", SettingsMenuActivity.theme);
        startActivity(i);
        finish();
    }
    public void onMap2Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 2);
        i.putExtra("com.example.ButtonGames.theme", SettingsMenuActivity.theme);
        startActivity(i);
        finish();
    }
}
