package com.neatwitstudios.terrajengine.map;

import java.awt.image.BufferedImage;

public class Block {

    public static final int SIZE = 1024;

    private BufferedImage image;
    private boolean isSolid;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }
}
