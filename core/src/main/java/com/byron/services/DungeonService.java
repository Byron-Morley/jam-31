package com.byron.services;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.engine.GameResources;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;
import com.byron.models.Spawn;
import com.byron.renderers.strategy.RenderPriority;
import com.byron.utils.dungeon.DungeonUtils;
import com.byron.utils.dungeon.Room;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.byron.utils.Config.MAP_HEIGHT;
import static com.byron.utils.Config.MAP_WIDTH;

public class DungeonService implements IDungeonService {

    IDungeonManager dungeonManager;
    IItemService itemService;
    Engine engine;
    private Set<GridPoint2> occupiedPositions = new HashSet<>();
    private Set<GridPoint2> wallBottom = new HashSet<>();

    private float CHANCE_OF_CORNER_PILLARS = 0.2f;

    public DungeonService(IDungeonManager dungeonManager, IItemService itemService) {
        this.dungeonManager = dungeonManager;
        this.itemService = itemService;
        engine = GameResources.get().getEngine();
        createFloorsAndWalls(dungeonManager.getDungeon());
        createEdges();
        spawnSceneryItems();
        spawnColumnItems();
        spawnSpecialItems();

//        mapOutRooms(dungeonManager.getRooms());
    }

    private void mapOutRooms(Array<Room> rooms) {
        for (Room room : rooms) {
            int x = room.getX();
            int y = room.getY();
            int width = room.getWidth();
            int height = room.getHeight();

            for (int i = x; i < x + width; i++) {
                for (int j = y; j < y + height; j++) {
                    Entity entity = new Entity();
                    entity.add(new PositionComponent(new Vector2(i, j)));
                    entity.add(new RenderComponent(SpriteFactory.getSprite("red-grid"), RenderPriority.UI));
                    engine.addEntity(entity);
                }
            }

        }
    }

    public void spawnSceneryItems() {
        List<Spawn> spawns = dungeonManager.getSpawns();
        Array<Room> rooms = dungeonManager.getRooms();

        List<Spawn> clutterSpawns = spawns.stream()
            .filter(spawn -> "clutter".equals(spawn.getType()))
            .collect(Collectors.toList());

        for (Room room : rooms) {
            // Calculate room area and divide by 10 to get number of items
            int roomArea = room.getWidth() * room.getHeight();
            int itemsToSpawn = Math.max(1, roomArea / 15); // Ensure at least 1 item

            // Try to spawn calculated number of items
            for (int i = 0; i < itemsToSpawn; i++) {
                int attempts = 0;
                int maxAttempts = 10;
                boolean placed = false;

                while (!placed && attempts < maxAttempts) {
                    int randomX = MathUtils.random(room.getX(), room.getX() + room.getWidth() - 1);
                    int randomY = MathUtils.random(room.getY(), room.getY() + room.getHeight() - 1);
                    GridPoint2 position = new GridPoint2(randomX, randomY);

                    if (!occupiedPositions.contains(position) && !clutterSpawns.isEmpty()) {
                        int randomIndex = MathUtils.random(clutterSpawns.size() - 1);
                        Spawn selectedSpawn = clutterSpawns.get(randomIndex);
                        spawn(selectedSpawn.getName(), randomX, randomY);
                        occupiedPositions.add(position);
                        placed = true;
                    }
                    attempts++;
                }
            }
        }
    }

    private void createFloorsAndWalls(int[][] map) {
        // Create a Random object for generating random numbers
        java.util.Random random = new java.util.Random();

        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                if (map[x][y] == DungeonUtils.TILE_FLOOR) {
                    // Generate random number between 0-100
                    // If number is less than 2 (2% chance), spawn broken floor
                    if (random.nextInt(100) < 2) {
                        spawn("stone-floor-broken", x, y);
                    } else if (random.nextInt(100) < 20) {
                        int floor = random.nextInt(3) + 1;
                        spawn("stone-floor-" + floor, x, y);
                    } else {
                        spawn("stone-floor-1", x, y);
                    }
                    addToBitmap(x, y);
                    addWalls(map, y, x);
                }
            }
        }
    }

    private void addWalls(int[][] map, int y, int x) {
        if (y < MAP_HEIGHT - 1 && map[x][y + 1] == DungeonUtils.TILE_WALL) {
            if (x < MAP_WIDTH - 1 && map[x + 1][y] != DungeonUtils.TILE_FLOOR || map[x + 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                spawn("stone-wall-right", x, y + 1);
                addToBitmap(x, y + 1);
                addToBitmap(x, y + 2);
            } else if (y > 0 && x > 0 && map[x - 1][y] != DungeonUtils.TILE_FLOOR || map[x - 1][y + 1] == DungeonUtils.TILE_FLOOR) {
                spawn("stone-wall-left", x, y + 1);
                addToBitmap(x, y + 1);
                addToBitmap(x, y + 2);
            } else {
                addToBitmap(x, y + 1);
                addToBitmap(x, y + 2);
                spawn("stone-wall", x, y + 1);
            }
            spawn("shade", x, y);
        }
    }

    public void spawnColumnItems() {
        List<Spawn> spawns = dungeonManager.getSpawns();
        Array<Room> rooms = dungeonManager.getRooms();

        // Filter for column type items
        List<Spawn> columnSpawns = spawns.stream()
            .filter(spawn -> "column".equals(spawn.getType()))
            .collect(Collectors.toList());

        for (Room room : rooms) {
            // Get corner positions for this room
            List<GridPoint2> corners = Arrays.asList(
                new GridPoint2(room.getX(), room.getY()),                             // bottom-left
                new GridPoint2(room.getX() + room.getWidth() - 1, room.getY()),      // bottom-right
                new GridPoint2(room.getX(), room.getY() + room.getHeight() - 1),     // top-left
                new GridPoint2(room.getX() + room.getWidth() - 1, room.getY() + room.getHeight() - 1)  // top-right
            );

            // For each corner, 50% chance to spawn
            for (GridPoint2 corner : corners) {
                if (MathUtils.randomBoolean(CHANCE_OF_CORNER_PILLARS) && !occupiedPositions.contains(corner)) {
                    if (!columnSpawns.isEmpty()) {
                        int randomIndex = MathUtils.random(columnSpawns.size() - 1);
                        Spawn selectedSpawn = columnSpawns.get(randomIndex);
                        spawn(selectedSpawn.getName(), corner.x, corner.y);
                        occupiedPositions.add(corner);
                    }
                }
            }
        }
    }

    public void spawnSpecialItems() {
    List<Spawn> spawns = dungeonManager.getSpawns();
    Array<Room> rooms = dungeonManager.getRooms();

    // Filter for special type items
    List<Spawn> specialSpawns = spawns.stream()
        .filter(spawn -> "special".equals(spawn.getType()))
        .collect(Collectors.toList());

    // Get a random room to place the special item
    if (!rooms.isEmpty() && !specialSpawns.isEmpty()) {
        Room selectedRoom = rooms.get(MathUtils.random(rooms.size - 1));

        // Try to place in center of room first
        int centerX = selectedRoom.getX() + selectedRoom.getWidth() / 2;
        int centerY = selectedRoom.getY() + selectedRoom.getHeight() / 2;
        GridPoint2 position = new GridPoint2(centerX, centerY);

        // If center is occupied, try random positions in room
        if (occupiedPositions.contains(position)) {
            int attempts = 0;
            int maxAttempts = 10;
            boolean placed = false;

            while (!placed && attempts < maxAttempts) {
                int randomX = MathUtils.random(selectedRoom.getX(), selectedRoom.getX() + selectedRoom.getWidth() - 1);
                int randomY = MathUtils.random(selectedRoom.getY(), selectedRoom.getY() + selectedRoom.getHeight() - 1);
                position = new GridPoint2(randomX, randomY);

                if (!occupiedPositions.contains(position)) {
                    placed = true;
                }
                attempts++;
            }
        }

        // Spawn the special item
        int randomIndex = MathUtils.random(specialSpawns.size() - 1);
        Spawn selectedSpawn = specialSpawns.get(randomIndex);
        spawn(selectedSpawn.getName(), position.x, position.y);
        occupiedPositions.add(position);
    }
}



    private void createEdges() {
        int[][] bitmap = dungeonManager.getBitmap();
        for (int x = 0; x < bitmap.length; x++) {
            for (int y = 0; y < bitmap[0].length; y++) {
                if (bitmap[x][y] == 0) {

                    float dungeonX = (float) x / 2;
                    float dungeonY = (float) y / 2;

                    int index = calculateIndex(x, y);

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

    private void addToBitmap(int x, int y) {
        int bx = x * 2;
        int by = y * 2;

        int[][] bitmap = dungeonManager.getBitmap();

        // Fill the 2x2 grid:
        bitmap[bx][by] = 1;     // Bottom-left
        bitmap[bx + 1][by] = 1;   // Bottom-right
        bitmap[bx][by + 1] = 1;   // Top-left
        bitmap[bx + 1][by + 1] = 1; // Top-right
    }

    public int calculateIndex(int x, int y) {
        int[][] bitmap = dungeonManager.getBitmap();
        if (!isValidPosition(x, y)) return 0;

        // Get values of neighboring cells (0 or 1)
        int nw = isValidPosition(x - 1, y + 1) ? bitmap[x - 1][y + 1] : 0; // Northwest (8)
        int ne = isValidPosition(x + 1, y + 1) ? bitmap[x + 1][y + 1] : 0; // Northeast (1)
        int sw = isValidPosition(x - 1, y - 1) ? bitmap[x - 1][y - 1] : 0; // Southwest (4)
        int se = isValidPosition(x + 1, y - 1) ? bitmap[x + 1][y - 1] : 0; // Southeast (2)

        // Calculate index with new bit positions:
        // NW(8) + SW(4) + SE(2) + NE(1)

        int index = (nw << 3) | (sw << 2) | (se << 1) | ne;

        if (index == 8) {
            if (isValidPosition(x, y + 1) && bitmap[x][y + 1] == 1) {
                index = 9;
            } else if (isValidPosition(x - 1, y) && bitmap[x - 1][y] == 1) {
                index = 12;
            }
        } else if (index == 4) {
            if (isValidPosition(x - 1, y) && bitmap[x - 1][y] == 1) {
                index = 12;
            } else if (isValidPosition(x, y - 1) && bitmap[x][y - 1] == 1) {
                index = 6;
            }
        } else if (index == 2) {
            if (isValidPosition(x, y - 1) && bitmap[x][y - 1] == 1) {
                index = 6;
            } else if (isValidPosition(x + 1, y) && bitmap[x + 1][y] == 1) {
                index = 3;
            }
        } else if (index == 1) {
            if (isValidPosition(x + 1, y) && bitmap[x + 1][y] == 1) {
                index = 3;
            } else if (isValidPosition(x, y + 1) && bitmap[x][y + 1] == 1) {
                index = 9;
            }
        }

        return index;
    }

    // Add this helper method to check bounds
    private boolean isValidPosition(int x, int y) {
        int[][] bitmap = dungeonManager.getBitmap();
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

    public boolean isWalkable(int x, int y) {
        return dungeonManager.getDungeon()[x][y] == DungeonUtils.TILE_FLOOR;
    }

    @Override
    public boolean isWalkable(Vector2 position) {
        return isWalkable((int) position.x, (int) position.y);
    }
}
