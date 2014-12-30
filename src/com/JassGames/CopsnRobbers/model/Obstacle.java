package com.JassGames.CopsnRobbers.model;



public class Obstacle {

    private double xLower;
    private double xUpper;
    private double yLower;
    private double yUpper;

    // Make sure these obstacles account for radius of sprite as well
    public Obstacle (Double xL, Double xU, Double yL, Double yU){
        xLower = xL;
        xUpper = xU;
        yLower = yL;
        yUpper = yU;
    }

    public double getXLower() {
        return xLower;
    }
    public double getXUpper() {
        return xUpper;
    }
    public double getYLower() {
        return yLower;
    }
    public double getYUpper() {
        return yUpper;
    }



}
