package com.byron.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static com.byron.utils.Config.PX_PER_METER;

public class Dimensions {
    public static final float toMeters(int px) {
        return (float) px / PX_PER_METER;
    }

    public static final float toMeters(float px) {
        return px / PX_PER_METER;
    }

    public static final int toPixels(float meters) {
        return (int) (meters * PX_PER_METER);
    }

    public static final Vector2 calculateGlobalPositionInPixelsToMetersRelativeToCenter(float x, float y) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float relativeX = x - width / 2f;
        float relativeY = y - height / 2f;

        return new Vector2(toMeters(relativeX), toMeters(relativeY));
    }
}
