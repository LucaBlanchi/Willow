package com.neatwitstudios.terrajengine.map;

import com.neatwitstudios.terrajengine.CoordConverter;
import com.neatwitstudios.terrajengine.camera.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BackgroundAndForegroundManager {

    private List<BackgroundOrForeground> backgrounds = new ArrayList<>();
    private List<BackgroundOrForeground> foregrounds = new ArrayList<>();

    public void loadBackgrounds(List<BackgroundOrForeground> backgrounds) {
        this.backgrounds.clear();
        this.backgrounds.addAll(backgrounds);
    }

    public void loadForegrounds(List<BackgroundOrForeground> foregrounds) {
        this.foregrounds.clear();
        this.foregrounds.addAll(foregrounds);
    }

    public void drawBackground(Graphics2D g2d, Camera camera) {
        drawList(g2d, camera, backgrounds);
    }

    public void drawForeground(Graphics2D g2d, Camera camera) {
        drawList(g2d, camera, foregrounds);
    }

    private void drawList(Graphics2D g2d, Camera camera, List<BackgroundOrForeground> list) {
        for (BackgroundOrForeground bg : list) {
            g2d.drawImage(
                    bg.image(),
                    CoordConverter.getScreenX(bg.x() - getXDisplacement(camera, bg), camera),
                    CoordConverter.getScreenY(bg.y(), camera),
                    CoordConverter.getResizedLength(bg.width(), camera),
                    CoordConverter.getResizedLength(bg.height(), camera),
                    null
            );
        }
    }

    private int getXDisplacement(Camera camera, BackgroundOrForeground bg) {
        if (bg.distance() == 0) {
            return 0;
        }
        return camera.getCenterX() / bg.distance();
    }
}
