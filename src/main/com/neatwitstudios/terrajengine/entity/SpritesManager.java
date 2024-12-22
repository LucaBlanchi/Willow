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
    private static final String PROJECTILE = "projectile";

    private SpritesManager() {
    }

    private static Map<String, BufferedImage[]> init() {
        Map<String, BufferedImage[]> map = Map.of(
                PLAYER, new BufferedImage[16],
                PORCUPINE, new BufferedImage[16],
                PROJECTILE, new BufferedImage[16]
        );
        try {
            map.get(PLAYER)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRight.png")));
            map.get(PLAYER)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerLeft.png")));
            map.get(PLAYER)[2] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunRight1.png")));
            map.get(PLAYER)[3] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunRight2.png")));
            map.get(PLAYER)[4] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunRight3.png")));
            map.get(PLAYER)[5] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunRight4.png")));
            map.get(PLAYER)[6] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunLeft1.png")));
            map.get(PLAYER)[7] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunLeft2.png")));
            map.get(PLAYER)[8] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunLeft3.png")));
            map.get(PLAYER)[9] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/playerRunLeft4.png")));

            map.get(PORCUPINE)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineRight.png")));
            map.get(PORCUPINE)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineLeft.png")));
            map.get(PORCUPINE)[2] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineStep1Right.png")));
            map.get(PORCUPINE)[3] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineStep2Right.png")));
            map.get(PORCUPINE)[4] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineStep1Left.png")));
            map.get(PORCUPINE)[5] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineStep2Left.png")));

            map.get(PROJECTILE)[0] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineProjectileRight.png")));
            map.get(PROJECTILE)[1] = ImageIO.read(Objects.requireNonNull(SpritesManager.class.getResourceAsStream("/static/sprites/porcupineProjectileLeft.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static BufferedImage[] getSprites(String name) {
        return sprites.get(name);
    }
}
