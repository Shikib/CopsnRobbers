package com.example.ButtonGames.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Sprite;

public class SimpleTagSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sh;
    private Board board;
    private final Paint left = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint right = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Canvas canvas = new Canvas();

    public SimpleTagSurfaceView(Context context, Board board) {
        super(context);
        sh = getHolder();
        this.board = board;
        sh.addCallback(this);
        left.setColor(Color.RED);
        left.setStyle(Paint.Style.FILL);
        right.setColor(Color.BLUE);
        right.setStyle(Paint.Style.FILL);
    }

    public void updateDraw(){
        this.draw(canvas);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle((float) board.getPlayerL().getX(), (float) board.getPlayerL().getY(), (float) Sprite.radius, left);
        canvas.drawCircle((float) board.getPlayerR().getX(), (float) board.getPlayerR().getY(), (float) Sprite.radius, right);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = sh.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle((float) board.getPlayerL().getX(), (float) board.getPlayerL().getY(), (float) Sprite.radius, left);
        canvas.drawCircle((float) board.getPlayerR().getX(), (float) board.getPlayerR().getY(), (float) Sprite.radius, right);
        sh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // not sure what to put in here?
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // not sure what to put in here?
    }
}
