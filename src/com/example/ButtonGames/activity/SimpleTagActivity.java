package com.example.ButtonGames.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.model.Sprite;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initMaps();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        board = new Board(maps.get(0), metrics.widthPixels,metrics.heightPixels);

        holder = new FrameLayout(this);
        stSurfaceView = new SimpleTagSurfaceView(this, board);
        buttons = new RelativeLayout(this);
        initView();
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
        List<Obstacle> simpleMap = new ArrayList<Obstacle>();
        simpleMap.add(new Obstacle(100.00, 500.00, 100.00, 115.00));
        simpleMap.add(new Obstacle(300.00, 315.00, 200.00, 350.00));
        maps = new ArrayList<List<Obstacle>>();
        maps.add(simpleMap);
    }

    public void initView() {
        Button left = new Button(this);
        left.setId(123456);

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerL().startMoving();
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerL().startSpinning();
                return true;
            }
        });

        Button right = new Button(this);
        right.setId(123457);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
                    board.getPlayerR().startMoving();
                else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL)
                    board.getPlayerR().startSpinning();
                return true;
            }
        });

        TextView scoreL = new TextView(this);
        scoreL.setText("" + board.getPlayerL().getScore());
        scoreL.setTextColor(Color.GREEN);
        scoreL.setTextSize(30f);
        scoreL.setId(654321);


        TextView scoreR = new TextView(this);
        scoreR.setText("" + board.getPlayerR().getScore());
        scoreR.setTextColor(Color.MAGENTA);
        scoreR.setTextSize(30f);
        scoreR.setId(754321);

        RelativeLayout.LayoutParams leftRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams rightRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        buttons.setLayoutParams(params);
        buttons.addView(left);
        buttons.addView(right);
        buttons.addView(scoreL);
        buttons.addView(scoreR);

        leftRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        left.setLayoutParams(leftRules);

        rightRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        right.setLayoutParams(rightRules);

        holder.addView(stSurfaceView);
        holder.addView(buttons);
        setContentView(holder);
    }

    /*public void addListenerOnButtons(){
        stSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
                boolean state; // completely unnecessary to put there, but intelliJ won't let me compile otherwise

                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        if (x < 500 && y > 200)
                            board.getPlayerL().startMoving();
                        else if (x > 500 && y > 200)
                            board.getPlayerR().startMoving();
                        else
                            return false;
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        state = false;
                        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                            x = (int) motionEvent.getX(i);
                            y = (int) motionEvent.getY(i);

                            if (x < 500 && y > 200) {
                                board.getPlayerL().startMoving();
                                state = true;
                            }
                            else if (x > 500 && y > 200) {
                                board.getPlayerR().startMoving();
                                state = true;
                            }
                        }
                        return state;
                    case MotionEvent.ACTION_UP:
                        if (x < 500 && y > 200)
                            board.getPlayerL().startSpinning();
                        else if (x > 500 && y > 200)
                            board.getPlayerR().startSpinning();
                        else
                            return false;
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        if (x < 500 && y > 200)
                            board.getPlayerL().startSpinning();
                        else if (x > 500 && y > 200)
                            board.getPlayerR().startSpinning();
                        else
                            return false;
                        return true;
                    case MotionEvent.ACTION_POINTER_UP:
                        state = false;
                        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                            x = (int) motionEvent.getX(i);
                            y = (int) motionEvent.getY(i);

                            if (x < 500 && y > 200) {
                                board.getPlayerL().startSpinning();
                                state = true;
                            }
                            else if (x > 500 && y > 200) {
                                board.getPlayerR().startSpinning();
                                state = true;
                            }
                        }
                        return state;
                    default:
                        return true;
                }
            }
        });
    }*/
}
