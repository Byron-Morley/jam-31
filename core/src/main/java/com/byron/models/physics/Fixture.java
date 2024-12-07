package com.mygdx.game.dto.physics;

import com.mygdx.game.utils.shape.RectangleShape;
import com.mygdx.game.utils.shape.Shape;

public class Fixture implements Collider {

    public String id;
    private Shape shape;
    private Body body;

    public Fixture(Body body, String id, Shape shape) {
        this.id = id;
        this.shape = shape;
        this.body = body;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public float getX() {
        return body.position.x + shape.getX();
    }

    @Override
    public float getY() {
        return body.position.y + shape.getY();
    }

    @Override
    public float getWidth() {
        RectangleShape rectangleShape = (RectangleShape) shape;
        return rectangleShape.getWidth();
    }

    @Override
    public float getHeight() {
        RectangleShape rectangleShape = (RectangleShape) shape;
        return rectangleShape.getHeight();
    }

    @Override
    public float getBottom() {
        return this.getY();
    }

    @Override
    public float getTop() {
        return this.getY() + getHeight();
    }

    @Override
    public float getLeft() {
        return this.getX();
    }

    @Override
    public float getRight() {
        return this.getX() + getWidth();
    }

    @Override
    public float getPreviousX() {
        return body.getPreviousX() + shape.getX();
    }

    @Override
    public float getPreviousY() {
        return body.getPreviousY() + shape.getY();
    }

}
