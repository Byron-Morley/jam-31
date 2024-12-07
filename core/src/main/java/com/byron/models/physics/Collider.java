package com.mygdx.game.dto.physics;

public interface Collider {
    float getX();
    float getY();
    float getWidth();
    float getHeight();
    float getBottom();
    float getTop();
    float getLeft();
    float getRight();
    float getPreviousX();
    float getPreviousY();
}
