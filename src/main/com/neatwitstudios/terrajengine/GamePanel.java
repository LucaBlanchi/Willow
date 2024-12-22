package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.camera.CameraOnEntity;
import com.neatwitstudios.terrajengine.entity.AttackManager;
import com.neatwitstudios.terrajengine.entity.EnemyManager;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;

    public static final int BLOCKS_PER_ROW = 27;

    private static final int MAX_FPS = 60;

    private static boolean debugMode = false;

    private final KeyHandler keyHandler = new KeyHandler();
    private final AttackManager attackManager = new AttackManager();
    private final BlockMapManager blockMapManager = new BlockMapManager();
    private final BackgroundAndForegroundManager bgAndFgManager = new BackgroundAndForegroundManager();
    private final CollisionsChecker blockCollisionsChecker = new BlockCollisionsChecker(blockMapManager);
    private final Player player = new Player(keyHandler, attackManager, blockCollisionsChecker);
    private final EnemyManager enemyManager = new EnemyManager(attackManager);
    private final ZoneManager zoneManager = new ZoneManager(
            attackManager,
            blockMapManager,
            blockCollisionsChecker,
            bgAndFgManager,
            player,
            enemyManager
    );
    private final CameraOnEntity camera = new CameraOnEntity(player, Block.SIZE * BLOCKS_PER_ROW, Block.SIZE * BLOCKS_PER_ROW * HEIGHT / WIDTH);

    private Thread gameThread;

    private double scaleFactorX = 1.0;
    private double scaleFactorY = 1.0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        zoneManager.loadInitialZone();
    }

    private void handleResize() {
        int newWidth = this.getWidth();
        int newHeight = this.getHeight();

        scaleFactorX = (double) newWidth / WIDTH;
        scaleFactorY = (double) newHeight / HEIGHT;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameThread != null) {

            long currentTime = System.nanoTime();

            update();
            repaint();

            long elapsedTime = System.nanoTime() - currentTime;
            sleepToCapFps(elapsedTime);
        }
    }

    private void sleepToCapFps(long elapsedTime) {
        long sleepTime = 1000 / MAX_FPS - elapsedTime / 1000000;
        if (sleepTime < 0) {
            sleepTime = 0;
        }
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        player.update();
        enemyManager.updateEnemies();
        if (player.getHealth() <= 0)  {
            zoneManager.loadZone(0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(scaleFactorX, scaleFactorY);

        bgAndFgManager.drawBackground(g2d, camera);
        blockMapManager.draw(g2d, camera);
        enemyManager.drawEnemies(g2d, camera);
        player.draw(g2d, camera);
        attackManager.drawAttacks(g2d, camera);

        if (debugMode) {
            drawDebugFeatures(g2d);
        }

        g2d.dispose();
    }

    private void drawDebugFeatures(Graphics2D g2d) {
        player.drawDebugFeatures(g2d, camera);
        enemyManager.drawDebugFeatures(g2d, camera);
        attackManager.drawDebugFeatures(g2d, camera);
    }

    public static void toggleDebugMode() {
        debugMode = !debugMode;
    }
}
