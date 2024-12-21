package com.neatwitstudios.terrajengine.map;

import java.awt.image.BufferedImage;

public record BackgroundOrForeground(
        int x,
        int y,
        int width,
        int height,
        BufferedImage image,
        int distance
) {}
