package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {

    private final List<Entity> enemies = new ArrayList<>();

    private final AttackManager attackManager;

    public EnemyManager(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    public void loadEnemies(List<Entity> enemies) {
        this.enemies.clear();
        this.enemies.addAll(enemies);
    }

    public void updateEnemies() {
        for (Entity enemy : enemies) {
            enemy.update();
        }
        attackManager.updateAttacks();
        attackManager.damageEntities(enemies);
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }

    public void drawEnemies(Graphics2D g2d, Camera camera) {
        for (Entity enemy : enemies) {
            enemy.draw(g2d, camera);
        }
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        for (Entity enemy : enemies) {
            enemy.drawDebugFeatures(g2d, camera);
        }
    }
}
