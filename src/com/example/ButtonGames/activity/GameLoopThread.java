package com.example.ButtonGames.activity;


import android.graphics.Canvas;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.view.SimpleTagSurfaceView;

public class GameLoopThread extends Thread {
    static final long FPS = 10;
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
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();

            board.updateBoard();

            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (Exception ex) {} // i believe this is unnecessary
        }
    }
}