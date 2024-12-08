package com.byron.services;

import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;

public class DungeonService implements IDungeonService {

    IDungeonManager dungeonManager;

    public DungeonService(IDungeonManager dungeonManager) {
        this.dungeonManager = dungeonManager;
    }
}
