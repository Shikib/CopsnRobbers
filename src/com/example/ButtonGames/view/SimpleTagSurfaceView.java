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
    private final Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint obstacle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GameLoopThread gameLoopThread;
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.map1);

    private Bitmap spriteGreen1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_1);
    private Bitmap spriteGreen2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_2);
    private Bitmap spriteGreen3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_3);

    private Bitmap HspriteGreen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_1);
    private Bitmap HspriteGreen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_2);
    private Bitmap HspriteGreen3 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_3);

    private Bitmap spritePurple1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_1);
    private Bitmap spritePurple2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_2);
    private Bitmap spritePurple3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_3);

    private Bitmap HspritePurple1 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_1);
    private Bitmap HspritePurple2 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_2);
    private Bitmap HspritePurple3 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_3);




    public SimpleTagSurfaceView(Context context, Board board) {
        super(context);
        gameLoopThread = new GameLoopThread(this, board);
        sh = getHolder();
        this.board = board;
        text.setColor(Color.WHITE);
        text.setStyle(Paint.Style.FILL);
        text.setTextSize(board.getHeight()/2);
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

        // draw left sprite
        Matrix leftMatrix = new Matrix();
        Bitmap leftBitmap = getCorrectSpriteL();

        leftMatrix.setRotate((float) board.getPlayerL().getDirection(), (float) leftBitmap.getWidth()/2, (float) leftBitmap.getHeight()/2);
        leftMatrix.postTranslate((float) board.getPlayerL().getX(), (float) board.getPlayerL().getY());

        canvas.drawBitmap(leftBitmap, leftMatrix, null);

        // draw right sprite
        Matrix rightMatrix = new Matrix();
        Bitmap rightBitmap = getCorrectSpriteR();

        rightMatrix.setRotate((float) board.getPlayerR().getDirection(), (float) rightBitmap.getWidth()/2, (float) rightBitmap.getHeight()/2);
        // bandaid solution here: should set X position of right sprite to correct value
        rightMatrix.postTranslate((float) board.getPlayerR().getX() - 200, (float) board.getPlayerR().getY());

        canvas.drawBitmap(rightBitmap, rightMatrix, null);

        // draw score
        canvas.drawText(Integer.toString(board.getPlayerL().getScore()), 2*board.getWidth()/8, 3*board.getHeight()/4, text);
        canvas.drawText(Integer.toString(board.getPlayerR().getScore()), 5*board.getWidth()/8, 3*board.getHeight()/4, text);

        // draw obstacles
        List<Obstacle> obstacles = board.getObstacles();
        for (Obstacle o : obstacles){
            canvas.drawRect((float) o.getXRange().getUpper().doubleValue(), (float) o.getYRange().getUpper().doubleValue(),
                    (float) o.getXRange().getLower().doubleValue(), (float) o.getYRange().getLower().doubleValue(),obstacle);
        }
    }

    public Bitmap getCorrectSpriteL(){
        Sprite sprite = board.getPlayerL();

        if (sprite.getState()){
            if (sprite.getSpinning()){
                return HspriteGreen1;
            } else {
                int currentFrame = board.getCurrentFrame() % 4;
                if (currentFrame == 0){
                    return HspriteGreen1;
                } else if (currentFrame == 1){
                    return HspriteGreen2;
                } else if (currentFrame == 2){
                    return HspriteGreen1;
                } else if (currentFrame == 3){
                    return HspriteGreen3;
                }
            }

        } else {
            if (sprite.getSpinning()){
                return spriteGreen1;
            } else {
                int currentFrame = board.getCurrentFrame() % 4;
                if (currentFrame == 0){
                    return spriteGreen1;
                } else if (currentFrame == 1){
                    return spriteGreen2;
                } else if (currentFrame == 2){
                    return spriteGreen1;
                } else { // currentFrame == 3
                    return spriteGreen3;
                }
            }
        }
        return null; // will never get here
    }

    public Bitmap getCorrectSpriteR(){
        Sprite sprite = board.getPlayerR();

        if (sprite.getState()){
            if (sprite.getSpinning()){
                return HspritePurple1;
            } else {
                int currentFrame = board.getCurrentFrame() % 4;
                if (currentFrame == 0){
                    return HspritePurple1;
                } else if (currentFrame == 1){
                    return HspritePurple2;
                } else if (currentFrame == 2){
                    return HspritePurple1;
                } else if (currentFrame == 3){
                    return HspritePurple3;
                }
            }
        } else {
            if (sprite.getSpinning()){
                return spritePurple1;
            } else {
                int currentFrame = board.getCurrentFrame() % 4;
                if (currentFrame == 0){
                    return spritePurple1;
                } else if (currentFrame == 1){
                    return spritePurple2;
                } else if (currentFrame == 2){
                    return spritePurple1;
                } else { // currentFrame == 3
                    return spritePurple3;
                }
            }
        }
        return null; // will never get here
    }
}
