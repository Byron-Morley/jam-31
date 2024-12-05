package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.byron.models.physics.Body;

public class BodyComponent implements Component {

    public Body body;
    public BodyComponent(Body body) {
        this.body = body;
    }
}
