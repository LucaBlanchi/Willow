package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectileAttack implements Attack {

    private final Rectangle hitBox;

    private int damage = 30;
    private boolean isGoingRight;
    private int duration = 200;
    private boolean isFinished;

    private final Entity owner;

    public ProjectileAttack(Entity owner) {
        isGoingRight = owner.isFacingRight();
        this.hitBox = new Rectangle(
                owner.getX() - Block.SIZE / 2,
                owner.getY() + Block.SIZE,
                Block.SIZE,
                Block.SIZE / 4
        );
        this.owner = owner;
    }

    public void update() {
        hitBox.x += isGoingRight ? Block.SIZE / 4 : -Block.SIZE / 4;

        duration--;
        if (duration <= 0) {
            isFinished = true;
        }
    }

    public void damageEntity(Entity entity) {
        Rectangle solidBounds = entity.getSolidBounds();
        Rectangle entityHitBox = new Rectangle(
                solidBounds.x + entity.getX(),
                solidBounds.y + entity.getY(),
                solidBounds.width,
                solidBounds.height
        );
        if (owner != entity && hitBox.intersects(entityHitBox)) {
            entity.takeDamage(damage);
            duration = 0;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

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
