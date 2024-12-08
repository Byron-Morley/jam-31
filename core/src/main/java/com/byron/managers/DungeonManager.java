package com.byron.managers;

import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.renderers.DungeonRenderer;
import com.byron.services.DungeonService;
import com.byron.utils.dungeon.DungeonUtils;

import static com.byron.utils.dungeon.DungeonUtils.mapToString;

public class DungeonManager implements IDungeonManager {
    private final int TILE_SCALE = 1;
    IDungeonService dungeonService;
    IItemService itemService;
    int[][] dungeon;

    public DungeonManager(IItemService itemService) {
        this.itemService = itemService;
        dungeon = DungeonUtils.createDungeon(64, 64, 4, 4, 3, 8, 96);
        dungeonService = new DungeonService(this);

        System.out.println("DungeonManager created");
        System.out.println(mapToString(dungeon));
        createDungeonEntities();
    }

    private void createDungeonEntities() {
        for (int y = 0; y < dungeon.length; y++) {
            for (int x = 0; x < dungeon[y].length; x++) {
                if (dungeon[y][x] == 1) {
                    for (int subY = 0; subY < TILE_SCALE; subY++) {
                        for (int subX = 0; subX < TILE_SCALE; subX++) {
                            itemService.spawnItem(
                                itemService.getItem("stone-floor").build(),
                                new GridPoint2(x * TILE_SCALE + subX, y * TILE_SCALE + subY)
                            );
                        }
                    }
                }
            }
        }
    }
}
