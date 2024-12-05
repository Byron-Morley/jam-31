package com.byron.utils;

public class Config {
    public static boolean DEBUG = true;
    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;
    public final static float VIEWPORT_WIDTH_IN_METERS = 24;
    public final static int WORLD_WIDTH = 30;
    public final static int WORLD_HEIGHT = 30;
    public static final float PX_PER_METER = 32f;
    public static final float METERS_PER_PX = 1 / 32f;
    public static final float PLAYER_START_X = 1;
    public static final float PLAYER_START_Y = 10;
    public static final float PLAYER_SPRITE_WIDTH = 32;
    public static final float PLAYER_SPRITE_HEIGHT = 32;
    public static final float PLAYER_CELL_WIDTH = PLAYER_SPRITE_WIDTH / PX_PER_METER;
    public static final float PLAYER_CELL_HEIGHT = PLAYER_SPRITE_HEIGHT / PX_PER_METER;
    public static final float GRAVITY = -2f;
    public static final float MAX_VELOCITY = 10f;
    public static final long LONG_JUMP_PRESS = 150l;
    public static final float ACCELERATION = 5f;
    public static final float MAX_JUMP_SPEED = 30f;
    public static final float DAMP = 0.09f;
}
