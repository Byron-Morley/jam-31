package com.byron.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface IDungeonService {
    boolean isWalkable(int x, int y);

    default boolean isWalkable(Vector2 position) {
        return isWalkable((int) position.x, (int) position.y);
    }
}