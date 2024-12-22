package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {

    protected BufferedImage sprite;
    protected boolean pickedUp = false;
    protected Rectangle bounds;

    public abstract String getEffectOnCollision(Player player);

    public abstract void draw(Graphics2D g2d, Camera camera);

    public abstract void drawDebugFeatures(Graphics2D g2d, Camera camera);

    public boolean isPickedUp() {
        return pickedUp;
    }
}
