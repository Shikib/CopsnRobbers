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
    private Sprite hunter;

    public TimerTask timerTask;

    private final int HEIGHT = 1920;
    private final int WIDTH = 1080;
    private List<Obstacle> obstacles;


    public Board(List<Obstacle> obstacles){
        this.obstacles = obstacles;
        initSprites(0,0,(int) Math.random()* 2);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                updateBoard();
            }
        };
    }

    public void initSprites(int scoreL, int scoreR, int rand) {
        double radius = Sprite.radius;

        playerL = new Sprite(this, false, scoreL, 0 + radius, HEIGHT/2, 0);
        playerR = new Sprite(this, false, scoreR, WIDTH - radius, HEIGHT/2, 180);

        if (rand == 1) {
            playerL.setState(true);
            hunter = playerL;
        }
        else {
            playerR.setState(true);
            hunter = playerR;
        }
    }

    public void addObstacle(Obstacle o){
        obstacles.add(o);
    }

    public boolean removeObstacle(Obstacle o){
        return obstacles.remove(o);
    }

    public List<Obstacle> getObstacles(){
        return obstacles;
    }

    // Produce true if can move to that x, y coordinate
    public boolean canMove(double x, double y){
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
            hunter.updateScore();
            switchRoles();

            int rand;
            if (hunter.equals(playerL))
                rand = 0;
            else
                rand = 1;

            // consider putting delay here
            initSprites(playerL.getScore(), playerR.getScore(), rand);
        }

        return hasCollision;
    }

    public void switchRoles(){
        if (hunter.equals(playerL))
            hunter = playerR;
        else
            hunter = playerL;

        playerR.setState(playerL.getState());
        playerL.setState(!playerL.getState());
    }


    public void updateBoard(){
        playerL.action();
        playerR.action();
        checkCollision();
    }


}
