package com.byron.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ICameraManager {
    void render(float delta);
    void resize(int width, int height);
}
