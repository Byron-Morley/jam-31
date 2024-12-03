package com.byron.renderers.strategy;

import com.badlogic.gdx.math.Vector2;

public class DefaultRenderPositionStrategy implements RenderPositionStrategy {
    @Override
    public Vector2 process(float x, float y) {
        return new Vector2(x, y);
    }
}
