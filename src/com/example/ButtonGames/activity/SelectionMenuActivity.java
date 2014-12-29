package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class SelectionMenuActivity extends Activity{

    private int theme;
    private ArrayList<List<Obstacle>> obstacles;
    private int buttonHeight;
    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "com.example.ButtonGames", Context.MODE_PRIVATE);

        theme = prefs.getInt("theme", 0);


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

        TextView text = (TextView) findViewById(R.id.textViewSelection);
        Typeface tf = Typeface.createFromAsset(getAssets(), "abadi_condensed_xtrabold.ttf");
        text.setTypeface(tf);
        text.setTextColor(Color.WHITE);

        ViewGroup group = (ViewGroup) findViewById(R.id.linearLayout1);
        Bitmap bitmap = getBackgroundImage().copy(Bitmap.Config.ARGB_8888, true);

        buttonHeight = bitmap.getHeight();
        buttonWidth = bitmap.getWidth();
        initObstacles();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor(getObstacleColour()));
        paint.setStyle(Paint.Style.FILL);


        for (int i = 1; i < group.getChildCount(); i++) {
            ImageButton button = (ImageButton) group.getChildAt(i);

            bitmap = getBackgroundImage().copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(bitmap);

            for (int j = 0; j < obstacles.get(i - 1).size(); j++) {
                Obstacle o = obstacles.get(i - 1).get(j);
                canvas.drawRect((float) o.getXLower(), (float) o.getYLower() + buttonHeight / 12,
                        (float) o.getXUpper(), (float) o.getYUpper() + buttonHeight / 12, paint);
            }

            Drawable background = new BitmapDrawable(getResources(), bitmap);

            button.setBackground(background);
        }
    }

    public Bitmap getBackgroundImage(){
        if (theme == 0){
            return BitmapFactory.decodeResource(getResources(), R.drawable.background0);
        } else if (theme == 1){
            return BitmapFactory.decodeResource(getResources(), R.drawable.background1);
        } else if (theme == 2){
            return BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        } else {
            return BitmapFactory.decodeResource(getResources(), R.drawable.background3);
        }
    }

    public String getObstacleColour() {
        String colour;
        switch (theme) {
            case 0:
                colour = "#6c6c6c";
                break;
            case 1:
                colour = "#dba916";
                break;
            case 2:
                colour = "#19732a";
                break;
            case 3:
                colour = "#b86d2d";
                break;
            default:
                colour = "0xffffff";
                break;
        }

        return colour;
    }

    public void initObstacles(){
        List<Obstacle> obstacles0 = new ArrayList<Obstacle>();
        obstacles0.add(new Obstacle((double) screenWidth / 3, (double) 2 * screenWidth / 3,
                (double) 95 * screenHeight / 240, (double) 105 * screenHeight / 240));

        List<Obstacle> obstacles1 = new ArrayList<Obstacle>();
        obstacles1.add(new Obstacle((double) 3 * screenWidth / 8, (double) 5 * screenWidth / 8,
                (double) 15 * screenHeight / 48, (double) 25 * screenHeight / 48));

        List<Obstacle> obstacles2 = new ArrayList<Obstacle>();
        obstacles2.add(new Obstacle((double) 9 * screenWidth / 40, (double) 11 * screenWidth / 40,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));
        obstacles2.add(new Obstacle((double) 19 * screenWidth / 40, (double) 21 * screenWidth / 40,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));
        obstacles2.add(new Obstacle((double) 29 * screenWidth / 40, (double) 31 * screenWidth / 40,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));

        List<Obstacle> obstacles3 = new ArrayList<Obstacle>();
        obstacles3.add(new Obstacle((double) screenWidth / 4, (double) 3 * screenWidth / 4,
                (double) 95 * screenHeight / 240, (double) 105 * screenHeight / 240));
        obstacles3.add(new Obstacle((double) 22 * screenWidth / 55, (double) 23 * screenWidth / 55,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));

        List<Obstacle> obstacles4 = new ArrayList<Obstacle>();
        obstacles4.add(new Obstacle((double) screenWidth / 7, (double) 3 * screenWidth / 7,
                (double) 185 * screenHeight / 720, (double) 215 * screenHeight / 720));
        obstacles4.add(new Obstacle((double) 4 * screenWidth / 7, (double) 6 * screenWidth / 7,
                (double) 185 * screenHeight / 720, (double) 215 * screenHeight / 720));
        obstacles4.add(new Obstacle((double) screenWidth / 7, (double) 6 * screenWidth / 7,
                (double) 385 * screenHeight / 720, (double) 415 * screenHeight / 720));

        List<Obstacle> obstacles5 = new ArrayList<Obstacle>();
        obstacles5.add(new Obstacle((double) screenWidth / 4, (double) 2 * screenWidth / 4,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles5.add(new Obstacle((double) 2 * screenWidth / 4, (double) 3 * screenWidth / 4,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles5.add(new Obstacle((double) 53 * screenWidth / 220, (double) 57 * screenWidth / 220,
                (double) 5 * screenHeight / 24, (double) 10 * screenHeight / 24));
        obstacles5.add(new Obstacle((double) 163 * screenWidth / 220, (double) 167 * screenWidth / 220,
                (double) 10 * screenHeight / 24, (double) 15 * screenHeight / 24));

        List<Obstacle> obstacles6 = new ArrayList<Obstacle>();
        obstacles6.add(new Obstacle((double) screenWidth / 5, (double) 2 * screenWidth / 5,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) 3 * screenWidth / 5, (double) 4 * screenWidth / 5,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) screenWidth / 5, (double) 2 * screenWidth / 5,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) 3 * screenWidth / 5, (double) 4 * screenWidth / 5,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) 3 * screenWidth / 10, (double) 7 * screenWidth / 10,
                (double) 95 * screenHeight / 240, (double) 105 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) 3 * screenWidth / 20, (double) 4 * screenWidth / 20,
                (double) 45 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles6.add(new Obstacle((double) 16 * screenWidth / 20, (double) 17 * screenWidth / 20,
                (double) 45 * screenHeight / 240, (double) 155 * screenHeight / 240));

        List<Obstacle> obstacles7 = new ArrayList<Obstacle>();
        obstacles7.add(new Obstacle((double) screenWidth / 4, (double) 3 * screenWidth / 4,
                (double) 95 * screenHeight / 240, (double) 105 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 22 * screenWidth / 55, (double) 23 * screenWidth / 55,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));
        obstacles7.add(new Obstacle((double) 2 * screenWidth / 8, (double) 3 * screenWidth / 8,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 53 * screenWidth / 220, (double) 57 * screenWidth / 220,
                (double) 10 * screenHeight / 48, (double) 15 * screenHeight / 48));
        obstacles7.add(new Obstacle((double) 5 * screenWidth / 8, (double) 6 * screenWidth / 8,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 163 * screenWidth / 220, (double) 167 * screenWidth / 220,
                (double) 10 * screenHeight / 48, (double) 15 * screenHeight / 48));
        obstacles7.add(new Obstacle((double) 2 * screenWidth / 8, (double) 3 * screenWidth / 8,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 163 * screenWidth / 220, (double) 167 * screenWidth / 220,
                (double) 25 * screenHeight / 48, (double) 30 * screenHeight / 48));
        obstacles7.add(new Obstacle((double) 5 * screenWidth / 8, (double) 6 * screenWidth / 8,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 163 * screenWidth / 220, (double) 167 * screenWidth / 220,
                (double) 25 * screenHeight / 48, (double) 30 * screenHeight / 48));

        obstacles = new ArrayList<List<Obstacle>>();
        obstacles.add(obstacles0);
        obstacles.add(obstacles1);
        obstacles.add(obstacles2);
        obstacles.add(obstacles3);
        obstacles.add(obstacles4);
        obstacles.add(obstacles5);
        obstacles.add(obstacles6);
        obstacles.add(obstacles7);
    }

    public void onMap0Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 0);
        i.putExtra("com.example.ButtonGames.theme", theme);
        startActivity(i);
        finish();
    }
    public void onMap1Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 1);
        i.putExtra("com.example.ButtonGames.theme", theme);
        startActivity(i);
        finish();
    }
    public void onMap2Button(View view){
        Intent i = new Intent(this, SimpleTagActivity.class);
        i.putExtra("com.example.ButtonGames.obstacle", 2);
        i.putExtra("com.example.ButtonGames.theme", theme);
        startActivity(i);
        finish();
    }
}
