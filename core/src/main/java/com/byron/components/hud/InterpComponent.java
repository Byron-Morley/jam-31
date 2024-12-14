package com.byron.components.hud;

import com.badlogic.gdx.math.Interpolation;

public abstract class InterpComponent<T> {

    public final Interpolation interpolation;
    public final T start, end;
    public T current;
    public float progress;

    public InterpComponent(Interpolation interpolation, T start, T current, T end) {
        this.interpolation = interpolation;
        this.start = start;
        this.current = current;
        this.end = end;
    }

    public InterpComponent(T start, T current, T end) {
        this(Interpolation.linear, start, current, end);
    }
}