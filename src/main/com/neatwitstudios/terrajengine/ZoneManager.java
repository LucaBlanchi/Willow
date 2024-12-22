package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.AttackManager;
import com.neatwitstudios.terrajengine.entity.EnemyManager;
import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.entity.Porcupine;
import com.neatwitstudios.terrajengine.items.ItemsManager;
import com.neatwitstudios.terrajengine.items.Pistachio;
import com.neatwitstudios.terrajengine.map.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ZoneManager {

    private int loadedZoneId = 0;

    private final AttackManager attackManager;
    private final BlockMapManager blockMapManager;
    private final CollisionsChecker collisionsChecker;
    private final BackgroundAndForegroundManager bgAndFgManager;
    private final Player player;
    private final EnemyManager enemyManager;
    private final ItemsManager itemsManager;

    public ZoneManager(
            AttackManager attackManager,
            BlockMapManager blockMapManager,
            CollisionsChecker collisionsChecker,
            BackgroundAndForegroundManager bgAndFgManager,
            Player player,
            EnemyManager enemyManager,
            ItemsManager itemsManager
    ) {
        this.attackManager = attackManager;
        this.blockMapManager = blockMapManager;
        this.collisionsChecker = collisionsChecker;
        this.bgAndFgManager = bgAndFgManager;
        this.player = player;
        this.enemyManager = enemyManager;
        this.itemsManager = itemsManager;
    }

    public void loadZone(int zoneId) {
        loadedZoneId = zoneId;

        try {
            attackManager.clearAttacks();

            if (zoneId == 0) {
                blockMapManager.loadMapByPath("/resources/static/maps/map0.txt");

                bgAndFgManager.loadBackgrounds(List.of(
                        new BackgroundOrForeground(
                                -11 * Block.SIZE,
                                50 * Block.SIZE,
                                Block.SIZE * 91,
                                Block.SIZE * 60,
                                ImageIO.read(Objects.requireNonNull(BackgroundOrForeground.class.getResourceAsStream("/static/backgrounds/background0.png"))),
                                20
                        )
                ));

                enemyManager.loadEnemies(List.of(
                        new Porcupine(Block.SIZE * 21, Block.SIZE * 10 + 1, player, attackManager, collisionsChecker)
                ));

                itemsManager.loadItems(List.of(new Pistachio(new Rectangle(
                        Block.SIZE * 35 + Block.SIZE / 2,
                        Block.SIZE * 7 + 1,
                        Block.SIZE,
                        Block.SIZE)))
                );

                player.initializePlayerByInitialPosition(Block.SIZE * 4, Block.SIZE * 7 + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadInitialZone() {
        loadZone(0);
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }
}
