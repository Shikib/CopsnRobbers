package com.example.ButtonGames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Sarah on 2014-12-20.
 */
public class Board {

    private Sprite playerL;
    private Sprite playerR;
    private boolean hunterState; // true = left is hunter, false = right is hunter


    private final int HEIGHT = 370;
    private final int WIDTH = 800;
    private List<Obstacle> obstacles;


    public Board(List<Obstacle> obstacles){
        this.obstacles = obstacles;
        initSprites(0,0,(int) Math.random()* 2);
    }

    public void initSprites(int scoreL, int scoreR, int rand) {
        double radius = Sprite.radius;

        playerL = new Sprite(this, false, scoreL, 0 + radius, HEIGHT/2, 0);
        playerR = new Sprite(this, false, scoreR, WIDTH - radius, HEIGHT/2, 180);

        if (rand == 1) {
            playerL.setState(true);
            hunterState = true;
        }
        else {
            playerR.setState(true);
            hunterState = false;
        }
    }

    public Sprite getPlayerL() {
        return playerL;
    }

    public Sprite getPlayerR() {
        return playerR;
    }


    // Produce true if can move to that x, y coordinate
    public boolean canMove(double x, double y){
        if (x < 0 || x > WIDTH || y < 0 || y > HEIGHT)
            return false;
        for (Obstacle o: obstacles){
            if (o.getXRange().contains(x) && o.getYRange().contains(y))
                return false;
        }
        return true;
    }

    // If there is a collision, update score, and switch roles
    public boolean checkCollision(){
        double radius = Sprite.radius;
        boolean hasCollision = Math.abs(playerL.getX() - playerR.getX()) <= 2*radius && Math.abs(playerL.getY() - playerR.getY()) <= 2*radius;

        if (hasCollision) {
            resetSprites(0);
        }

        return hasCollision;
    }

    // condition is 0 if tag, 1 if time ran out
    public void resetSprites(int condition) {
        Sprite hunter;
        Sprite hunted;
        if (hunterState) {
            hunter = playerL;
            hunted = playerR;
        }
        else {
            hunter = playerR;
            hunted = playerL;
        }

        if (condition == 0)
            hunter.updateScore();
        else
            hunted.updateScore();

        // consider putting delay here
        initSprites(playerL.getScore(), playerR.getScore(), hunterState ? 0 : 1);

    }

    public void updateBoard(){
        playerL.action();
        playerR.action();
        checkCollision();
    }


}
