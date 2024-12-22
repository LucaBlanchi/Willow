package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.entity.Entity;

public interface CollisionsChecker {

    boolean isStandingOnGround(Entity entity);

    int getAdjustedXSpeedToAvoidCollisions(Entity entity);

    int getAdjustedYSpeedToAvoidCollision(Entity entity);

    boolean isInsideCollision(int x, int y);
}
