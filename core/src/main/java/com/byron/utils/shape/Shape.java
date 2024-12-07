package com.byron.utils.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.byron.models.status.Direction;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Shape {
    protected float x;
    protected float y;
    protected ShapeRenderer.ShapeType shapeType = ShapeRenderer.ShapeType.Line;
    protected Color color = new Color(1f, 0f, 0f, 0.5f);
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void translate(Direction direction, float offset) {
        switch(direction){
            case UP:
                this.y += offset;
                break;
            case DOWN:
                this.y -= offset;
                break;
            case RIGHT:
                this.x += offset;
                break;
            case LEFT:
                this.x -= offset;
                break;
        }
    }

    public ShapeRenderer.ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeRenderer.ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void setPosition(Vector2 position);

    public abstract boolean contains(float x, float y);

    public abstract void render(ShapeRenderer shapeRenderer);
    public abstract void render(ShapeRenderer shapeRenderer, Vector2 position);
    public abstract void render(ShapeRenderer shapeRenderer, float x, float y);

}
