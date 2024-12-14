package com.byron.constants;

import static com.badlogic.gdx.graphics.Color.ORANGE;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.graphics.Color;

/**
 * I envision many constants will be used throughout the game, let's have them in places like this so they don't
 * try to kill our eyes when looking at other classes
 */
public class GeneralConstants {

    public static final float AGENT_SPEED = 5f;
    public static final Color LIGHT_COLOR = ORANGE.cpy().lerp(WHITE, 0.5f);
}