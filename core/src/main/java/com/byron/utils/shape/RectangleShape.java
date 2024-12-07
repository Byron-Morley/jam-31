package com.mygdx.game.utils.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public class RectangleShape extends Shape {
    private float width;
    private float height;

    public RectangleShape(float width, float height, Vector2 position) {
        this.width = width;
        this.height = height;
        setPosition(position);
    }

    public RectangleShape(float width, float height, float x, float y) {
        this.width = width;
        this.height = height;
        setX(x);
        setY(y);
    }

    public RectangleShape(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setPosition(Vector2 position) {
        setX(position.x - width / 2);
        setY(position.y - height / 2);
    }

    public void setExactPosition(Vector2 position) {
        setX(position.x);
        setY(position.y);
    }

    @Override
    public boolean contains(float x, float y) {
        return getX() <= x && x <= getX() + width && getY() <= y && y <= getY() + height;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(x, y, width, height);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, Vector2 position) {
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, float x, float y) {
        shapeRenderer.rect(x, y, width, height);
    }
}
