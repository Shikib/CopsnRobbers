package com.example.ButtonGames.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 2014-12-20.
 */
public class Board {

    private Sprite playerR;
    private Sprite playerL;
    private final int HEIGHT = 1920;
    private final int WIDTH = 1080;
    private List<Obstacle> obstacles;


    public Board(Sprite playerR, Sprite playerL){
        playerR = this.playerR;
        playerL = this.playerL;
        obstacles = new ArrayList<Obstacle>();
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
        boolean insideObstacle = false;
        for (Obstacle o: obstacles){
            if (o.getXRange().contains(x) && o.getYRange().contains(y)){
                insideObstacle = true;
            }
        }
        return !insideObstacle;
    }

    public void switchRoles(){
        playerR.setState(playerL.getState());
        playerL.setState(!playerL.getState());
        }

    // If there is a collision, update score, and switch roles
    public boolean checkCollision(){
        boolean hasCollision = ((playerL.getX() == playerR.getX()) && (playerL.getY() == playerR.getY()));

        if (hasCollision) {
            updateScore();
            switchRoles();
        }
        return hasCollision;
    }

    // Maybe should put update score in sprite...
    public void updateScore(){
        if (playerR.getState()) {
            playerR.setScore(playerR.getScore() + 1);
        } else {
            playerL.setScore(playerL.getScore() + 1);
        }
    }

    public void updateBoard(){
        checkCollision();
        playerL.action();
        playerR.action();
    }


}
