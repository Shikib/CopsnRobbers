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
    private double radius = Sprite.radius; // Radius of sprite
    private boolean hunterState; // true = left is hunter, false = right is hunter

    public static final int winningScore = 5;

    private int currentFrame = 0; // What frame the game is on right now
    private int switchRoleTime = 3000; // Number of frames before sprites switch roles




    public Board(List<Obstacle> obstacles, int width, int height){
        this.obstacles = obstacles;
        this.width = width;
        this.height = height;
        initSprites(0,0,(int) (Math.random()* 2)); // Makes sprites with random sprite as hunter/hunted
    }

    public void initSprites(int scoreL, int scoreR, int rand) {

        playerL = new Sprite(this, false, scoreL, 0 + radius, height/2, 0); // Set left sprite on left side of board
        playerR = new Sprite(this, false, scoreR, width - radius, height/2, 180); // Set right sprite on right side of board

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


    // Produce true if can move to that x, y coordinate - need to be fixed
    public boolean canMove(double x, double y){
        // Check if hit borders
        if (x < 0 || x > width || y < 0 || y > height)
            return false;
        // Check of hit obstacles
        for (Obstacle o: obstacles){
            if (o.getXRange().contains(x) && o.getYRange().contains(y))
                return false;
        }
        return true;
    }

    // If there is a collision, update score, and reset sprites
    public boolean checkCollision(){
        double radius = Sprite.radius;
        boolean hasCollision = Math.abs(playerL.getX() - playerR.getX()) <= 2*radius && Math.abs(playerL.getY() - playerR.getY()) <= 2*radius;

        if (hasCollision) {
            resetSprites(0); // Condition 0 means hunter updates score
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
        if ((currentFrame != 0 && (currentFrame % switchRoleTime) == 0)){
            resetSprites(1); // (Condition 1 = time has run out, hunted gets point)
        }

    }

    public void updateBoard(){
        playerL.action(); // Move left sprite
        playerR.action(); // Move right sprite
        checkCollision();
        checkSwitchRoles();
        currentFrame++;
    }


}
