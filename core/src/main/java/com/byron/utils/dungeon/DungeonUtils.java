/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.byron.utils.dungeon;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Random;

/**
 * Utility class to generate flat and hierarchical random dungeons.
 *
 * @author davebaol
 */
public final class DungeonUtils {

    public static void main(String[] args) {
        System.out.println("DungeonUtils.main()");
//		int mapSizeX = 60;
//		int mapSizeY = 100;
//		int map[][] = DungeonUtils.generate(mapSizeX, mapSizeY, MathUtils.random(70, 120), 3, 15, 100);
//		System.out.println(mapToString(map));

        int mapSizeX = 75;
        int mapSizeY = 125;
        int map[][] = createDungeon(70, 120, 15, 67, 2, 10, 100, 20).getMap();
        System.out.println(mapToString(map));
    }

    public static int[][] createSimpleDungeon(int width, int height) {
        int[][] dungeon = new int[width][height];

        // Fill with walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                dungeon[x][y] = TILE_WALL;
            }
        }

        // Create left room (4x4)
        for (int x = 1; x < 5; x++) {
            for (int y = 1; y < 5; y++) {
                dungeon[x][y] = TILE_FLOOR;
            }
        }

        // Create right room (4x4)
        for (int x = 7; x < 11; x++) {
            for (int y = 1; y < 5; y++) {
                dungeon[x][y] = TILE_FLOOR;
            }
        }

        // Create corridor
        for (int x = 5; x < 7; x++) {
            dungeon[x][2] = TILE_FLOOR;
        }

        return dungeon;
    }


    public static Dungeon createDungeon(
            int mapSizeX, int mapSizeY,
            int roomCountMin, int roomCountMax,
            int roomMinSize, int roomMaxSize,
            int squashIterations, int seed
    ) {
        int mulitplier = 2;


        int map[][] = new int[mapSizeX / mulitplier][mapSizeY / mulitplier];
        int submapSizeX = mapSizeX / 2 / mulitplier;
        int submapSizeY = mapSizeY / 3 / mulitplier;

        Random random = new Random(seed);
        Array<Room> rooms = new Array<>();

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {

                Dungeon subDungeon = DungeonUtils.generate(
                        submapSizeX, submapSizeY,
                        random.nextInt(roomCountMax - roomCountMin + 1) + roomCountMin,
                        roomMinSize,
                        roomMaxSize,
                        squashIterations,
                        false,
                        random.nextInt());

                rooms.addAll(addRelativeRoomPositions(subDungeon.getRooms(), x, y, submapSizeX, submapSizeY));
                int submap[][] = subDungeon.getMap();

                for (int x0 = 0; x0 < submapSizeX; x0++) {
                    for (int y0 = 0; y0 < submapSizeY; y0++) {
                        if (submap[x0][y0] != TILE_EMPTY)
                            map[x * submapSizeX + x0][y * submapSizeY + y0] = submap[x0][y0];
                    }
                }
            }
        }

        // Generate corridors to connect buildings
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                // Generates 1 or 2 corridors per building
                boolean corridor1 = y < 3 - 1;
                boolean corridor2 = x < 2 - 1;
                if (corridor1 && corridor2 && random.nextBoolean()) {
                    if (random.nextBoolean())
                        corridor1 = false;
                    else
                        corridor2 = false;
                }
                if (corridor1) {
                    int min = x * submapSizeX + 1;
                    int max = x * submapSizeX + submapSizeX / 2;
                    int x0 = random.nextInt(max - min + 1) + min;
                    int y0 = y * submapSizeY + 1;
                    for (int i = submapSizeY + 5; i > 0; i--) {
                        if (i < submapSizeY && map[x0][y0 + i] == TILE_FLOOR)
                            break;
                        map[x0][y0 + i] = TILE_FLOOR;
                    }
                }
                if (corridor2) {
                    int x1 = x * submapSizeX + 1;
                    int min = y * submapSizeY + 1;
                    int max = y * submapSizeY + submapSizeY / 2;
                    int y1 = random.nextInt(max - min + 1) + min;
                    for (int i = submapSizeX + 5; i > 0; i--) {
                        if (i < submapSizeX && map[x1 + i][y1] == TILE_FLOOR)
                            break;
                        map[x1 + i][y1] = TILE_FLOOR;
                    }
                }
            }
        }
        addMissingWalls(map);
        int padding = 0;
        int[][] enlargedMap = enLargeMap(map, mulitplier, padding);
        int[][] fixedMap = fixMap(enlargedMap);
        return new Dungeon(fixedMap, correctRooms(rooms, mulitplier, padding));
    }

    private static int[][] fixMap(int[][] map) {
        int width = map.length;
        int height = map[0].length;

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 2; y++) {
                // Check for vertical pattern: floor-wall-wall-floor
                if (map[x][y] == TILE_FLOOR &&
                        map[x][y + 1] == TILE_WALL &&
                        map[x][y + 2] == TILE_WALL &&
                        map[x][y + 3] == TILE_FLOOR) {
                    map[x][y + 1] = TILE_FLOOR;
                    map[x][y + 2] = TILE_FLOOR;
                }
            }
        }
        return map;
    }

    private static Array<Room> addRelativeRoomPositions(Array<Room> rooms, int x, int y, int submapSizeX, int submapSizeY) {
        Array<Room> correctedRooms = new Array<>();
        for (Room room : rooms) {
            int x0 = room.x + (x * submapSizeX);
            int y0 = room.y + (y * submapSizeY);
            int width = room.width;
            int height = room.height;
            correctedRooms.add(new Room(x0, y0, width, height));
        }
        return correctedRooms;
    }

    private static Array<Room> correctRooms(Array<Room> rooms, int mulitplier, int padding) {
        Array<Room> correctedRooms = new Array<>();
        for (Room room : rooms) {
            int x = room.x * mulitplier + padding;
            int y = room.y * mulitplier + padding;
            int width = room.width * mulitplier;
            int height = room.height * mulitplier;

            correctedRooms.add(new Room(x, y, width, height));
        }
        return correctedRooms;

    }

    private static int[][] enLargeMap(int[][] originalMap, int scale, int padding) {

        int newWidth = (originalMap.length * scale) + (padding * 2);
        int newHeight = (originalMap[0].length * scale) + (padding * 2);
        int[][] enlargedMap = new int[newWidth][newHeight];

        // Fill entire array with TILE_EMPTY (0) first
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                enlargedMap[x][y] = TILE_EMPTY;
            }
        }

        // Copy and scale original map contents, offset by padding
        for (int x = 0; x < originalMap.length; x++) {
            for (int y = 0; y < originalMap[0].length; y++) {
                for (int dx = 0; dx < scale; dx++) {
                    for (int dy = 0; dy < scale; dy++) {
                        enlargedMap[padding + (x * scale) + dx][padding + (y * scale) + dy] = originalMap[x][y];
                    }
                }
            }
        }
        return enlargedMap;
    }


    public static final int TILE_EMPTY = 0;
    public static final int TILE_FLOOR = 1;
    public static final int TILE_WALL = 2;

    private DungeonUtils() {
    }

    public static Dungeon generate(int mapSizeX, int mapSizeY, int roomCount, int roomMinSize, int roomMaxSize, int squashIterations, int seed) {
        return generate(mapSizeX, mapSizeY, roomCount, roomMinSize, roomMaxSize, squashIterations, true, seed);
    }

    public static Dungeon generate(
            int mapSizeX,
            int mapSizeY,
            int roomCount,
            int roomMinSize,
            int roomMaxSize,
            int squashIterations,
            boolean addMissingWalls,
            int seed
    ) {
        int[][] map = new int[mapSizeX][mapSizeY];
        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                map[x][y] = TILE_EMPTY;
            }
        }
        Random random = new Random(seed);
        // Generate random rooms and make sure they don't collide each other.
        // Also decrease the room width and height by 1 so as to make sure that no two rooms
        // are directly next to one another (making one big room).
        Array<Room> rooms = new Array<Room>();
        for (int k = 0; k < squashIterations; k++) {
//			System.out.println("k:" + k + ", rooms:" + rooms.size);
            int failures = 0;
            for (int i = rooms.size; i < roomCount; i++) {
                if (failures > 1000) {
                    break;
                }

                Room room = new Room();

                room.x = random.nextInt(mapSizeX - roomMaxSize - 1 - 1 + 1) + 1;
                room.y = random.nextInt(mapSizeY - roomMaxSize - 1 - 1 + 1) + 1;
                room.width = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;
                room.height = random.nextInt(roomMaxSize - roomMinSize + 1) + roomMinSize;

                if (collides(rooms, room)) {
                    i--;
                    failures++;
                    continue;
                }
                room.width--;
                room.height--;

                rooms.add(room);
            }

            // Move all the rooms closer to one another to get rid of some large gaps
            squashRooms(rooms);
        }
        roomCount = rooms.size;

        System.out.println("roomCount:" + roomCount);
        // Build corridors between rooms that are near to one another.
        // We choose a random point in each room and then move the second point towards the
        // first one (in the while loop).
        for (int i = 0; i < roomCount; i++) {
            Room roomA = rooms.get(i);
            Room roomB = findClosestRoom(rooms, roomA);

            int pointAx = random.nextInt(roomA.width + 1) + roomA.x;
            int pointAy = random.nextInt(roomA.height + 1) + roomA.y;
            int pointBx = random.nextInt(roomB.width + 1) + roomB.x;
            int pointBy = random.nextInt(roomB.height + 1) + roomB.y;

            while ((pointBx != pointAx) || (pointBy != pointAy)) {
                if (pointBx != pointAx) {
                    if (pointBx > pointAx)
                        pointBx--;
                    else
                        pointBx++;
                } else if (pointBy != pointAy) {
                    if (pointBy > pointAy)
                        pointBy--;
                    else
                        pointBy++;
                }

                map[pointBx][pointBy] = TILE_FLOOR;
            }
        }

        // Iterate through all the rooms and set the tile to FLOOR for every tile within a room
        for (int i = 0; i < roomCount; i++) {
            Room room = rooms.get(i);
            for (int x = room.x; x < room.x + room.width; x++) {
                for (int y = room.y; y < room.y + room.height; y++) {
                    map[x][y] = TILE_FLOOR;
                }
            }
        }

        // Convert to a wall tile any empty tile touching a floor tile
        if (addMissingWalls)
            addMissingWalls(map);

        return new Dungeon(map, rooms);
    }

    // Iterates through all the tiles in the map and if it finds a tile that is a FLOOR
    // we check all the surrounding tiles for empty values. If we find an empty tile (that
    // touches the floor) we build a WALL.
    public static void addMissingWalls(int[][] map) {
        int mapSizeX = map.length;
        int mapSizeY = map[0].length;
        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                if (map[x][y] == TILE_FLOOR) {
                    for (int xx = x - 1; xx <= x + 1; xx++) {
                        for (int yy = y - 1; yy <= y + 1; yy++) {
                            if (map[xx][yy] == TILE_EMPTY) map[xx][yy] = TILE_WALL;
                        }
                    }
                }
            }
        }
    }

    private static boolean collides(Array<Room> rooms, Room room) {
        return collides(rooms, room, -1);
    }

    private static boolean collides(Array<Room> rooms, Room room, int ignore) {
        for (int i = 0; i < rooms.size; i++) {
            if (i == ignore) continue;
            Room check = rooms.get(i);
            if (!((room.x + room.width < check.x) || (room.x > check.x + check.width) || (room.y + room.height < check.y) || (room.y > check.y
                    + check.height))) return true;
        }

        return false;
    }

    private static void squashRooms(Array<Room> rooms) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < rooms.size; j++) {
                Room room = rooms.get(j);
                while (true) {
                    int oldX = room.x;
                    int oldY = room.y;
                    if (room.x > 1) room.x--;
                    if (room.y > 1) room.y--;
                    if ((room.x == 1) && (room.y == 1)) break;
                    if (collides(rooms, room, j)) {
                        room.x = oldX;
                        room.y = oldY;
                        break;
                    }
                }
            }
        }
    }

    private static Room findClosestRoom(Array<Room> rooms, Room room) {
        float midX = room.x + (room.width / 2f);
        float midY = room.y + (room.height / 2f);
        Room closest = null;
        float closestDistance = Float.POSITIVE_INFINITY;
        for (int i = 0; i < rooms.size; i++) {
            Room check = rooms.get(i);
            if (check == room) continue;
            float checkMidX = check.x + (check.width / 2f);
            float checkMidY = check.y + (check.height / 2f);
            float distance = Math.min(Math.abs(midX - checkMidX) - (room.width / 2f) - (check.width / 2f), Math.abs(midY - checkMidY)
                    - (room.height / 2f) - (check.height / 2f));
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = check;
            }
        }
        return closest;
    }

    public static String mapToString(int[][] map) {
        StringBuilder sb = new StringBuilder(map.length * (map[0].length + 1)); // +1 is due to the new line char
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                switch (map[x][y]) {
                    case TILE_EMPTY:
                        sb.append(' ');
                        break;
                    case TILE_FLOOR:
                        sb.append('.');
                        break;
                    case TILE_WALL:
                        sb.append('#');
                        break;
                    default:
                        sb.append('?');
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
