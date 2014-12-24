package com.example.ButtonGames.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of banner, set as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initMaps(); // Set up map options to use

        // Get width and height of board from display
        Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getRealSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;

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
                int action = event.getAction();
                // Start moving left sprite if button is pressed
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerL().startMoving();
                // Start spinning left sprite when button is let go
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerL().startSpinning();
                return true;
            }
        });

        Button right = new Button(this);
        right.setId(123457);

        // Set right button so it can deal with touch events
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                // Start moving right sprite if button is pressed
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerR().startMoving();
                // Start spinning right sprite if buttons is let go
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerR().startSpinning();
                return true;
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

        // Rule to place button on botton left of screen
        leftRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        left.setLayoutParams(leftRules);

        // Rule to place button on bottom right of screen
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        right.setLayoutParams(rightRules);

        // Add SimpleTagSurfaceView to holder
        holder.addView(stSurfaceView);
        // Add buttons to holder
        holder.addView(buttons);
        // Set holder to what is shown on display
        setContentView(holder);
    }

}
