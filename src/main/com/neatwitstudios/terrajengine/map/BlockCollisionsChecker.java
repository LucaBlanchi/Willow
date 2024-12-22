package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.entity.Entity;

public class BlockCollisionsChecker implements CollisionsChecker {

    private final BlockMapManager blockMapManager;

    public BlockCollisionsChecker(BlockMapManager blockMapManager) {
        this.blockMapManager = blockMapManager;
    }

    public boolean isStandingOnGround(Entity entity) {
        int rightX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width / 2;
        int leftX = entity.getX() + entity.getSolidBounds().x - entity.getSolidBounds().width / 2;
        int justUnderBottomY = entity.getY() + entity.getSolidBounds().y - 1;

        int playerLeftCol = leftX / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerBottomRow = (justUnderBottomY) / Block.SIZE;

        return blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol) || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol);
    }

    public int getAdjustedXSpeedToAvoidCollisions(Entity entity) {
        int projectedLeftX = entity.getX() + entity.getSolidBounds().x - entity.getSolidBounds().width / 2 + entity.getXSpeed();
        int projectedRightX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width / 2 + entity.getXSpeed();
        int topY = entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height;
        int bottomY = entity.getY() + entity.getSolidBounds().y;
        int middleY = (topY + bottomY) / 2;

        int playerTopRow = topY / Block.SIZE;
        int playerBottomRow = bottomY / Block.SIZE;
        int playerMiddleRow = middleY / Block.SIZE;
        int playerRightCol = projectedRightX / Block.SIZE;
        int playerLeftCol = projectedLeftX / Block.SIZE;

        if (entity.getXSpeed() > 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerRightCol)
                    || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)
                    || blockMapManager.isBlockSolid(playerMiddleRow, playerRightCol)
            ) {
                int nearestBlockLeftEdge = (playerRightCol * Block.SIZE);
                int maxMoveRight = nearestBlockLeftEdge - (entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width / 2) - 1;
                return Math.min(entity.getXSpeed(), maxMoveRight);
            }
        } else if (entity.getXSpeed() < 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol)
                    || blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol)
                    || blockMapManager.isBlockSolid(playerMiddleRow, playerLeftCol)
            ) {
                int nearestBlockRightEdge = (playerLeftCol * Block.SIZE) + Block.SIZE;
                int maxMoveLeft = nearestBlockRightEdge - (entity.getX() + entity.getSolidBounds().x - entity.getSolidBounds().width / 2);
                return Math.max(entity.getXSpeed(), maxMoveLeft);
            }
        }

        return entity.getXSpeed();
    }

    public int getAdjustedYSpeedToAvoidCollision(Entity entity) {
        int rightX = entity.getX() + entity.getSolidBounds().x + entity.getSolidBounds().width / 2;
        int leftX = entity.getX() + entity.getSolidBounds().x - entity.getSolidBounds().width / 2;
        int middleX = (rightX + leftX) / 2;
        int projectedTopY = entity.getY() + entity.getYSpeed() + entity.getSolidBounds().y;
        int projectedBottomY = entity.getY() + entity.getYSpeed() + entity.getSolidBounds().y + entity.getSolidBounds().height;

        int playerBottomRow = projectedTopY / Block.SIZE;
        int playerTopRow = projectedBottomY / Block.SIZE;
        int playerRightCol = rightX / Block.SIZE;
        int playerLeftCol = leftX / Block.SIZE;
        int playerMiddleCol = middleX / Block.SIZE;

        if (entity.getYSpeed() < 0) {
            if (blockMapManager.isBlockSolid(playerBottomRow, playerLeftCol)
                    || blockMapManager.isBlockSolid(playerBottomRow, playerRightCol)
                    || blockMapManager.isBlockSolid(playerBottomRow, playerMiddleCol)
            ) {
                int nearestBlockBottomEdge = (playerBottomRow * Block.SIZE) + Block.SIZE;
                int maxMoveUp = nearestBlockBottomEdge - (entity.getY() + entity.getSolidBounds().y);
                return Math.max(entity.getYSpeed(), maxMoveUp);
            }
        } else if (entity.getYSpeed() > 0) {
            if (blockMapManager.isBlockSolid(playerTopRow, playerLeftCol)
                    || blockMapManager.isBlockSolid(playerTopRow, playerRightCol)
                    || blockMapManager.isBlockSolid(playerTopRow, playerMiddleCol)
            ) {
                int nearestBlockTopEdge = (playerTopRow * Block.SIZE);
                int maxMoveDown = nearestBlockTopEdge - (entity.getY() + entity.getSolidBounds().y + entity.getSolidBounds().height) - 1;
                entity.setYSpeed(0);
                return Math.min(entity.getYSpeed(), maxMoveDown);
            }
        }
        return entity.getYSpeed();
    }

    public boolean isInsideCollision(int x, int y) {
        int col = x / Block.SIZE;
        int row = y / Block.SIZE;

        return blockMapManager.isBlockSolid(row, col);
    }
}
