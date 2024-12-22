package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;
import com.neatwitstudios.terrajengine.map.CollisionsChecker;

import java.awt.*;

public class ProjectileAttack implements Attack {

    private final Rectangle hitBox;

    private final boolean isGoingRight;
    private int duration = 200;
    private boolean isFinished;

    private final Entity owner;
    private final CollisionsChecker collisionsChecker;

    public ProjectileAttack(Entity owner, CollisionsChecker collisionsChecker) {
        isGoingRight = owner.isFacingRight();
        this.hitBox = new Rectangle(
                owner.getX() - Block.SIZE / 2,
                owner.getY() + Block.SIZE * 3 / 4,
                Block.SIZE,
                Block.SIZE / 5
        );
        this.owner = owner;
        this.collisionsChecker = collisionsChecker;
    }

    @Override
    public void update() {
        hitBox.x += isGoingRight ? Block.SIZE / 4 : -Block.SIZE / 4;

        if (collisionsChecker.isInsideCollision(hitBox.x, hitBox.y)) {
            duration = 0;
        }
        duration--;
        if (duration <= 0) {
            isFinished = true;
        }
    }

    @Override
    public void damageEntity(Entity entity) {
        Rectangle solidBounds = entity.getSolidBounds();
        Rectangle entityHitBox = new Rectangle(
                solidBounds.x + entity.getX(),
                solidBounds.y + entity.getY(),
                solidBounds.width,
                solidBounds.height
        );
        if (owner != entity && hitBox.intersects(entityHitBox)) {
            int damage = 30;
            entity.takeDamage(damage);
            duration = 0;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        g2d.drawImage(
                SpritesManager.getSprites("projectile")[isGoingRight? 0 : 1],
                CoordConverter.getScreenX(hitBox.x - hitBox.width / 2, camera),
                CoordConverter.getScreenY(hitBox.y, camera),
                CoordConverter.getResizedLength(hitBox.width, camera),
                CoordConverter.getResizedLength(hitBox.height, camera),
                null
        );
    }

    @Override
    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(hitBox.x - hitBox.width / 2, camera),
                CoordConverter.getScreenY(hitBox.y, camera),
                CoordConverter.getResizedLength(hitBox.width, camera),
                CoordConverter.getResizedLength(hitBox.height, camera)
        );
    }
}
