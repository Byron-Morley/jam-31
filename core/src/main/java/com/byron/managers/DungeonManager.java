package com.byron.managers;

import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.renderers.DungeonRenderer;
import com.byron.services.DungeonService;
import com.byron.utils.dungeon.DungeonUtils;

import static com.byron.utils.Config.MAP_HEIGHT;
import static com.byron.utils.Config.MAP_WIDTH;
import static com.byron.utils.dungeon.DungeonUtils.mapToString;

public class DungeonManager implements IDungeonManager {
    private final int TILE_SCALE = 1;
    IDungeonService dungeonService;
    IItemService itemService;
    int[][] dungeon;

    public DungeonManager(IItemService itemService) {
        this.itemService = itemService;
        dungeon = DungeonUtils.createDungeon(MAP_WIDTH, MAP_HEIGHT, 4, 4, 3, 8, 96);
        dungeonService = new DungeonService(this);

        System.out.println("DungeonManager created");
//        System.out.println(mapToString(dungeon));
        createDungeonEntities();
    }

    private void createDungeonEntities() {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                // Spawn floors
                if (dungeon[x][y] == DungeonUtils.TILE_FLOOR) {
                    spawn("stone-floor", x, y);

                    // Spawn wall above floor tiles
                    if (y < MAP_HEIGHT - 1 && dungeon[x][y + 1] == DungeonUtils.TILE_WALL) {

                        if (x < MAP_WIDTH - 1 && dungeon[x + 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                            spawn("stone-wall-right", x, y + 1);
                        } else if (y > 0 && x > 0 && dungeon[x - 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                            spawn("stone-wall-left", x, y + 1);
                        } else {
                            spawn("stone-wall", x, y + 1);
                        }
                    }
                } else if (dungeon[x][y] == DungeonUtils.TILE_EMPTY) {
//                    spawn("stone-wall", x, y);
                }
            }
        }
    }

    public int calculateIndex(int dx, int dy) {
//        int topright = calculateWeight(dx + 1, dy + 1, 0);
//        int bottomright = calculateWeight(dx + 1, dy, 1);
//        int bottomleft = calculateWeight(dx, dy, 2);
//        int topleft = calculateWeight(dx, dy + 1, 3);

//        int index = bottomleft + bottomright + topleft + topright;

//        return index;
        return 0;
    }


    private void spawn(String item, int x, int y) {
        itemService.spawnItem(
                itemService.getItem(item).build(),
                new GridPoint2(x, y)
        );
    }
}


