package com.byron.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface IDungeonService {
    boolean isWalkable(int x, int y);
    boolean isWalkable(Vector2 position);
}
