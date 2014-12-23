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
    private double radius = playerL.radius;
    private boolean hunterState; // true = left is hunter, false = right is hunter


    private int height;
    private int width;
    private List<Obstacle> obstacles;


    public Board(List<Obstacle> obstacles, int width, int height){
        this.obstacles = obstacles;
        initSprites(0,0,(int) Math.random()* 2);
        this.width = width;
        this.height = height;
    }

    public void initSprites(int scoreL, int scoreR, int rand) {
        double radius = Sprite.radius;

        playerL = new Sprite(this, false, scoreL, 0 + radius, height/2, 0);
        playerR = new Sprite(this, false, scoreR, width - radius, height/2, 180);

        if (rand == 1) {
            playerL.setState(true);
            hunterState = true;
        }
        else {
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

    public Sprite getPlayerL() {
        return playerL;
    }

    public Sprite getPlayerR() {
        return playerR;
    }

    public List<Obstacle> getObstacles(){
        return obstacles;
    }


    // Produce true if can move to that x, y coordinate
    public boolean canMove(double x, double y){
        if (x < 0 || x > width - radius || y < 0 || y > height - radius)
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
