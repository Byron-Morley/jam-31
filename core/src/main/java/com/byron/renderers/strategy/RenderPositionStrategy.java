package com.byron.renderers.strategy;

import com.badlogic.gdx.math.Vector2;
import com.byron.models.status.Direction;

public interface RenderPositionStrategy {
    Vector2 process(float x, float y);
    Vector2 process(float x, float y, Direction direction);
}
