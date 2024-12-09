package com.byron.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.engine.GameResources;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.renderers.strategy.RenderPriority;
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
    int[][] bitmap;
    int[][] shitmap;
    Engine engine;


    public DungeonManager(IItemService itemService) {
        this.itemService = itemService;
        dungeon = DungeonUtils.createDungeon(MAP_WIDTH, MAP_HEIGHT, 8, 20, 2, 6, 96);
//        dungeon = DungeonUtils.createSimpleDungeon(MAP_WIDTH, MAP_HEIGHT);
        dungeonService = new DungeonService(this);
        bitmap = new int[MAP_WIDTH * 2][MAP_HEIGHT * 2];
        shitmap = new int[MAP_WIDTH * 2][MAP_HEIGHT * 2];
        engine = GameResources.get().getEngine();
        System.out.println("DungeonManager created");
//        System.out.println(mapToString(dungeon));
        createFloorsAndWalls();
        createEdges();
        printBitmap(bitmap);
        printBitmap(shitmap);
    }

    private void createEdges() {
        for (int x = 0; x < bitmap.length; x++) {
            for (int y = 0; y < bitmap[0].length; y++) {
                if (bitmap[x][y] == 0) {

                    float dungeonX = (float) x / 2;
                    float dungeonY = (float) y / 2;

                    int index = calculateIndex(x, y);

                    shitmap[x][y] = index;

                    spawnEdge(dungeonX, dungeonY, index);
                }
            }
        }
    }

    private void spawnEdge(float dungeonX, float dungeonY, int index) {

        Entity entity = new Entity();
        Sprite sprite = SpriteFactory.getSprite("building/dungeon-wall", index);

        entity.add(new RenderComponent(sprite, RenderPriority.BACKGROUND));
        entity.add(new PositionComponent(new Vector2(dungeonX, dungeonY)));
        engine.addEntity(entity);
    }

    private void createFloorsAndWalls() {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                if (dungeon[x][y] == DungeonUtils.TILE_FLOOR) {
                    spawn("stone-floor", x, y);
                    addToBitmap(x, y);

                    if (y < MAP_HEIGHT - 1 && dungeon[x][y + 1] == DungeonUtils.TILE_WALL) {

                        if (x < MAP_WIDTH - 1 && dungeon[x + 1][y] != DungeonUtils.TILE_FLOOR || dungeon[x + 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                            spawn("stone-wall-right", x, y + 1);
                            addToBitmap(x, y + 1);
                            addToBitmap(x, y + 2);

                        } else if (y > 0 && x > 0 && dungeon[x - 1][y] != DungeonUtils.TILE_FLOOR || dungeon[x - 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                            spawn("stone-wall-left", x, y + 1);
                            addToBitmap(x, y + 1);
                            addToBitmap(x, y + 2);

                        } else {
                            addToBitmap(x, y + 1);
                            addToBitmap(x, y + 2);
                            spawn("stone-wall", x, y + 1);
                        }
                    }
                }
            }
        }
    }

    private void addToBitmap(int x, int y) {
        int bx = x * 2;
        int by = y * 2;

        // Fill the 2x2 grid:
        bitmap[bx][by] = 1;     // Bottom-left
        bitmap[bx + 1][by] = 1;   // Bottom-right
        bitmap[bx][by + 1] = 1;   // Top-left
        bitmap[bx + 1][by + 1] = 1; // Top-right
    }

    public int calculateIndex(int x, int y) {
        if (!isValidPosition(x, y)) return 0;

        // Get values of neighboring cells (0 or 1)
        int nw = isValidPosition(x - 1, y + 1) ? bitmap[x - 1][y + 1] : 0; // Northwest (8)
        int ne = isValidPosition(x + 1, y + 1) ? bitmap[x + 1][y + 1] : 0; // Northeast (1)
        int sw = isValidPosition(x - 1, y - 1) ? bitmap[x - 1][y - 1] : 0; // Southwest (4)
        int se = isValidPosition(x + 1, y - 1) ? bitmap[x + 1][y - 1] : 0; // Southeast (2)

        // Calculate index with new bit positions:
        // NW(8) + SW(4) + SE(2) + NE(1)
        return (nw << 3) | (sw << 2) | (se << 1) | ne;
    }


    // Add this helper method to check bounds
    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < bitmap.length && y >= 0 && y < bitmap[0].length;
    }

    private void printBitmap(int[][] bitmap) {
        System.out.println("Bitmap array contents (0,0 at bottom left):");
        // Start from max Y (top) and work down
        for (int y = bitmap[0].length - 1; y >= 0; y--) {
            // Add row number for debugging
            System.out.printf("%2d |", y);
            // Print each x value in the row
            for (int x = 0; x < bitmap.length; x++) {
                System.out.print(bitmap[x][y] + " ");
            }
            System.out.println();
        }
        // Print x-axis numbers
        System.out.print("   ");
        for (int x = 0; x < bitmap.length; x++) {
            System.out.printf("%2d", x);
        }
        System.out.println("\n");
    }


    private void spawn(String item, int x, int y) {
        itemService.spawnItem(
            itemService.getItem(item).build(),
            new GridPoint2(x, y)
        );
    }
}


