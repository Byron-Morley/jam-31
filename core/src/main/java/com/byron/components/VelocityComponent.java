package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {

    Vector2 velocity;

    public VelocityComponent(Vector2 velocity) {
        this.velocity = velocity;
    }
}
