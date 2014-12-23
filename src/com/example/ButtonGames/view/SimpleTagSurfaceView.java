package com.example.ButtonGames.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.ButtonGames.activity.GameLoopThread;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Sprite;

import java.util.jar.Attributes;

public class SimpleTagSurfaceView extends SurfaceView{

    private SurfaceHolder sh;
    private Board board;
    private final Paint left = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint right = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GameLoopThread gameLoopThread;


    public SimpleTagSurfaceView(Context context, Board board) {
        super(context);
        gameLoopThread = new GameLoopThread(this, board);
        sh = getHolder();
        this.board = board;
        left.setColor(Color.RED);
        left.setStyle(Paint.Style.FILL);
        right.setColor(Color.BLUE);
        right.setStyle(Paint.Style.FILL);


        sh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                // width - 800, height - 370 : On my nexus one emulator
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }

            }
        });
    }



    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawRect((float) board.getPlayerL().getX(), (float) board.getPlayerL().getY(),
                (float) board.getPlayerL().getX() + (float) Sprite.radius, (float) board.getPlayerL().getY() + (float) Sprite.radius, left);
        canvas.drawCircle((float) board.getPlayerR().getX(), (float) board.getPlayerR().getY(), (float) Sprite.radius, right);
    }

}
