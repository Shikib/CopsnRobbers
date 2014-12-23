package com.example.ButtonGames.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.ButtonGames.R;
import com.example.ButtonGames.activity.GameLoopThread;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.model.Sprite;

import java.util.List;
import java.util.jar.Attributes;

public class SimpleTagSurfaceView extends SurfaceView{

    private SurfaceHolder sh;
    private Board board;
    private final Paint left = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint right = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint obstacle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GameLoopThread gameLoopThread;
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.map1);

    private Bitmap spriteGreen1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_1);
    private Bitmap spriteGreen2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_2);
    private Bitmap spriteGreen3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_3);




    public SimpleTagSurfaceView(Context context, Board board) {
        super(context);
        gameLoopThread = new GameLoopThread(this, board);
        sh = getHolder();
        this.board = board;
        left.setColor(Color.RED);
        left.setStyle(Paint.Style.FILL);
        right.setColor(Color.BLUE);
        right.setStyle(Paint.Style.FILL);
        obstacle.setColor(Color.GRAY);
        obstacle.setStyle(Paint.Style.FILL);


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

        Rect rect = new Rect(0, 0, board.getWidth(), board.getHeight());
        canvas.drawBitmap(background, null, rect, null);

        canvas.save();
        canvas.translate((float) board.getPlayerL().getX(), (float) board.getPlayerL().getY());
        canvas.rotate((float) board.getPlayerL().getDirection());
        drawCorrectSprite(canvas, board.getPlayerL());
        canvas.restore();

        canvas.save();
        canvas.translate((float) board.getPlayerR().getX(), (float) board.getPlayerR().getY());
        canvas.rotate((float) board.getPlayerR().getDirection());
        drawCorrectSprite(canvas, board.getPlayerR());
        canvas.restore();

        List<Obstacle> obstacles = board.getObstacles();
        for (Obstacle o : obstacles){
            canvas.drawRect((float) o.getXRange().getUpper().doubleValue(), (float) o.getYRange().getUpper().doubleValue(),
                    (float) o.getXRange().getLower().doubleValue(), (float) o.getYRange().getLower().doubleValue(),obstacle);
        }

    }

    public void drawCorrectSprite(Canvas canvas, Sprite sprite){
        // true is hunter
        if (sprite.getState()){
            if (sprite.getSpinning()){
                canvas.drawRect(0f - (float) Sprite.radius, 0f - (float) Sprite.radius, (float) Sprite.radius, (float) Sprite.radius, left);
            } else {
                canvas.drawRect(0f - (float) Sprite.radius, 0f - (float) Sprite.radius, (float) Sprite.radius, (float) Sprite.radius, left);
            }

        } else {
            if (sprite.getSpinning()){
                canvas.drawBitmap(spriteGreen1, 0f - (float) Sprite.radius, 0f - (float) Sprite.radius, null);
            } else {
                int currentFrame = board.getCurrentFrame() % 4;
                if (currentFrame == 0){
                    canvas.drawBitmap(spriteGreen1, 0f - (float) Sprite.radius, 0f - (float) Sprite.radius, null);
                } else if (currentFrame == 1){
                    canvas.drawBitmap(spriteGreen2, 0f - (float) Sprite.radius, 0f - (float) Sprite.radius, null);
                } else if (currentFrame == 2){
                    canvas.drawBitmap(spriteGreen1, 0f - (float) Sprite.radius, 0f - (float) Sprite.radius, null);
                } else if (currentFrame == 3){
                    canvas.drawBitmap(spriteGreen3, 0f - (float) Sprite.radius, 0f - (float) Sprite.radius, null);
                }
            }
        }
    }



}
