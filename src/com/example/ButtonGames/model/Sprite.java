package com.example.ButtonGames.model;

import android.graphics.Bitmap;

import java.util.TimerTask;

public class Sprite {

    public static final double radius = 40; // subject to change

    private Board board;   // corresponding board
    private boolean state; // true implies hunter, false implies hunted

    private double x;
    private double y;

    private int score;        // int from 0 to 5
    private double direction; // angle we are currently facing right now
    private double rspeed;    // speed of rotation
    private double speed;     // max speed -- change if add acceleration

    private boolean spinning; // is the sprite spinning -- false implies moving


    public Sprite(Board board, boolean state, int score, double x, double y, double direction) {
        this.board = board;
        this.state = state;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.score = score;
        this.spinning = true;
        this.speed = 50;
        this.rspeed = 4;

    }

    public boolean getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public boolean getSpinning() {
        return spinning;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void startSpinning() {
        this.spinning = true;
    }

    public void startMoving() {
        this.spinning = false;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    // to use in scheduled task
    public void action() {
        if (spinning)
            rotate();
        else
            move();
    }

    public void rotate() {
        direction = (direction + rspeed) % 360;
    }

    public void move() {
        double vDistance = speed*Math.sin(direction*2*Math.PI/360);
        double hDistance = speed*Math.cos(direction*2*Math.PI/360);

        moveHorizontal(hDistance);
        moveVertical(vDistance);
    }

    private void moveHorizontal(double amount) {
        if (board.canMove(x + amount, y)) // will keep moving in y unless something is done
            x += amount;
    }

    private void moveVertical(double amount) {
        if (board.canMove(x, y + amount))
            y += amount;
    }

    public int updateScore() {
        // add gameOver call here????
        return score++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprite sprite = (Sprite) o;

        if (Double.compare(sprite.direction, direction) != 0) return false;
        if (Double.compare(sprite.rspeed, rspeed) != 0) return false;
        if (Double.compare(sprite.speed, speed) != 0) return false;
        if (state != sprite.state) return false;
        if (Double.compare(sprite.x, x) != 0) return false;
        if (Double.compare(sprite.y, y) != 0) return false;
        if (!board.equals(sprite.board)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = board.hashCode();
        result = 31 * result + (state ? 1 : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(direction);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rspeed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
