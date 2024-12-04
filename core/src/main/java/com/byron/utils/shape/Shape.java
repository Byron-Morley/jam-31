package com.byron.utils.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Shape {
    public float x;
    public float y;
    private Vector2 velocity = new Vector2(Vector2.Zero);
    public boolean attached;
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

    public void setExactPosition(Vector2 position) {
        this.setX(position.x);
        this.setY(position.y);
    }

    public abstract void setPosition(Vector2 position);

    public abstract boolean contains(float x, float y);

    public abstract void render(ShapeRenderer shapeRenderer);

    public abstract void render(ShapeRenderer shapeRenderer, Vector2 position);

    public abstract void render(ShapeRenderer shapeRenderer, float x, float y);

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        if (attached) this.setColor(Color.YELLOW);
        this.attached = attached;
    }

    public Shape withVelocity(Vector2 velocity) {
        this.velocity = velocity;
        return this;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
