package com.example.ButtonGames.model;

import android.util.Range;

/**
 * Created by Sarah on 2014-12-20.
 */
public class Obstacle {

    private Range<Double> xRange;
    private Range<Double> yRange;

    // make sure these obstacles account for radius of sprite as well
    public Obstacle (Double xL, Double xU, Double yL, Double yU){
        xRange = new Range<Double>(xL, xU);
        yRange = new Range<Double>(yL, yU);
    }

    public Range<Double> getXRange(){
        return xRange;
    }

    public Range<Double> getYRange(){
        return yRange;
    }

}
