package com.example.ButtonGames.view;

import android.content.Context;
import android.graphics.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.ButtonGames.R;
import com.example.ButtonGames.activity.GameLoopThread;
import com.example.ButtonGames.model.Board;
import com.example.ButtonGames.model.Obstacle;
import com.example.ButtonGames.model.Sprite;

import java.util.List;

public class SimpleTagSurfaceView extends SurfaceView{

    private SurfaceHolder sh;
    private Board board;
    public GameLoopThread gameLoopThread;
    private int spriteTheme; // Theme of sprite

    private final Paint textL = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for Lscore
    private final Paint textR = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for Rscore
    private final Paint textT = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for timer
    private final Paint textC = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for countdown
    private final Paint textM = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style/size/alignment of text for message
    private final Paint obstacle = new Paint(Paint.ANTI_ALIAS_FLAG); // Color/style for obstacle

    // Bitmap of background
    private Bitmap background;

    // Bitmap of inmate sprites
    private Bitmap inmate0 = BitmapFactory.decodeResource(getResources(), R.drawable.inmate0);
    private Bitmap inmate1 = BitmapFactory.decodeResource(getResources(), R.drawable.inmate1);
    private Bitmap inmate2 = BitmapFactory.decodeResource(getResources(), R.drawable.inmate2);

    // Bitmap of police sprites
    private Bitmap police0 = BitmapFactory.decodeResource(getResources(), R.drawable.police0);
    private Bitmap police1 = BitmapFactory.decodeResource(getResources(), R.drawable.police1);
    private Bitmap police2 = BitmapFactory.decodeResource(getResources(), R.drawable.police2);

    private Bitmap deadSprite = BitmapFactory.decodeResource(getResources(), R.drawable.dead_sprite);


    public SimpleTagSurfaceView(Context context, final Board board, Bitmap background, int spriteTheme) {
        super(context);

        //Set up color/style of score text timer and obstacles
        textL.setColor(Color.RED);
        textL.setStyle(Paint.Style.FILL);
        textL.setTextSize(board.getHeight() / 6);
        textL.setTextAlign(Paint.Align.LEFT);
        textL.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "arial_black.ttf"));

        textR.setColor(Color.BLUE);
        textR.setStyle(Paint.Style.FILL);
        textR.setTextSize(board.getHeight() / 6);
        textR.setTextAlign(Paint.Align.RIGHT);
        textR.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "arial_black.ttf"));

        textC.setColor(Color.RED);
        textC.setStyle(Paint.Style.FILL);
        textC.setTextSize(board.getHeight() / 6);
        textC.setTextAlign(Paint.Align.CENTER);
        textC.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "abadi_condensed_xtrabold.ttf"));

        textT.setColor(Color.WHITE);
        textT.setStyle(Paint.Style.FILL);
        textT.setTextSize(board.getHeight() / 6);
        textT.setTextAlign(Paint.Align.CENTER);
        textT.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "DS-DIGIB.TTF"));

        textM.setColor(Color.RED);
        textM.setStyle(Paint.Style.FILL);
        textM.setTextSize(board.getHeight() / 6);
        textM.setTextAlign(Paint.Align.CENTER);
        textM.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "abadi_condensed_xtrabold.ttf"));

        obstacle.setColor(Color.GRAY);
        obstacle.setStyle(Paint.Style.FILL);

        this.background = background;
        this.spriteTheme = spriteTheme;

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
        Bitmap leftBitmap = Bitmap.createScaledBitmap(getCorrectSprite(true),
                getCorrectSprite(true).getWidth() * (board.getHeight() / 9) / getCorrectSprite(true).getHeight(), board.getHeight() / 9, false);

        // Rotate and translate left sprite
        leftMatrix.setRotate((float) board.getPlayerL().getDirection(), (float) leftBitmap.getWidth() / 2, (float) leftBitmap.getHeight() / 2);
        leftMatrix.postTranslate((float) board.getPlayerL().getX() - ((float) leftBitmap.getWidth() / 2),
                (float) board.getPlayerL().getY() - ((float) leftBitmap.getHeight() / 2));


        // Make a matrix, get the correct right sprite for the frame
        Matrix rightMatrix = new Matrix();
        // Resize the sprite -- MUST CHANGE RADIUS AND SPEED
        Bitmap rightBitmap = Bitmap.createScaledBitmap(getCorrectSprite(false),
                getCorrectSprite(false).getWidth() * (board.getHeight() / 9) / getCorrectSprite(false).getHeight(), board.getHeight() / 9, false);

        // Rotate and translate right sprite
        rightMatrix.setRotate((float) board.getPlayerR().getDirection(), (float) rightBitmap.getWidth() / 2, (float) rightBitmap.getHeight() / 2);
        rightMatrix.postTranslate((float) board.getPlayerR().getX() - ((float) rightBitmap.getWidth() / 2),
                (float) board.getPlayerR().getY() - ((float) rightBitmap.getHeight() / 2));


        // Draw score
        canvas.drawText(Integer.toString(board.getPlayerL().getScore()), 6*board.getWidth()/24, (3*34+2) * board.getHeight() / (3*36), textL);
        canvas.drawText(Integer.toString(board.getPlayerR().getScore()), 18*board.getWidth()/24, (3*34+2) * board.getHeight() / (3*36), textR);

        // Draw obstacles
        List<Obstacle> obstacles = board.getObstacles();
        for (Obstacle o : obstacles){
            canvas.drawRect((float) o.getXLower(), (float) o.getYLower(),
                    (float) o.getXUpper(), (float) o.getYUpper(),obstacle);
        }

        // Draw the countdown after resetting roles
        if (board.getCurrentFrame() <= -40){
            if ((-55 <= board.getCurrentFrame() && board.getCurrentFrame()<= -51) ||
                    (-47 <= board.getCurrentFrame() && board.getCurrentFrame() <= -43)) {

                // Draw the winning sprite on top
                if ((board.getWinMethod() && board.getHunterState()) ||
                        (!board.getWinMethod() && !board.getHunterState())){
                    // Draw right sprite
                    canvas.drawBitmap(rightBitmap, rightMatrix, null);
                    // Draw left sprite
                    canvas.drawBitmap(leftBitmap, leftMatrix, null);

                } else {
                    // Draw left sprite
                    canvas.drawBitmap(leftBitmap, leftMatrix, null);
                    // Draw right sprite
                    canvas.drawBitmap(rightBitmap, rightMatrix, null);
                }

                if (board.getWinMethod()) {
                    canvas.drawText("CAUGHT!", board.getWidth() / 2, board.getHeight() / 2, textM);
                } else {
                    canvas.drawText("ESCAPED!", board.getWidth() / 2, board.getHeight() / 2, textM);
                }

            }
        } else {
            // Draw left sprite
            canvas.drawBitmap(leftBitmap, leftMatrix, null);
            // Draw right sprite
            canvas.drawBitmap(rightBitmap, rightMatrix, null);

            if (board.getCurrentFrame() < 0) {
                int loadingTime = board.getCurrentFrame() / 10;
                if (loadingTime == -2)
                    canvas.drawText("READY", board.getWidth() / 2, board.getHeight() / 2, textC);
                else if (loadingTime == -1)
                    canvas.drawText("SET", board.getWidth() / 2, board.getHeight() / 2, textC);
                else if (loadingTime == 0)
                    canvas.drawText("GO!", board.getWidth() / 2, board.getHeight() / 2, textC);
            } else {
                // Calculate time on timer based on currentFrame
                int timeOnTimer = (board.getSwitchRoleTime() / 10) - ((board.getCurrentFrame() % board.getSwitchRoleTime()) / 10); // <-- Frames per second
                //Draws the timer
                canvas.drawText(Integer.toString(timeOnTimer), board.getWidth() / 2, (3*34+2) * board.getHeight() / (3*36), textT);
            }
        }
    }

    // true = left, false = right
    public Bitmap getCorrectSprite(boolean side) {
        Sprite sprite;
        if (side)
            sprite = board.getPlayerL();
        else
            sprite = board.getPlayerR();

        Bitmap hSprite1 = police0;
        Bitmap hSprite2 = police1;
        Bitmap hSprite3 = police2;
        Bitmap sprite1 = inmate0;
        Bitmap sprite2 = inmate1;
        Bitmap sprite3 = inmate2;
        Bitmap deadSprite = this.deadSprite;

        if (board.getCurrentFrame() <= -40) {
            // Hunter and win by collision
            if (sprite.getState() && board.getWinMethod()){
                return hSprite1;
                // Hunted and win by collision
            } else if (!sprite.getState() && board.getWinMethod()){
                return deadSprite;
                // Hunter and win by times up
            } else if (sprite.getState()){
                return deadSprite;
            } else {
                return sprite1;
            }
        } else if (sprite.getState()) { // getState is true if hunter
            if (sprite.getSpinning()) {
                return hSprite1; // If spinning keep the same left sprite every frame
            } else {
                int currentFrame = board.getCurrentFrame() % 4; // Alternate between left sprites each from

                if (currentFrame < 0)
                    currentFrame += 4;

                if (currentFrame == 0) {
                    return hSprite1;
                } else if (currentFrame == 1) {
                    return hSprite2;
                } else if (currentFrame == 2) {
                    return hSprite1;
                } else if (currentFrame == 3) {
                    return hSprite3;
                }
            }

        } else { // is hunted
            if (sprite.getSpinning()) {
                return sprite1;
            } else {
                if (board.getCurrentFrame() <= -40) {
                    return deadSprite;
                } else {
                    int currentFrame = board.getCurrentFrame() % 4;
                    if (currentFrame < 0)
                        currentFrame += 4;
                    if (currentFrame == 0) {
                        return sprite1;
                    } else if (currentFrame == 1) {
                        return sprite2;
                    } else if (currentFrame == 2) {
                        return sprite1;
                    } else if (currentFrame == 3) {
                        return sprite3;
                    }
                }
            }
        }
        return sprite1; // return generic if in doubt
    }


}
