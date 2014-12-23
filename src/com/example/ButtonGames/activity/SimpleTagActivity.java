package com.example.ButtonGames.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.model.Sprite;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SimpleTagActivity extends Activity{

    private Board board;
    private List<List<Obstacle>> maps;
    private Timer timer;
    private TimerTask timerTask;
    private SimpleTagSurfaceView stSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMaps();
        board = new Board(maps.get(0));
        stSurfaceView = new SimpleTagSurfaceView(this, board);
        setContentView(stSurfaceView);
        addListenerOnButtons();

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
        simpleMap.add(new Obstacle(500.00, 800.00, 500.00, 800.00));
        maps = new ArrayList<List<Obstacle>>();
        maps.add(simpleMap);
    }


    public void addListenerOnButtons(){
        stSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
                Sprite sprite;

                if (x < 400 && y > 185)
                    sprite = board.getPlayerL();
                else if (x > 400 && y > 185)
                    sprite = board.getPlayerR();
                else
                    return false;

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sprite.startMoving();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sprite.startSpinning();
                }

                return true;
            }
        });
    }
}
