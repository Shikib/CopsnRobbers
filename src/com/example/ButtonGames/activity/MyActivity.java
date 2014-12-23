package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.ButtonGames.R;

public class MyActivity extends Activity {
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

        // Set what is displayed to main.xml in layout-land
        setContentView(R.layout.main);
    }

    // When play button is pressed, start new SimpleTagActivity
    public void onPlayButton(View view){
        startActivity(new Intent(this, SimpleTagActivity.class));
    }
}
