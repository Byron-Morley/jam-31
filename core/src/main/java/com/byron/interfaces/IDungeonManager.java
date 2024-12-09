package com.byron.interfaces;

import com.byron.models.Spawn;

import java.util.List;

public interface IDungeonManager {
    int[][] getDungeon();

    int[][] getBitmap();

    IDungeonService getDungeonService();

    List<Spawn> getSpawns();
}
