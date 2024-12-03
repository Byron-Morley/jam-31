package com.byron.engine;

public class GameState {

    private static boolean isPaused = false;
    private static float timeScale = 1.0f;

    public static void setTimeScale(float scale) {
        timeScale = scale;
        System.out.println("timeScale: " + timeScale);
    }

    public static float getTimeScale() {
        return timeScale;
    }

    public static void togglePause() {
        isPaused = !isPaused;
        System.out.println("isPaused: " + isPaused);
    }

    public static boolean isPaused() {
        return isPaused;
    }
}
