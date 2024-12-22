package com.neatwitstudios.terrajengine.entity;

import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AttackManager {

    private final List<Attack> attacks = new ArrayList<>();

    public void clearAttacks() {
        attacks.clear();
    }

    public void updateAttacks() {
        for (Attack attack : attacks) {
            attack.update();
        }
        attacks.removeIf(Attack::isFinished);
    }

    public void damageEntities(List<Entity> entities) {
        for (Attack attack : attacks) {
            for (Entity entity : entities) {
                attack.damageEntity(entity);
            }
        }
    }

    public void drawAttacks(Graphics2D g2d, Camera camera) {
        for (Attack attack : attacks) {
            attack.draw(g2d, camera);
        }
    }

    public void drawDebugFeatures(Graphics2D g2d, Camera camera) {
        for (Attack attack : attacks) {
            attack.drawDebugFeatures(g2d, camera);
        }
    }

    public void submitAttack(Attack attack) {
        attacks.add(attack);
    }
}
