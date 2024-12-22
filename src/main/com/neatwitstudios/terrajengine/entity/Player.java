package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.*;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;
import java.util.List;

public class Player extends Entity {

    private static final int SPEED = Block.SIZE * 10/64;
    private static final int JUMP_SPEED = Block.SIZE * 5/16;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 7/32;

    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;
    private final AttackManager attackManager;
    private final CollisionsChecker collisionsChecker;

    private static final int MAX_COYOTE_FRAMES = 3;
    private int coyoteFrames = 0;

    private int spriteNum;
    private int spriteWalkCounter;
    private boolean isWalking = false;

    private boolean isAttacking;
    private int attackCounter;

    public Player(
            KeyHandler keyHandler,
            MouseHandler mouseHandler,
            AttackManager attackManager,
            CollisionsChecker collisionsChecker
    ) {
        solidBounds = new Rectangle(
                -Block.SIZE * 17/20,
                0,
                Block.SIZE * 17/10,
                Block.SIZE * 15/10
        );

        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        this.attackManager = attackManager;
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
        attackManager.damageEntities(List.of(this));
        handleAttacking();
        updatePositionAndSpeed();
        updateSprite();
    }

    private void handleAttacking() {
        if (mouseHandler.isMouse1Pressed()) {
            isAttacking = true;
            if (attackCounter == 0) {
                attackManager.submitAttack(new StandardAttack(
                        solidBounds.width / 2,
                        0,
                        this
                ));
            }
        }
        if (isAttacking && ++attackCounter > 36) {
            isAttacking = false;
            attackCounter = 0;
        }
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
        isWalking = xSpeed != 0;
        if (!isWalking) {
            spriteWalkCounter = 0;
        }
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
            spriteNum = 0;
        } else {
            spriteNum = 1;
        }

        if (isWalking) {
            spriteWalkCounter++;
            int q = spriteWalkCounter % 32;
            if (q < 8) {
                spriteNum = 2;
            } else if (q < 16) {
                spriteNum = 3;
            } else if (q < 24) {
                spriteNum = 4;
            } else {
                spriteNum = 5;
            }
            if (!isFacingRight) {
                spriteNum += 4;
            }
        }

        if (isAttacking && attackCounter < 20) {
            spriteNum = attackCounter < 4 ? 10 : 11;
            if (!isFacingRight) {
                spriteNum += 2;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        int spriteWidth = solidBounds.width;
        int offset = 0;

        if (spriteNum > 1 && spriteNum < 10) {
            spriteWidth = solidBounds.width * 11/10;
        }

        if (spriteNum == 11 || spriteNum == 13) {
            spriteWidth = solidBounds.width * 13/10;
        }

        if (spriteNum == 13) {
            offset = -Block.SIZE / 2;
        }

        g2d.drawImage(
                SpritesManager.getSprites("player")[spriteNum],
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2 + offset, camera),
                CoordConverter.getScreenY(y + solidBounds.y + solidBounds.height, camera),
                CoordConverter.getResizedLength(spriteWidth, camera),
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

        g2d.drawString(
                "Life: " + health,
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.height, camera) - 30
        );
    }
}
