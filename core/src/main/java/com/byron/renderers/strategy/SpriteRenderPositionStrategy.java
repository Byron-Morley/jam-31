package com.byron.renderers.strategy;

import com.badlogic.gdx.math.Vector2;
import com.byron.models.status.Direction;

public class SpriteRenderPositionStrategy implements RenderPositionStrategy {
    private float leftOffset = 0;
    private float rightOffset = 0;

    public SpriteRenderPositionStrategy(float leftOffset, float rightOffset) {
        this.leftOffset = leftOffset;
        this.rightOffset = rightOffset;
    }

    @Override
    public Vector2 process(float x, float y) {
        return new Vector2(x, y);
    }

    public Vector2 process(float x, float y, Direction direction) {
        if (direction == Direction.RIGHT) {
            return new Vector2(x + leftOffset, y);
        } else if (direction == Direction.LEFT) {
            return new Vector2(x + rightOffset, y);
        }
        return new Vector2(x, y);
    }
}
