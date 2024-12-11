package com.byron.managers;

import com.badlogic.gdx.utils.Array;
import com.byron.factories.ModelFactory;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.models.Spawn;
import com.byron.services.DungeonService;
import com.byron.utils.dungeon.Dungeon;
import com.byron.utils.dungeon.DungeonUtils;
import com.byron.utils.dungeon.Room;

import java.util.List;
import java.util.Random;

import static com.byron.utils.Config.MAP_WIDTH;
import static com.byron.utils.Config.MAP_HEIGHT;

public class DungeonManager implements IDungeonManager {
    private final int TILE_SCALE = 1;
    IDungeonService dungeonService;
    int[][] dungeon;
    int[][] bitmap;
    List<Spawn> spawns;
    Array<Room> rooms;

    public DungeonManager(IItemService itemService) {
        spawns = ModelFactory.getDungeonSpawns();

        Random random = new Random();
        int seed = random.nextInt(1000000000);
//        int seed = 60570046;
        System.out.println("seed: "+ seed);

        int roomCountMax = MAP_WIDTH / 4;


        Dungeon dungeonObject = DungeonUtils.createDungeon(
            MAP_WIDTH, MAP_HEIGHT,
            roomCountMax/2,
            roomCountMax,
            3,
            6,
            96,
            seed
        );
        dungeon = dungeonObject.getMap();
        rooms = dungeonObject.getRooms();
        System.out.println("DungeonManager.DungeonManager "+ rooms.size);
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

    public Array<Room> getRooms() {
        return rooms;
    }
}


