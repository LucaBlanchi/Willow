package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.SoundManager;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;

public class Porcupine extends Entity {

    private static final int SPEED = Block.SIZE * 5/64;
    private static final int GRAVITY = Block.SIZE / 64;
    private static final int MAX_FALL_SPEED = Block.SIZE * 3/32;

    private int spriteNum = 0;
    private int spriteWalkCounter;
    private boolean isWalking = false;

    private int attackCoolDown = 0;

    private final Player player;
    private final AttackManager attackManager;
    private final CollisionsChecker collisionsChecker;

    public Porcupine(int x, int y, Player player, AttackManager attackManager, CollisionsChecker collisionsChecker) {
        this.player = player;
        this.attackManager = attackManager;
        this.collisionsChecker = collisionsChecker;

        this.x = x;
        this.y = y;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.solidBounds = new Rectangle(
                -Block.SIZE * 14/20,
                0,
                Block.SIZE * 12/10,
                Block.SIZE * 14/10
        );
        this.isFacingRight = false;
        this.health = 100;
    }

    @Override
    public void update() {
        handleAttacking();
        updatePositionAndSpeed();
        updateSprite();
    }

    private void handleAttacking() {
        if (Math.abs(player.getX() - x) < Block.SIZE * 8
                && Math.abs(player.getY() - y) < Block.SIZE
                && attackCoolDown == 0) {
            SoundManager.playSE(2);
            attackManager.submitAttack(new ProjectileAttack(
                    this,
                    collisionsChecker
            ));
            attackCoolDown = 60;
        }
        if (attackCoolDown > 0) {
            attackCoolDown--;
        }
    }

    private void updatePositionAndSpeed() {
        if (isPlayerInSight()) {
            if (player.getX() < x) {
                xSpeed = -SPEED;
                isFacingRight = false;
            } else {
                xSpeed = SPEED;
                isFacingRight = true;
            }
            x += collisionsChecker.getAdjustedXSpeedToAvoidCollisions(this);

            isWalking = true;
        } else {
            spriteWalkCounter = 0;
            isWalking = false;
        }

        boolean isStandingOnGround = collisionsChecker.isStandingOnGround(this);
        if (isStandingOnGround) {
            ySpeed = 0;
        } else {
            ySpeed = Math.max(ySpeed - GRAVITY, -MAX_FALL_SPEED);
        }
        y += collisionsChecker.getAdjustedYSpeedToAvoidCollision(this);
    }

    private boolean isPlayerInSight() {
        return Math.abs(player.getX() - x) < Block.SIZE * 10
                && Math.abs(player.getX() - x) > Block.SIZE / 3
                && Math.abs(player.getY() - y) < Block.SIZE * 8;
    }

    private void updateSprite() {
        spriteNum = isFacingRight ? 0 : 1;
        if (isWalking) {
            spriteWalkCounter++;
            if (isFacingRight) {
                spriteNum = spriteWalkCounter % 40 < 20 ? 2 : 3;
            } else {
                spriteNum = spriteWalkCounter % 40 < 20 ? 4 : 5;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        g2d.drawImage(
                SpritesManager.getSprites("porcupine")[spriteNum],
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
        g2d.drawString(
                "Life: " + health,
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.height, camera) - 30
        );
        g2d.drawString(
                "Attack cooldown: " + attackCoolDown,
                CoordConverter.getScreenX(x + solidBounds.x - solidBounds.width / 2, camera),
                CoordConverter.getScreenY(y + solidBounds.height, camera) - 40
        );
    }
}
