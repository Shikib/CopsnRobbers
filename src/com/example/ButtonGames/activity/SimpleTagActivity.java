package com.example.ButtonGames.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
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

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                board.updateBoard();
                stSurfaceView.updateDraw();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);


    }

    public void initMaps(){
        List<Obstacle> simpleMap = new ArrayList<Obstacle>();
        simpleMap.add(new Obstacle(500.00, 800.00, 500.00, 800.00));
        maps = new ArrayList<List<Obstacle>>();
        maps.add(simpleMap);
    }


    public void onRightButton(View view){
        // what to put here
    }

    public void onLeftButton(View view){
        // what to put here
    }
}
