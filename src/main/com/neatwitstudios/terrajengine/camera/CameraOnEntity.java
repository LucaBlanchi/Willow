package com.neatwitstudios.terrajengine.camera;

import com.neatwitstudios.terrajengine.entity.Entity;

public class CameraOnEntity implements Camera {
    private final int width;
    private final int height;
    private final Entity entity;

    public CameraOnEntity(Entity entity, int width, int height) {
        this.width = width;
        this.height = height;
        this.entity = entity;
    }

    public int getCenterX() {
        return entity.getX();
    }

    public int getCenterY() {
        return entity.getY() + entity.getSolidBounds().height / 2;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
