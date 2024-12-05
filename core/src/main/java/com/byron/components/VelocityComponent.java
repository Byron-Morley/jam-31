package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    private final static float DEFAULT_MAX = 2f;

    public float max;
    public Vector2 velocity;
    public Vector2 acceleration;
    public Vector2 speed;

    public VelocityComponent(float velocityX, float velocityY) {
        this.velocity = new Vector2(velocityX, velocityY);
        this.acceleration = new Vector2();
        this.max = DEFAULT_MAX;
        this.speed = new Vector2();
    }

    public VelocityComponent(Vector2 velocity) {
        this.velocity = velocity;
        this.acceleration = new Vector2();
        this.max = DEFAULT_MAX;
        this.speed = new Vector2();
    }
}
