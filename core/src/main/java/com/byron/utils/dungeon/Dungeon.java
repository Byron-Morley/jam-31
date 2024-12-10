package com.byron.utils.dungeon;

import com.badlogic.gdx.utils.Array;


public class Dungeon{
        int[][] map;
        Array<Room> rooms;

        public Dungeon(int[][] map, Array<Room> rooms) {
            this.map = map;
            this.rooms = rooms;
        }

        public int[][] getMap() {
            return map;
        }

        public Array<Room> getRooms() {
            return rooms;
        }
    }