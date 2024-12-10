package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {

    public boolean overridePhysicsSystem;
    public final Vector2 position;

    public PositionComponent(float x, float y) {
        overridePhysicsSystem = true;
        position = new Vector2(x, y);
    }

    public PositionComponent(Vector2 position) {
        this(position.x, position.y);
    }

    public PositionComponent() {
        this(0f, 0f);
    }

    public PositionComponent(PositionComponent positionComponent) {
        this(positionComponent.position);
        overridePhysicsSystem = positionComponent.overridePhysicsSystem;
    }

    public void setPosition(Vector2 position) {
        overridePhysicsSystem = true;
        this.position.set(position);
    }

    public PositionComponent(GridPoint2 v) {
        this(v.x, v.y);
    }
}