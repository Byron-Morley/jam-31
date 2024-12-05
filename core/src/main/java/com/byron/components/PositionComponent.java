package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public boolean overridePhysicsSystem;
    float x;
    float y;

    public PositionComponent() {}

    public PositionComponent(float x, float y) {
        overridePhysicsSystem = true;
        this.x = x;
        this.y = y;
    }

    public PositionComponent(PositionComponent positionComponent) {
        overridePhysicsSystem = true;
        this.x = positionComponent.x;
        this.y = positionComponent.y;
    }

    public PositionComponent(Vector2 v) {
        this.overridePhysicsSystem = true;
        this.x = v.x;
        this.y = v.y;
    }

    public void set(float x, float y){
        overridePhysicsSystem = true;
        this.x = x;
        this.y = y;
    }
    public void setX(float x){
        overridePhysicsSystem = true;
        this.x = x;
    }
    public void setY(float y){
        overridePhysicsSystem = true;
        this.y = y;
    }

    public void setPosition(Vector2 vector2) {
        overridePhysicsSystem = true;
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
