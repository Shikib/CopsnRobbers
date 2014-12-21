package com.example.ButtonGames.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 2014-12-20.
 */
public class Board {

    private Player playerR;
    private Player playerL;
    private final int HEIGHT = 1920;
    private final int WIDTH = 1080;
    private List<Obstacle> obstacles;


    public Board(Player playerR, Player playerL){
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
    public boolean canMove(Double x, Double y){
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

    public void updateBoard(){
        playerL.action();
        playerR.action();
    }


}
