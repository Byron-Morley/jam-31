package com.byron.interfaces;


public interface ICameraManager {
    void render(float delta);
    void resize(int width, int height);
    void setPosition(float x, float y);
    ICameraService getCameraService();
}
