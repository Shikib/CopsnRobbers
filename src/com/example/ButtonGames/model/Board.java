package com.example.ButtonGames.model;

import android.content.Intent;
import com.example.ButtonGames.activity.MyActivity;
import com.example.ButtonGames.activity.SimpleTagActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Sarah on 2014-12-20.
 */
public class Board {

    private Sprite playerL;
    private Sprite playerR;
    private int height;
    private int width;
    private List<Obstacle> obstacles;
    private boolean hunterState; // true = left is hunter, false = right is hunter
    private boolean winMethod; // true = win by collision, hunter wins, false = win my time runs out, hunted wins
    private boolean timeToSwitch;// true = time to switch
    private int spriteRadius;

    public static final int winningScore = 5;

    private int currentFrame = 0; // What frame the game is on right now
    private int switchRoleTime = 300; // Number of frames before sprites switch roles




    public Board(List<Obstacle> obstacles, int width, int height){
        this.obstacles = obstacles;
        this.width = width;
        this.height = height;
        this.spriteRadius = height / 24; // Based on resize of sprite in surface view
        initSprites(0,0,(int) (Math.random()* 2)); // Makes sprites with random sprite as hunter/hunted
        winMethod = false; // (do we need this? idk scared of null pointers)
        timeToSwitch = false;
    }

    public void initSprites(int scoreL, int scoreR, int rand) {

        playerL = new Sprite(this, false, scoreL, 0 + 2*spriteRadius, height/2, 0); // Set left sprite on left side of board
        playerR = new Sprite(this, false, scoreR, width - 2*spriteRadius, height/2, 180); // Set right sprite on right side of board

        // If 0 set left sprite as hunter
        if (rand == 0) {
            playerL.setState(true);
            hunterState = true;
        }
        else if (rand == 1){ // If 1 set right sprite as hunter
            playerR.setState(true);
            hunterState = false;
        }
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getCurrentFrame(){
        return currentFrame;
    }

    public Sprite getPlayerL() {
        return playerL;
    }

    public Sprite getPlayerR() {
        return playerR;
    }

    public List<Obstacle> getObstacles(){
        return obstacles;
    }

    public int getSwitchRoleTime(){
        return switchRoleTime;
    }

    public boolean getWinMethod(){
        return winMethod;
    }



    // Produce true if can move to that x, y coordinate - need to be fixed
    public boolean canMove(double x, double y){
        // Check if hit borders
        if (x < 0 || x > width || y < 0 || y > 5*height / 6)
            return false;
        // Check of hit obstacles
        for (Obstacle o: obstacles){
            if ((o.getXRange().contains(x + spriteRadius) || o.getXRange().contains(x - spriteRadius)) &&
                    (o.getYRange().contains(y + spriteRadius) || o.getYRange().contains(y - spriteRadius)))
                return false;
        }
        return true;
    }

    // If there is a collision, update score, and reset sprites
    public boolean checkCollision(){
        boolean hasCollision = Math.abs(playerL.getX() - playerR.getX()) <= 2*spriteRadius && Math.abs(playerL.getY() - playerR.getY()) <= 2*spriteRadius;

        if (hasCollision) {
            if (currentFrame > 0) {
                winMethod = true;
                currentFrame = -50;
            }
            if (currentFrame >= -40) {
                resetSprites(0); // Condition 0 means hunter updates score
            }
        }

        return hasCollision;
    }

    // Reset sprites to opposite ends of board, update score based on condition, switch roles
    // Condition is 0 if tag (hunter get point), 1 if time ran out (hunted get point)
    public void resetSprites(int condition) {
        Sprite hunter;
        Sprite hunted;

        if (hunterState) { // True means left is hunter, false means right is hunter
            hunter = playerL;
            hunted = playerR;
        }
        else {
            hunter = playerR;
            hunted = playerL;
        }

        if (condition == 0)
            hunter.updateScore();
        else if (condition == 1)
            hunted.updateScore();

        // Switch roles
        // If hunterState is true (left is hunter), make right hunter (1 = right is hunter, 0 = left is hunter)
        // Consider putting a delay here
        initSprites(playerL.getScore(), playerR.getScore(), hunterState ?  1 : 0);


    }

    // Check to see if time has run out
    public void checkSwitchRoles(){
        // If the current frame is not 0, and is a multiple of switchRoleTime
        if (timeToSwitch){
            if (currentFrame > 0) {
                winMethod = false;
                currentFrame = -50;
            }
            if (currentFrame >= -40) {
                resetSprites(1);  // (Condition 1 = time has run out, hunted gets point)
                timeToSwitch = false;
            }
        }

    }

    public void updateBoard(){
        if (currentFrame != 0 && (currentFrame % switchRoleTime) == 0){
            timeToSwitch = true;
        }
        playerL.action(); // Move left sprite
        playerR.action(); // Move right sprite
        checkCollision();
        checkSwitchRoles();
        currentFrame++;
    }


}
