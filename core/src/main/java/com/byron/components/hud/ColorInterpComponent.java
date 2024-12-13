package com.byron.components.hud;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

public class ColorInterpComponent implements Component {

    public final Interpolation interpolation;
    public final Color startColor, currentColor, endColor;
    public float progress;

    public ColorInterpComponent(Interpolation interpolation, Color startColor, Color endColor) {
        this.interpolation = interpolation;
        this.startColor = new Color(startColor);
        this.currentColor = new Color(startColor);
        this.endColor = new Color(endColor);
    }

    public ColorInterpComponent(Color startColor, Color endColor) {
        this(Interpolation.linear, startColor, endColor);
    }
}