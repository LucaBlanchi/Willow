package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.map.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StandardAttack implements Attack {

    private final int xDisplacement;
    private final int yDisplacement;
    private final Rectangle hitBox;

    private final int damage = 40;
    private int duration = 12;
    private boolean isFinished;

    private final Entity owner;
    private final List<Entity> damagedEntities = new ArrayList<>();

    public StandardAttack(int xDisplacement, int yDisplacement, Entity owner) {
        this.xDisplacement = xDisplacement;
        this.yDisplacement = yDisplacement;
        this.hitBox = new Rectangle(
                owner.getX() + owner.getSolidBounds().x + (owner.isFacingRight ? xDisplacement : -xDisplacement),
                owner.getY() + yDisplacement,
                Block.SIZE * 15/10,
                Block.SIZE
        );
        this.owner = owner;
    }

    @Override
    public void update() {
        hitBox.x = owner.getX() + owner.getSolidBounds().x + (owner.isFacingRight ? xDisplacement : -xDisplacement);
        hitBox.y = owner.getY() + yDisplacement;
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
        if (owner != entity
                && hitBox.intersects(entityHitBox)
                && !damagedEntities.contains(entity)) {
            entity.takeDamage(damage);
            damagedEntities.add(entity);
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {

    }

    @Override
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
