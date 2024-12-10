package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class DestinationComponent implements Component {

    public final Vector2 destination = new Vector2();

    public DestinationComponent() {
    }

    public DestinationComponent(float x, float y) {
        destination.set(x, y);
    }

    public DestinationComponent(Vector2 destination) {
        this.destination.set(destination);
    }
}