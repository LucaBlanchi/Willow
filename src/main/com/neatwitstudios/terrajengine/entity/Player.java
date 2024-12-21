package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.*;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private static final int SPEED = Block.SIZE * 10/64;
    private static final int JUMP_SPEED = Block.SIZE * 3/8;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 7/32;

    private final KeyHandler keyHandler;
    private final CollisionsChecker collisionsChecker;

    private static final int MAX_COYOTE_FRAMES = 3;
    private int coyoteFrames = 0;

    private BufferedImage sprite;

    public Player(KeyHandler keyHandler, CollisionsChecker collisionsChecker) {
        solidBounds = new Rectangle(
                -Block.SIZE * 17/20,
                0,
                Block.SIZE * 17/10,
                Block.SIZE * 15/10
        );

        this.keyHandler = keyHandler;
        this.collisionsChecker = collisionsChecker;
    }

    public void initializePlayerByInitialPosition(int initialX, int initialY) {
        health = 100;
        x = initialX;
        y = initialY;
        xSpeed = 0;
        ySpeed = 0;

        isFacingRight = true;
    }

    @Override
    public void update() {
        updatePositionAndSpeed();
        updateSprite();
    }

    private void updatePositionAndSpeed() {
        boolean isJumping = false;
        boolean isStandingOnGround = collisionsChecker.isStandingOnGround(this);
        if (isStandingOnGround) {
            coyoteFrames = MAX_COYOTE_FRAMES;
        } else {
            coyoteFrames--;
        }
        if (keyHandler.isUpPressed() && coyoteFrames > 0) {
            this.ySpeed = JUMP_SPEED;
            isJumping = true;
            coyoteFrames = 0;
        }
        if (!isJumping && isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        int newYSpeed = collisionsChecker.getAdjustedYSpeedToAvoidCollision(this);
        y += newYSpeed;

        xSpeed = SPEED * (keyHandler.isRightPressed() ? 1 : 0) - SPEED * (keyHandler.isLeftPressed() ? 1 : 0);
        updateFacingDirection();
        x += collisionsChecker.getAdjustedXSpeedToAvoidCollisions(this);
    }

    private void updateFacingDirection() {
        if (xSpeed > 0) {
            isFacingRight = true;
        } else if (xSpeed < 0) {
            isFacingRight = false;
        }
    }

    private void updateSprite() {
        if (isFacingRight) {
            sprite = SpritesManager.getSprites("player")[0];
        } else {
            sprite = SpritesManager.getSprites("player")[1];
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        g2d.drawImage(
                sprite,
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.y + solidBounds.height, camera),
                CoordConverter.getResizedLength(solidBounds.width, camera),
                CoordConverter.getResizedLength(solidBounds.height, camera),
                null
        );
    }

    @Override
    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.y + solidBounds.height, camera),
                CoordConverter.getResizedLength(solidBounds.width, camera),
                CoordConverter.getResizedLength(solidBounds.height, camera)
        );
    }
}
