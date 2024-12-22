package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectileAttack implements Attack {

    private final int xDisplacement;
    private final int yDisplacement;
    private final Rectangle hitBox;

    private final int damage;
    private int duration;
    private boolean isFinished;

    private final Entity owner;
    private final List<Entity> damagedEntities = new ArrayList<>();

    public ProjectileAttack(int xDisplacement, int yDisplacement, int width, int height, int damage, int duration, Entity owner) {
        this.xDisplacement = xDisplacement;
        this.yDisplacement = yDisplacement;
        this.hitBox = new Rectangle(
                owner.getX() + (owner.isFacingRight ? xDisplacement : -xDisplacement),
                owner.getY() + yDisplacement,
                width,
                height
        );
        this.damage = damage;
        this.duration = duration;
        this.owner = owner;
    }

    public void update() {
        hitBox.x = owner.getX() + (owner.isFacingRight ? xDisplacement : -xDisplacement);
        hitBox.y = owner.getY() + yDisplacement;
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
        if (owner != entity
                && hitBox.intersects(entityHitBox)
                && !damagedEntities.contains(entity)) {
            entity.takeDamage(damage);
            damagedEntities.add(entity);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void draw(Graphics2D g2d, Camera camera) {

    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(hitBox.x - hitBox.width / 2, camera),
                CoordConverter.getScreenY(hitBox.y + hitBox.height, camera),
                CoordConverter.getResizedLength(hitBox.width, camera),
                CoordConverter.getResizedLength(hitBox.height, camera)
        );
    }
}
