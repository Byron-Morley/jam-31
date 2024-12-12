package com.byron.components.hud;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class PositionInterpComponent implements Component {

    public final Interpolation interpolation;
    public final Vector2 startPosition, currentPosition, endPosition;
    public float progress;

    public PositionInterpComponent(Interpolation interpolation, Vector2 startPosition, Vector2 endPosition) {
        this.interpolation = interpolation;
        this.startPosition = new Vector2(startPosition);
        this.currentPosition = new Vector2(startPosition);
        this.endPosition = new Vector2(endPosition);
    }

    public PositionInterpComponent(Vector2 startPosition, Vector2 endPosition) {
        this(Interpolation.linear, startPosition, endPosition);
    }
}