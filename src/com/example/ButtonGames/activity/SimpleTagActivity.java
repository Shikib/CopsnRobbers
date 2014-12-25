package com.example.ButtonGames.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.*;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

import java.util.ArrayList;
import java.util.List;

public class SimpleTagActivity extends Activity{

    private Board board;
    private List<List<Obstacle>> maps;
    private SimpleTagSurfaceView stSurfaceView;
    private FrameLayout holder;     // holder for everything
    private RelativeLayout buttons; // holder for the buttons
    public static Activity simpleTag;

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleTag = this;

        // Get rid of banner, set as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get width and height of board from display
        Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getRealSize(size);
            screenWidth = size.x;
            screenHeight = size.y;

        initMaps(); // Set up map options to use

        // Make new board with width and height of display, and first map in list maps
        board = new Board(maps.get(0), screenWidth, screenHeight);


        holder = new FrameLayout(this);
        stSurfaceView = new SimpleTagSurfaceView(this, board);
        buttons = new RelativeLayout(this);
        initView(); // Set up left and right buttons


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void initMaps(){
        List<Obstacle> simpleMap = new ArrayList<Obstacle>(); // Example map
        simpleMap.add(new Obstacle((double)screenWidth / 5,
                (double) 2*screenWidth/ 5, (double) 10* screenHeight / 20, (double)11* screenHeight / 20));
        simpleMap.add(new Obstacle((double) 15*screenWidth/ 20, (double) 16* screenWidth / 20,
                (double) 2*screenHeight / 7, (double) 4*screenHeight / 7));

        maps = new ArrayList<List<Obstacle>>();
        maps.add(simpleMap);
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

        // Rules for how buttons are placed on screen
        RelativeLayout.LayoutParams leftRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams rightRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        // Add the buttons to the holder for buttons
        buttons.setLayoutParams(params);
        buttons.addView(left);
        buttons.addView(right);

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

        // Add SimpleTagSurfaceView to holder
        holder.addView(stSurfaceView);
        // Add buttons to holder
        holder.addView(buttons);
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
