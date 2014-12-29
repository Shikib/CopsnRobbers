package com.example.ButtonGames.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;

import android.widget.*;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

import java.util.ArrayList;
import java.util.List;

public class SimpleTagActivity extends Activity{

    private Board board;
    private List<List<Obstacle>> obstacles;
    private SimpleTagSurfaceView stSurfaceView;
    private FrameLayout holder;     // holder for everything
    private RelativeLayout buttons; // holder for the buttons
    public RelativeLayout pauseView;
    private Button pause;
    private boolean resume = false;
    private boolean dialogShown = false;

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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // Get width and height of board from display
        Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getRealSize(size);
            screenWidth = size.x;
            screenHeight = size.y;

        initObstacles(); // Set up obstacle options to use

        // Make new board with width and height of display, and correct obstacles
        obstacleMap = getIntent().getIntExtra("com.example.ButtonGames.obstacle", 0);
        List<Obstacle> map = obstacles.get(obstacleMap);

        board = new Board(map, screenWidth, screenHeight);
        board.setStats(getApplicationContext().getSharedPreferences(
                "com.example.ButtonGames", Context.MODE_PRIVATE));

        // Make new surface view with correct background
        backgroundMap = getIntent().getIntExtra("com.example.ButtonGames.theme", 0);
        stSurfaceView = new SimpleTagSurfaceView(this, board, initBackground(), backgroundMap);


        holder = new FrameLayout(this);
        buttons = new RelativeLayout(this);
        pauseView = new RelativeLayout(this);
        initView(); // Set up left and right buttons

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (resume && pauseView != null) {
            pauseView.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
            stSurfaceView.gameLoopThread.setRunning(true);
        }
        else
            resume = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stSurfaceView.gameLoopThread != null) {
            stSurfaceView.gameLoopThread.setRunning(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.removeView(buttons);
        holder.removeView(pauseView);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // add obstacles and map
        int obstacleMap = getIntent().getIntExtra("com.example.ButtonGames.obstacle", 0);

        savedInstanceState.putBoolean("gameStarted", true);
        savedInstanceState.putInt("obstacleMap", obstacleMap);

        savedInstanceState.putDouble("leftX", board.getPlayerL().getX());
        savedInstanceState.putDouble("leftY", board.getPlayerL().getY());
        savedInstanceState.putDouble("leftDir", board.getPlayerL().getDirection());
        savedInstanceState.putInt("leftScore", board.getPlayerL().getScore());
        savedInstanceState.putBoolean("leftState", board.getPlayerL().getState());

        savedInstanceState.putDouble("rightX", board.getPlayerR().getX());
        savedInstanceState.putDouble("rightY", board.getPlayerR().getY());
        savedInstanceState.putDouble("rightDir", board.getPlayerR().getDirection());
        savedInstanceState.putInt("rightScore", board.getPlayerR().getScore());

        savedInstanceState.putInt("currentFrame", board.getCurrentFrame());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            board.setObstacles(obstacles.get(savedInstanceState.getInt("obstacleMap")));

            board.getPlayerL().setX(savedInstanceState.getDouble("leftX"));
            board.getPlayerL().setY(savedInstanceState.getDouble("leftY"));
            board.getPlayerL().setDirection(savedInstanceState.getDouble("leftDir"));
            board.getPlayerL().setScore(savedInstanceState.getInt("leftScore"));
            board.getPlayerL().setState(savedInstanceState.getBoolean("leftState"));

            board.getPlayerR().setX(savedInstanceState.getDouble("rightX"));
            board.getPlayerR().setY(savedInstanceState.getDouble("rightY"));
            board.getPlayerR().setDirection(savedInstanceState.getDouble("rightDir"));
            board.getPlayerR().setScore(savedInstanceState.getInt("rightScore"));
            board.getPlayerR().setState(!savedInstanceState.getBoolean("leftState"));

            board.setCurrentFrame(savedInstanceState.getInt("currentFrame"));

            pauseView.setVisibility(View.VISIBLE);
        }
    }

    public void initObstacles(){
        List<Obstacle> obstacles0 = new ArrayList<Obstacle>();
        obstacles0.add(new Obstacle((double) screenWidth / 3, (double) 2 * screenWidth / 3,
                (double) 95 * screenHeight / 240, (double) 105 * screenHeight / 240));

        List<Obstacle> obstacles1 = new ArrayList<Obstacle>();
        obstacles1.add(new Obstacle((double) 27 * screenWidth / 55, (double) 28 * screenWidth / 55,
                (double) 24 * screenHeight / 540, (double) 5 * screenHeight / 18));
        obstacles1.add(new Obstacle((double) 27 * screenWidth / 55, (double) 28 * screenWidth / 55,
                (double) 10 * screenHeight / 18, (double) 424 * screenHeight / 540));

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
        obstacles3.add(new Obstacle((double) 27 * screenWidth / 55, (double) 28 * screenWidth / 55,
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
                (double) 45 * screenHeight / 240, (double) 100 * screenHeight / 240));
        obstacles5.add(new Obstacle((double) 163 * screenWidth / 220, (double) 167 * screenWidth / 220,
                (double) 100 * screenHeight / 240, (double) 155 * screenHeight / 240));

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
        obstacles7.add(new Obstacle((double) 27 * screenWidth / 55, (double) 28 * screenWidth / 55,
                (double) 5 * screenHeight / 24, (double) 15 * screenHeight / 24));
        obstacles7.add(new Obstacle((double) 2 * screenWidth / 8, (double) 3 * screenWidth / 8,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 5 * screenWidth / 8, (double) 6 * screenWidth / 8,
                (double) 45 * screenHeight / 240, (double) 55 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 2 * screenWidth / 8, (double) 3 * screenWidth / 8,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));
        obstacles7.add(new Obstacle((double) 5 * screenWidth / 8, (double) 6 * screenWidth / 8,
                (double) 145 * screenHeight / 240, (double) 155 * screenHeight / 240));

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

    public Bitmap initBackground(){
        if (backgroundMap == 0){
            return BitmapFactory.decodeResource(getResources(), R.drawable.board0);
        } else if (backgroundMap == 1){
            return BitmapFactory.decodeResource(getResources(), R.drawable.board1);
        } else if (backgroundMap == 2){
            return BitmapFactory.decodeResource(getResources(), R.drawable.board2);
        } else {
            return BitmapFactory.decodeResource(getResources(), R.drawable.board3);
        }
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

        pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pauseView.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
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
        leftRules.height = screenHeight /2;
        leftRules.width = screenWidth / 3;
        left.setLayoutParams(leftRules);
        left.getBackground().setAlpha(0);

        // Rule to place button on bottom right of screen
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


        // Set height, color of right button
        rightRules.height = screenHeight /2;
        rightRules.width = screenWidth / 3;
        right.setLayoutParams(rightRules);
        right.getBackground().setAlpha(0);

        pauseRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        pauseRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        pauseRules.setMargins( screenHeight / 36, screenHeight / 36,  screenHeight /36, screenHeight /36);

        // Set height, color of pause button
        pauseRules.height = screenWidth / 15;
        pauseRules.width = screenWidth / 15;
        pause.setLayoutParams(pauseRules);
        pause.setBackgroundResource(R.drawable.pause_button);
        pause.getBackground().setAlpha(64*3);

        // Add SimpleTagSurfaceView to holder
        holder.addView(stSurfaceView);
        // Add buttons to holder
        holder.addView(buttons);

        Button home = new Button(this);
        left.setId(223456);

        home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dialogShown)
                    return false;
                AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper(v.getContext(), android.R.style.Theme_Holo_Dialog));
                final View view = v;
                builder.setMessage("Are you sure you want quit the game?")
                        .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity) view.getContext()).finish();
                                dialogShown = false;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                hideSystemUI();
                                dialogShown = false;
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                dialogShown = true;
                return false;
            }
        });

        Button restart = new Button(this);
        restart.setId(223457);

        restart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dialogShown)
                    return false;
                AlertDialog.Builder builder = new AlertDialog.Builder( new ContextThemeWrapper(v.getContext(), android.R.style.Theme_Holo_Dialog));
                final View view = v;
                builder.setMessage("Are you sure you want restart the game?")
                        .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                board.initSprites(0,0, (int) (Math.random() * 2), (int) (Math.random() * 2));
                                board.resetSprites(-1);
                                board.setCurrentFrame(-40);

                                dialog.cancel();
                                hideSystemUI();
                                dialogShown = false;

                                pauseView.setVisibility(View.GONE);
                                pause.setVisibility(View.VISIBLE);
                                stSurfaceView.gameLoopThread.setRunning(true);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                hideSystemUI();
                                dialogShown = false;
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                dialogShown = true;
                return false;
            }
        });

        Button resume = new Button(this);
        resume.setId(223458);

        resume.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (board.getCurrentFrame() <= 0)
                    board.setCurrentFrame((board.getCurrentFrame()/10)*10 - 9);
                pauseView.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                return false;
            }
        });

        TextView paused = new TextView(this);
        paused.setText("PAUSED");
        paused.setTextColor(Color.RED);
        paused.setTypeface(Typeface.createFromAsset(getAssets(), "abadi_condensed_xtrabold.ttf"));
        paused.setTextSize(screenHeight / 15);

        RelativeLayout.LayoutParams pausedRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams homeRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams restartRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams resumeRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        pauseView.setLayoutParams(params);
        pauseView.addView(home);
        pauseView.addView(restart);
        pauseView.addView(resume);
        pauseView.addView(paused);

        pausedRules.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        paused.setLayoutParams(pausedRules);

        homeRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        homeRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        homeRules.setMargins(screenHeight / 36, screenHeight / 36, screenHeight / 36, screenHeight / 36);
        homeRules.height = screenWidth / 15;
        homeRules.width = screenWidth / 15;

        home.setLayoutParams(homeRules);
        home.setBackgroundResource(R.drawable.home_button);
        home.getBackground().setAlpha(64 * 3);

        Typeface tf = Typeface.createFromAsset(getAssets(), "abadi_condensed_xtrabold.ttf");
        home.setTypeface(tf);

        restartRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        restartRules.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        restartRules.setMargins( screenHeight / 36, screenHeight / 36, screenHeight /36, screenHeight /36);
        restartRules.height = screenWidth / 15;
        restartRules.width = screenWidth / 15;


        restart.setLayoutParams(restartRules);
        restart.setBackgroundResource(R.drawable.retry_button);
        restart.getBackground().setAlpha(64 * 3);

        resumeRules.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        resumeRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        resumeRules.setMargins( screenHeight / 36, screenHeight / 36,  screenHeight /36, screenHeight /36);

        resumeRules.height = screenWidth / 15;
        resumeRules.width = screenWidth / 15;
        resume.setLayoutParams(resumeRules);
        resume.setBackgroundResource(R.drawable.play_button);
        resume.getBackground().setAlpha(64*3);


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
