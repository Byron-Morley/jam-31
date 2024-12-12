package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class HUDProgressBarComponent implements Component {

    public final float xPortion, yPortion, wPortion, hPortion;
    public final Rectangle rectangle;
    public final Color color;
    public float progress;

    public HUDProgressBarComponent(float xPortion, float yPortion, float wPortion, float hPortion, Color color) {
        this.xPortion = xPortion;
        this.yPortion = yPortion;
        this.wPortion = wPortion;
        this.hPortion = hPortion;
        rectangle = new Rectangle(0f, 0f, 5f, 5f);
        this.color = new Color(color);
    }

    public HUDProgressBarComponent(float xPortion, float yPortion, float wPortion, float hPortion) {
        this(xPortion, yPortion, wPortion, hPortion, new Color(1f, 1f, 1f, 1f));
    }
}