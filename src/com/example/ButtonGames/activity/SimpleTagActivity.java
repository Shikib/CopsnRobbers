package com.example.ButtonGames.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

import java.util.ArrayList;
import java.util.List;

public class SimpleTagActivity extends Activity{

    private Board board;
    private List<List<Obstacle>> obstacles;
    private List<Bitmap> backgrounds;
    private SimpleTagSurfaceView stSurfaceView;
    private FrameLayout holder;     // holder for everything
    private RelativeLayout buttons; // holder for the buttons
    public RelativeLayout pauseView;
    private Button pause;
    private boolean resume = false;

    private int screenWidth;
    private int screenHeight;

    public static int obstacleMap;
    public static int  backgroundMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of banner, set as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get width and height of board from display
        Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getRealSize(size);
            screenWidth = size.x;
            screenHeight = size.y;

        initObstacles(); // Set up obstacle options to use
        initBackground(); // Set up background options to use

        // Make new board with width and height of display, and correct obstacles
        obstacleMap = getIntent().getIntExtra("com.example.ButtonGames.obstacle", 0);
        List<Obstacle> map = obstacles.get(obstacleMap);
        board = new Board(map, screenWidth, screenHeight);

        // Make new surface view with correct background
        backgroundMap = getIntent().getIntExtra("com.example.ButtonGames.theme", 0);
        Bitmap backgroundType = backgrounds.get(backgroundMap);

        stSurfaceView = new SimpleTagSurfaceView(this, board, backgroundType);

        holder = new FrameLayout(this);
        buttons = new RelativeLayout(this);
        pauseView = new RelativeLayout(this);
        initView(); // Set up left and right buttons


    }



    @Override
    protected void onResume() {
        super.onResume();
        if (resume && pauseView != null)
            pauseView.setVisibility(View.VISIBLE);
        else
            resume = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stSurfaceView != null) {
            stSurfaceView.gameLoopThread.killThread();
        }
    }


    public void initObstacles(){
        List<Obstacle> obstacles0 = new ArrayList<Obstacle>(); // Example map
        obstacles0.add(new Obstacle((double)screenWidth / 5,
                (double) 2*screenWidth/ 5, (double) 10* screenHeight / 20, (double)11* screenHeight / 20));

        List<Obstacle> obstacles1 = new ArrayList<Obstacle>();
        obstacles1.add(new Obstacle((double) 15*screenWidth/ 20, (double) 16* screenWidth / 20,
                (double) 2*screenHeight / 7, (double) 4*screenHeight / 7));

        List<Obstacle> obstacles2 = new ArrayList<Obstacle>();
        obstacles2.add(new Obstacle((double) 15*screenWidth/ 20, (double) 16* screenWidth / 20,
                (double) 2*screenHeight / 7, (double) 4*screenHeight / 7));

        obstacles = new ArrayList<List<Obstacle>>();
        obstacles.add(obstacles0);
        obstacles.add(obstacles1);
        obstacles.add(obstacles2);
    }

    public void initBackground(){
        Bitmap background0 = BitmapFactory.decodeResource(getResources(), R.drawable.map1);
        Bitmap background1 = BitmapFactory.decodeResource(getResources(), R.drawable.blank);

        backgrounds = new ArrayList<Bitmap>();
        backgrounds.add(background0);
        backgrounds.add(background1);
    }

    public void initView() {
        Button left = new Button(this);
        left.setId(123456);

        // Set left button so it can deal with touch events
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSystemUI();
                if (board.getCurrentFrame() < -8)
                    return false;
                int action = event.getAction();
                // Start moving left sprite if button is pressed
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerL().startMoving();
                // Start spinning left sprite when button is let go
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerL().startSpinning();
                return false;
            }
        });

        Button right = new Button(this);
        right.setId(123457);

        // Set right button so it can deal with touch events
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSystemUI();
                if (board.getCurrentFrame() < -8)
                    return false;
                int action = event.getAction();
                // Start moving right sprite if button is pressed
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerR().startMoving();
                // Start spinning right sprite if buttons is let go
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerR().startSpinning();
                return false;
            }
        });

        pause = new Button(this);
        pause.setId(123457);
        pause.setBackgroundResource(R.drawable.pause_button);

        pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                stSurfaceView.gameLoopThread.setRunning(false);
                v.setVisibility(View.GONE);
                pauseView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        // Rules for how buttons are placed on screen
        RelativeLayout.LayoutParams leftRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams rightRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams pauseRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        // Add the buttons to the holder for buttons
        buttons.setLayoutParams(params);
        buttons.addView(left);
        buttons.addView(right);
        buttons.addView(pause);

        // Rule to place button on bottom left of screen
        leftRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        // Set height, color of left button
        leftRules.height = screenHeight / 6;
        leftRules.width = screenWidth / 6;
        left.setLayoutParams(leftRules);
        left.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

        // Rule to place button on bottom right of screen
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        // Set height, color of right button
        rightRules.height = screenHeight / 6;
        rightRules.width = screenWidth / 6;
        right.setLayoutParams(rightRules);
        right.getBackground().setColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY);

        pauseRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        pauseRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        // Set height, color of pause button
        pauseRules.height = screenHeight / 6;
        pauseRules.width = screenWidth / 6;
        pause.setLayoutParams(pauseRules);

        // Add SimpleTagSurfaceView to holder
        holder.addView(stSurfaceView);
        // Add buttons to holder
        holder.addView(buttons);

//        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
//                (Context.LAYOUT_INFLATER_SERVICE);
//        pauseView = inflater.inflate(R.layout.pause_menu, holder, false);
//
//        holder.addView(pauseView);
//
//        pauseView.setVisibility(View.GONE);

        Button home = new Button(this);
        left.setId(223456);

        home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(v.getContext(), MainMenuActivity.class));
                ((Activity) v.getContext()).finish();
                return false;
            }
        });

        Button resume = new Button(this);
        resume.setId(223457);

        resume.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pauseView.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                stSurfaceView.gameLoopThread.setRunning(true);
                return false;
            }
        });

        RelativeLayout.LayoutParams homeRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams resumeRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams statsRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        pauseView.setLayoutParams(params);
        pauseView.addView(home);
        pauseView.addView(resume);

        homeRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        homeRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        homeRules.height = screenHeight / 6;
        homeRules.width = screenWidth / 6;
        home.setLayoutParams(homeRules);
        home.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);

        resumeRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        resumeRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        resumeRules.height = screenHeight / 6;
        resumeRules.width = screenWidth / 6;
        resume.setLayoutParams(resumeRules);
        resume.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);

        holder.addView(pauseView);
        pauseView.setVisibility(View.GONE);

        // Set holder to what is shown on display
        setContentView(holder);
    }


    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        stSurfaceView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            stSurfaceView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

}
