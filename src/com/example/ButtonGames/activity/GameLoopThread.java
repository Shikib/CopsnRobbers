package com.example.ButtonGames.activity;


import android.app.Activity;
import android.graphics.Canvas;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

public class GameLoopThread extends Thread {
    static final long FPS = 10; // Frames per second
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
            if (board.getPlayerL().getScore() >= score || board.getPlayerR().getScore() >= score)
                ((Activity) view.getContext()).finish();

            board.updateBoard(); // Tick the board

            try {
                c = view.getHolder().lockCanvas(); // get the canvas from view, let us make changes to it
                synchronized (view.getHolder()) {
                    view.onDraw(c); // Draw the board on the canvas gotten from view
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c); // Save and post canvas
                }
            }
            // Calculate how much extra time there is before the next tick
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
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