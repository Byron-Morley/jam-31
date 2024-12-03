package com.byron.renderers.strategy;

import com.badlogic.gdx.math.Vector2;

public interface RenderPositionStrategy {
    Vector2 process(float x, float y);
}
