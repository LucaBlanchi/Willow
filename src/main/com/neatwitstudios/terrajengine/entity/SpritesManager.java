package com.neatwitstudios.terrajengine.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SpritesManager {

    private static final Map<String, BufferedImage[]> sprites = init();
    private static final String PLAYER = "player";
    private static final String PORCUPINE = "porcupine";

    private SpritesManager() {
    }

    private static Map<String, BufferedImage[]> init() {
        Map<String, BufferedImage[]> map = Map.of(
                PLAYER, new BufferedImage[16],
                PORCUPINE, new BufferedImage[16]
        );
        try {
            map.get(PLAYER)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRight.png")));
            map.get(PLAYER)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerLeft.png")));

            map.get(PORCUPINE)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineRight.png")));
            map.get(PORCUPINE)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineProjectileRight.png")));
            map.get(PORCUPINE)[2] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineProjectileLeft.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static BufferedImage[] getSprites(String name) {
        return sprites.get(name);
    }
}
