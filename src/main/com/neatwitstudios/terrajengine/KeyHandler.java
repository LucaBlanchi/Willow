package com.neatwitstudios.terrajengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private boolean rPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_R) {
            rPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            Main.exitFullScreen();
        }
        if (code == KeyEvent.VK_F11) {
            Main.toggleFullScreen();
        }

        if (code == KeyEvent.VK_F3) {
            GamePanel.toggleDebugMode();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_R) {
            rPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isRPressed() {
        return rPressed;
    }
}
