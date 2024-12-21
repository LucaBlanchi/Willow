package com.neatwitstudios.terrajengine;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static JFrame window;
    private static boolean isFullScreen = false;

    private static final int STANDARD_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width * 2 / 3;
    private static final int STANDARD_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height * 2 / 3;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Willow");

        ImageIcon icon = new ImageIcon(Main.class.getResource("/resources/static/icon.png"));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setUndecorated(true);
        toggleFullScreen();

        window.setVisible(true);
        gamePanel.startGameThread();
    }

    public static void toggleFullScreen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (!isFullScreen) {
            window.dispose();
            window.setUndecorated(true);
            gd.setFullScreenWindow(window);
        } else {
            gd.setFullScreenWindow(null);
            window.dispose();
            window.setUndecorated(false);
            window.setSize(STANDARD_WIDTH, STANDARD_HEIGHT);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        }
        isFullScreen = !isFullScreen;
    }

    public static void exitFullScreen() {
        if (isFullScreen) {
            toggleFullScreen();
        }
    }
}
