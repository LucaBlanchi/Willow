package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;

public interface Attack {

    void update();

    void damageEntity(Entity entity);

    boolean isFinished();

    void draw(Graphics2D g2d, Camera camera);

    void drawDebugFeatures(Graphics2D g2d, Camera camera);
}
