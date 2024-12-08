package com.byron.utils;

public class Config {
    public static boolean DEBUG = true;
    public final static float VIEWPORT_WIDTH_IN_METERS = 18;
    public static final float SCREEN_WIDTH = 640;
    public static final float SCREEN_HEIGHT = 480;
    public static final float PX_PER_METER = 32f;
    public static final float METERS_PER_PX = 1f / PX_PER_METER;
    public static final float VIEWPORT_WIDTH = SCREEN_WIDTH / PX_PER_METER;  // ~20 meters
    public static final float VIEWPORT_HEIGHT = SCREEN_HEIGHT / PX_PER_METER; // ~15 meters

}
