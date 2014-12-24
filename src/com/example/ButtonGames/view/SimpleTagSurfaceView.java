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
    private GameLoopThread gameLoopThread;
    private final Paint textL = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for Lscore
    private final Paint textR = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for Rscore
    private final Paint textT = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for timer
    private final Paint textM = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for message
    private final Paint obstacle = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style for obstacle

    // Bitmap of background
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.map1);

    // Bitmap of green hunted sprite
    private Bitmap spriteGreen1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_1);
    private Bitmap spriteGreen2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_2);
    private Bitmap spriteGreen3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green_3);

    // Bitmap of green hunter sprite
    private Bitmap HspriteGreen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_1);
    private Bitmap HspriteGreen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_2);
    private Bitmap HspriteGreen3 = BitmapFactory.decodeResource(getResources(), R.drawable.hunter_sprite_green_3);

    // Bitmap of purple hunted sprite
    private Bitmap spritePurple1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_1);
    private Bitmap spritePurple2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_2);
    private Bitmap spritePurple3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_purple_3);

    // Bitmap of purple hunter sprite
    private Bitmap HspritePurple1 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_1);
    private Bitmap HspritePurple2 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_2);
    private Bitmap HspritePurple3 = BitmapFactory.decodeResource(getResources(), R.drawable.hunger_sprite_purple_3);


    public SimpleTagSurfaceView(Context context, final Board board) {
        super(context);

        //Set up color/style of score text timer and obstacles
        textL.setColor(Color.GREEN);
        textL.setStyle(Paint.Style.FILL);
        textL.setTextSize(board.getHeight() / 6);
        textL.setTextAlign(Paint.Align.LEFT);

        textR.setColor(Color.MAGENTA);
        textR.setStyle(Paint.Style.FILL);
        textR.setTextSize(board.getHeight() / 6);
        textR.setTextAlign(Paint.Align.RIGHT);

        textT.setColor(Color.BLACK);
        textT.setStyle(Paint.Style.FILL);
        textT.setTextSize(board.getHeight() / 8);
        textT.setTextAlign(Paint.Align.CENTER);

        textM.setColor(Color.RED);
        textM.setStyle(Paint.Style.FILL);
        textM.setTextSize(board.getHeight() / 8);
        textM.setTextAlign(Paint.Align.CENTER);

        obstacle.setColor(Color.GRAY);
        obstacle.setStyle(Paint.Style.FILL);

        // Set up stuff
        sh = getHolder();
        this.board = board;
        final SimpleTagSurfaceView view = this;

        sh.addCallback(new SurfaceHolder.Callback() {

            // Called when surface is created
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoopThread = new GameLoopThread(view, board);
                gameLoopThread.setRunning(true); // Start the thread
                try {
                    gameLoopThread.start();
                } catch (IllegalThreadStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            // Called when surface is destroyed. Stop the thread. Or something.
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }



    @Override
    public void onDraw(Canvas canvas) {

        Rect rect = new Rect(0, 0, board.getWidth(), board.getHeight()); // Rectangle that is size of board
        // Draw the background
        canvas.drawBitmap(background, null, rect, null);

        // Make a matrix, get the correct left sprite for the frame
        Matrix leftMatrix = new Matrix();
        // Resize the sprite -- MUST CHANGE RADIUS AND SPEED
        Bitmap leftBitmap = Bitmap.createScaledBitmap(getCorrectSpriteL(),
                getCorrectSpriteL().getWidth() * (board.getHeight() / 12) / getCorrectSpriteL().getHeight(), board.getHeight() / 12, false);

        // Rotate and translate left sprite
        leftMatrix.setRotate((float) board.getPlayerL().getDirection(), (float) leftBitmap.getWidth() / 2, (float) leftBitmap.getHeight() / 2);
        leftMatrix.postTranslate((float) board.getPlayerL().getX() - ((float) leftBitmap.getWidth() / 2),
                (float) board.getPlayerL().getY() - ((float) leftBitmap.getHeight() / 2));


        // Make a matrix, get the correct right sprite for the frame
        Matrix rightMatrix = new Matrix();
        // Resize the sprite -- MUST CHANGE RADIUS AND SPEED
        Bitmap rightBitmap = Bitmap.createScaledBitmap(getCorrectSpriteR(),
                getCorrectSpriteR().getWidth() * (board.getHeight() / 12) / getCorrectSpriteR().getHeight(), board.getHeight() / 12, false);

        // Rotate and translate right sprite
        rightMatrix.setRotate((float) board.getPlayerR().getDirection(), (float) rightBitmap.getWidth() / 2, (float) rightBitmap.getHeight() / 2);
        rightMatrix.postTranslate((float) board.getPlayerR().getX() - ((float) rightBitmap.getWidth() / 2),
                (float) board.getPlayerR().getY() - ((float) rightBitmap.getHeight() / 2));


        // Draw score
        canvas.drawText(Integer.toString(board.getPlayerL().getScore()), 1*board.getWidth()/6, 35*board.getHeight() / 36, textL);
        canvas.drawText(Integer.toString(board.getPlayerR().getScore()), 5*board.getWidth()/6, 35*board.getHeight() / 36, textR);

        // Draw obstacles
        List<Obstacle> obstacles = board.getObstacles();
        for (Obstacle o : obstacles){
            canvas.drawRect((float) o.getXRange().getLower().doubleValue(), (float) o.getYRange().getLower().doubleValue(),
                    (float) o.getXRange().getUpper().doubleValue(), (float) o.getYRange().getUpper().doubleValue(),obstacle);
        }

        // Draw the countdown after resetting roles
        if (board.getCurrentFrame() <= -40){
            if ((-50 <= board.getCurrentFrame() && board.getCurrentFrame()<= -48) ||
                    (-45 <= board.getCurrentFrame() && board.getCurrentFrame() <= -43)) {
                // Draw left sprite
                canvas.drawBitmap(leftBitmap, leftMatrix, null);
                // Draw right sprite
                canvas.drawBitmap(rightBitmap, rightMatrix, null);

                if (board.getWinMethod()) {
                    canvas.drawText("Hunter Wins!", board.getWidth() / 2, board.getHeight() / 2, textM);
                } else {
                    canvas.drawText("Prey got away!", board.getWidth() / 2, board.getHeight() / 2, textM);
                }

            }
        } else {
            // Draw left sprite
            canvas.drawBitmap(leftBitmap, leftMatrix, null);
            // Draw right sprite
            canvas.drawBitmap(rightBitmap, rightMatrix, null);

             if (board.getCurrentFrame() < 0) {
                 int loadingTime = board.getCurrentFrame() / 10;
                 if (loadingTime != 0)
                     canvas.drawText(Integer.toString(-1 * loadingTime), board.getWidth() / 2, board.getHeight() / 2, textT);
                 else
                     canvas.drawText("BEGIN!", board.getWidth() / 2, board.getHeight() / 2, textT);
             } else {
                 // Calculate time on timer based on currentFrame
                 int timeOnTimer = (board.getSwitchRoleTime() / 10) - ((board.getCurrentFrame() % board.getSwitchRoleTime()) / 10); // <-- Frames per second
                 //Draws the timer
                 canvas.drawText(Integer.toString(timeOnTimer), board.getWidth() / 2, 35 * board.getHeight() / 36, textT);
             }
        }
    }

    public Bitmap getCorrectSpriteL(){
        Sprite sprite = board.getPlayerL();

        // getState is true if hunter
        if (sprite.getState()){
            if (sprite.getSpinning()){
                return HspriteGreen1; // If spinning keep the same left sprite every frame
            } else {
                if (board.getCurrentFrame() <= -40) { // freeze frame -- Can change to something else
                    return HspriteGreen2;
                } else {
                    int currentFrame = board.getCurrentFrame() % 4; // Alternate between left sprites each from
                    if (currentFrame == 0) {
                        return HspriteGreen1;
                    } else if (currentFrame == 1) {
                        return HspriteGreen2;
                    } else if (currentFrame == 2) {
                        return HspriteGreen1;
                    } else if (currentFrame == 3) {
                        return HspriteGreen3;
                    }
                }
            }

        } else { // is hunted
            if (sprite.getSpinning()){
                return spriteGreen1;
            } else {
                if (board.getCurrentFrame() <= -40) {
                    return spriteGreen1;
                } else {
                    int currentFrame = board.getCurrentFrame() % 4;
                    if (currentFrame == 0) {
                        return spriteGreen1;
                    } else if (currentFrame == 1) {
                        return spriteGreen2;
                    } else if (currentFrame == 2) {
                        return spriteGreen1;
                    } else { // currentFrame == 3
                        return spriteGreen3;
                    }
                }
            }
        }
        return spriteGreen1; // return generic if in doubt
    }

    public Bitmap getCorrectSpriteR(){
        Sprite sprite = board.getPlayerR();

        if (sprite.getState()){
            if (sprite.getSpinning()){
                return HspritePurple1;
            } else {
                if (board.getCurrentFrame() <= -40){
                    return HspritePurple1;
                } else {
                    int currentFrame = board.getCurrentFrame() % 4;
                    if (currentFrame == 0) {
                        return HspritePurple1;
                    } else if (currentFrame == 1) {
                        return HspritePurple2;
                    } else if (currentFrame == 2) {
                        return HspritePurple1;
                    } else if (currentFrame == 3) {
                        return HspritePurple3;
                    }
                }
            }
        } else {
            if (sprite.getSpinning()){
                return spritePurple1;
            } else {
                if (board.getCurrentFrame() <= -40) {
                    return spritePurple1;
                } else {
                    int currentFrame = board.getCurrentFrame() % 4;
                    if (currentFrame == 0) {
                        return spritePurple1;
                    } else if (currentFrame == 1) {
                        return spritePurple2;
                    } else if (currentFrame == 2) {
                        return spritePurple1;
                    } else { // currentFrame == 3
                        return spritePurple3;
                    }
                }

            }
        }
        return spritePurple1; // if in doubt return generic sprite
    }
}
