package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.GamePanel;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final List<Item> items = new ArrayList<>();

    public ItemsManager() {}

    public void loadItems(List<Item> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public void checkCollisions(Player player) {
        List<String> effects = new ArrayList<>();
        for (Item item : items) {
            String effect = item.getEffectOnCollision(player);
            if (effect != null) {
                effects.add(effect);
            }
        }
        items.removeIf(Item::isPickedUp);
        for (String effect : effects) {
            switch (effect) {
                case "theEnd" -> GamePanel.endGame();
                default -> {
                }
            }
        }
    }

    public void drawItems(Graphics2D g2d, Camera camera) {
        for (Item item : items) {
            item.draw(g2d, camera);
        }
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        for (Item item : items) {
            item.drawDebugFeatures(g2d, camera);
        }
    }
}
