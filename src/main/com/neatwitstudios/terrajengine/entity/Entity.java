package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int xSpeed;
    protected int ySpeed;

    protected Rectangle solidBounds;
    protected boolean isFacingRight;

    protected int health;

    public abstract void update();

    public abstract void draw(Graphics2D g2d, Camera camera);

    public abstract void drawDebugFeatures(Graphics2D g2d, Camera camera);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int yDiff) {
        this.ySpeed = yDiff;
    }

    public Rectangle getSolidBounds() {
        return solidBounds;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
}
