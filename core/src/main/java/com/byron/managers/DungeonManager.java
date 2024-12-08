package com.byron.managers;

import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IRenderable;
import com.byron.renderers.DungeonRenderer;
import com.byron.services.DungeonService;
import com.byron.utils.dungeon.DungeonUtils;

import static com.byron.utils.dungeon.DungeonUtils.mapToString;

public class DungeonManager implements IDungeonManager, IRenderable {

    IDungeonService dungeonService;
    DungeonRenderer dungeonRenderer;
    int[][] dungeon;

    public DungeonManager() {
        dungeon = DungeonUtils.createDungeon(64, 64, 4, 4, 3, 8, 96);
        dungeonService = new DungeonService(this);

        System.out.println("DungeonManager created");
        System.out.println(mapToString(dungeon));

//        dungeonRenderer = new DungeonRenderer(dungeon);





    }

    @Override
    public void render(float delta) {
//        dungeonRenderer.render(delta);
    }

    @Override
    public void dispose() {
        dungeonRenderer.dispose();
    }
}
