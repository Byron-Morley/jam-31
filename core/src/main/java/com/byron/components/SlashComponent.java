package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SlashComponent implements Component {

    public final Vector2 start = new Vector2(), current = new Vector2(), end = new Vector2();
    public float progress;

    public SlashComponent(float startX, float startY, float endX, float endY) {
        start.set(startX, startY);
        current.set(start);
        end.set(endX, endY);
    }

    public SlashComponent(Vector2 start, Vector2 end) {
        this(start.x, start.y, end.x, end.y);
    }
}