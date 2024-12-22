package com.neatwitstudios.terrajengine.items;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.entity.SpritesManager;
import com.neatwitstudios.terrajengine.map.Block;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Pistachio extends Item {

    public Pistachio(Rectangle bounds) {
        this.bounds = bounds;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/pistachio.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEffectOnCollision(Player player) {
        if (!bounds.contains(player.getX(), player.getY() + Block.SIZE / 2)) {
            return null;
        }
        pickedUp = true;
        return "theEnd";
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        g2d.drawImage(
                sprite,
                CoordConverter.getScreenX(bounds.x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(bounds.y + Block.SIZE, camera),
                CoordConverter.getResizedLength(bounds.width, camera),
                CoordConverter.getResizedLength(bounds.height, camera),
                null
        );
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                CoordConverter.getScreenX(bounds.x - Block.SIZE / 2, camera),
                CoordConverter.getScreenY(bounds.y + Block.SIZE, camera),
                CoordConverter.getResizedLength(bounds.width, camera),
                CoordConverter.getResizedLength(bounds.height, camera)
        );
    }
}
