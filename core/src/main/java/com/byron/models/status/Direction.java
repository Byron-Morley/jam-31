package com.byron.models.status;

import com.badlogic.gdx.math.Vector2;

public enum Direction {

    UP("UP", new Vector2(0f, 1f)),
    LEFT("LEFT", new Vector2(-1f, 0f)),
    DOWN("DOWN", new Vector2(0f, -1f)),
    RIGHT("RIGHT", new Vector2(1f, 0f)),
    UP_LEFT("UP_LEFT", new Vector2(-1f, 1f)),
    UP_RIGHT("UP_RIGHT", new Vector2(1f, 1f)),
    DOWN_LEFT("DOWN_LEFT", new Vector2(-1f, -1f)),
    DOWN_RIGHT("DOWN_RIGHT", new Vector2(1f, -1f)),
    CENTER("CENTER", new Vector2(0f, 0f));

    private final String name;
    public final Vector2 vector;

    Direction(String name, Vector2 vector) {
        this.name = name;
        this.vector = vector;
    }

    public String getName() {
        return name;
    }

    public static Direction fromString(String name) {
        if (name != null) {
            for (Direction d : Direction.values()) {
                if (name.endsWith(d.name)) {
                    return d;
                }
            }
        }

        return null;
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
}