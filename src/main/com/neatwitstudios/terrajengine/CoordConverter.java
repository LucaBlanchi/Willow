package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.camera.Camera;

public class CoordConverter {

    private static final int SCREEN_CENTER_X = GamePanel.WIDTH / 2;
    private static final int SCREEN_CENTER_Y = GamePanel.HEIGHT / 2;

    private CoordConverter() {}

    public static int getResizedLength(int length, Camera camera) {
        return length * GamePanel.WIDTH / camera.getWidth();
    }

    public static int getScreenX(int x, Camera camera) {
        return SCREEN_CENTER_X + (x - camera.getCenterX()) * GamePanel.WIDTH / camera.getWidth();
    }

    public static int getScreenY(int y, Camera camera) {
        return SCREEN_CENTER_Y - (y - camera.getCenterY()) * GamePanel.WIDTH / camera.getWidth();
    }
}
