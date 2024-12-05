package com.byron.renderers.strategy;

import com.badlogic.gdx.math.Vector2;
import com.byron.models.status.Direction;

public class DefaultRenderPositionStrategy implements RenderPositionStrategy {
    @Override
    public Vector2 process(float x, float y) {
        return new Vector2(x, y);
    }

    @Override
    public Vector2 process(float x, float y, Direction direction) {
        return new Vector2(x, y);
    }
}
