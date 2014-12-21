package com.example.ButtonGames.model;

import java.util.TimerTask;

public class Sprite {

    private Board board;   // corresponding board
    private boolean state; // true implies hunter, false implies hunted

    private double x;
    private double y;

    private int score;        // int from 0 to 5
    private double direction; // angle we are currently facing right now
    private double rspeed;    // speed of rotation
    private double speed;     // max speed -- change if add acceleration

    private boolean spinning; // is the sprite spinning
    private boolean moving;   // is the sprite moving


    public Sprite(Board board, boolean state, double x, double y, double direction) {
        this.board = board;
        this.state = state;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.score = 0;
        this.spinning = true;
        this.moving = false;
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

    public boolean getMoving() {
        return moving;
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

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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
        else if (moving)
            move();
    }

    public void rotate() {
        direction = (direction + rspeed) % 360;
    }

    public void move() {
        double vDistance = speed*Math.sin(direction);
        double hDistance = speed*Math.cos(direction);

        if (direction < 90)
            ;
        else if (direction < 180)
            hDistance = -hDistance;
        else if (direction < 270) {
            vDistance = -vDistance;
            hDistance = -hDistance;
        }
        else
            vDistance = -vDistance;

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

    private void updateScore() {
        // add gameOver call here????
        score++;
    }
}