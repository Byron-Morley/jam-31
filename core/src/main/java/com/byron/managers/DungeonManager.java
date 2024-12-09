package com.byron.managers;

import com.byron.factories.ModelFactory;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.models.Spawn;
import com.byron.services.DungeonService;
import com.byron.utils.dungeon.DungeonUtils;

import java.util.List;

import static com.byron.utils.Config.MAP_HEIGHT;
import static com.byron.utils.Config.MAP_WIDTH;

public class DungeonManager implements IDungeonManager {
    private final int TILE_SCALE = 1;
    IDungeonService dungeonService;
    int[][] dungeon;
    int[][] bitmap;
    List<Spawn> spawns;


    public DungeonManager(IItemService itemService) {
        spawns = ModelFactory.getDungeonSpawns();
        System.out.println("DungeonManager.DungeonManager "+ spawns.size());
        dungeon = DungeonUtils.createDungeon(MAP_WIDTH, MAP_HEIGHT, 12, 20, 2, 6, 96);
//        dungeon = DungeonUtils.createSimpleDungeon(MAP_WIDTH, MAP_HEIGHT);
        bitmap = new int[MAP_WIDTH * 2][MAP_HEIGHT * 2];
        dungeonService = new DungeonService(this, itemService);

    }

    public int[][] getDungeon() {
        return dungeon;
    }

    public int[][] getBitmap() {
        return bitmap;
    }

    @Override
    public IDungeonService getDungeonService() {
        return dungeonService;
    }

    public List<Spawn> getSpawns() {
        return spawns;
    }
}


