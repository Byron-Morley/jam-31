package com.byron.interfaces;

import com.badlogic.gdx.utils.Array;
import com.byron.models.Spawn;
import com.byron.utils.dungeon.Room;

import java.util.List;

public interface IDungeonManager {
    int[][] getDungeon();

    int[][] getBitmap();

    IDungeonService getDungeonService();

    List<Spawn> getSpawns();

    Array<Room> getRooms();
}
