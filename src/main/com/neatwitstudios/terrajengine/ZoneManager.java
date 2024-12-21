package com.neatwitstudios.terrajengine;

import com.neatwitstudios.terrajengine.entity.Player;
import com.neatwitstudios.terrajengine.map.*;

public class ZoneManager {

    private int loadedZoneId = 0;

    private final BlockMapManager blockMapManager;
    private final Player player;

    public ZoneManager(BlockMapManager blockMapManager, Player player) {
        this.blockMapManager = blockMapManager;
        this.player = player;
    }

    public void loadZone(int zoneId) {
        loadedZoneId = zoneId;

        if (zoneId == 0) {
            blockMapManager.loadMapByPath("/resources/static/maps/map0.txt");
            player.initializePlayerByInitialPosition(Block.SIZE * 4, Block.SIZE + 1);
        }
    }

    public void loadInitialZone() {
        loadZone(0);
    }
}
