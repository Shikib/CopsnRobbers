package com.JassGames.CopsnRobbers.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.View;
import com.JassGames.CopsnRobbers.model.Board;

import java.lang.Boolean;import java.lang.InterruptedException;import java.lang.Override;import java.lang.System;import java.lang.Thread;

public class GameLoopThread extends Thread {
    static final long FPS = 10; // Frames per second --> IF CHANGE THIS MUST CHANGE DRAW TIMER
    private SimpleTagSurfaceView view;
    private Board board;
    private boolean running = false;

    public GameLoopThread(SimpleTagSurfaceView view, Board board) {
        this.view = view;
        this.board = board;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS; // Time per tick, 1000 = milli seconds
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis(); // Record when the loop starts
            int score = Board.winningScore; // get the static winning score

        // check whether game is over
        if (board.getPlayerL().getScore() >= score || board.getPlayerR().getScore() >= score) {
            Boolean winner = false;
            if (board.getPlayerL().getScore() >= score) {
                winner = true;
            }
            Intent gameOverScreen = new Intent((view.getContext()), GameOverActivity.class);
            gameOverScreen.putExtra("com.example.ButtonGames.winner", winner);
            gameOverScreen.putExtra("com.example.ButtonGames.cop", board.getHunterState());
            gameOverScreen.putExtra("com.example.ButtonGames.oldObstacle", SimpleTagActivity.obstacleMap);
            gameOverScreen.putExtra("com.example.ButtonGames.oldTheme", SimpleTagActivity.backgroundMap);

            SimpleTagActivity activity = (SimpleTagActivity) view.getContext();
            activity.finish();
            view.getContext().startActivity(gameOverScreen);

            activity.pauseView.setVisibility(View.GONE);
            return;
        }
            if (((SimpleTagActivity) view.getContext()).pauseView.getVisibility() == View.GONE)
                board.updateBoard(); // Tick the board

            try {
                c = view.getHolder().lockCanvas(); // get the canvas from view, let us make changes to it
                synchronized (view.getHolder()) {
                    view.onDraw(c); // Draw the board on the canvas gotten from view
                }
            } catch (java.lang.NullPointerException ex) {
                ex.printStackTrace();
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c); // Save and post canvas
                }
            }
            // Calculate how much extra time there is before the next tick
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime); // If there is extra time before the next tick, sleep that extra time
                else
                    sleep(10); // System is running behind, but sleep anyways to avoid overloading CPU
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }
}