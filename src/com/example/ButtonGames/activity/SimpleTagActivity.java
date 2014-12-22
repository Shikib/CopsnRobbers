package com.example.ButtonGames.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.example.ButtonGames.R;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class SimpleTagActivity extends Activity{

    private Board board;
    private List<List<Obstacle>> maps;
    private Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMaps();
        board = new Board(maps.get(0));
        timer = new Timer();
        timer.scheduleAtFixedRate(board.getBoardTimerTask(), 1000, 100);

        setContentView(R.layout.game);
    }

    public void initMaps(){
        List<Obstacle> simpleMap = new ArrayList<Obstacle>();
        simpleMap.add(new Obstacle(800.00, 500.00, 800.00, 500.00));
        maps.add(simpleMap);
    }


    public void onRightButton(View view){
        // what to put here
    }

    public void onLeftButton(View view){
        // what to put here
    }
}
