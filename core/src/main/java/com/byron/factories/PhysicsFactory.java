package com.byron.factories;

import com.badlogic.gdx.math.Vector2;
import com.byron.models.physics.Body;
import com.byron.utils.shape.RectangleShape;
import com.byron.utils.shape.Shape;

public class PhysicsFactory {
    private static PhysicsFactory instance = null;
    public static PhysicsFactory get() {
        if (instance == null) {
            instance = new PhysicsFactory();
        }

        return instance;
    }

    public PhysicsFactory() {
    }

    public Body createObstacleBody(float x, float y, float width, float height) {
        return new Body(x, y, width, height);
    }

    public Body createPlayerBody(float x, float y, float width, float height) {

        System.out.println("Creating player body");

        float leftMargin = width * 0.3f / 2f;
        float rightMargin = width * 0.3f / 2f;
        float bottomMargin = height * 0.0f / 2f;
        float topMargin = height * 0.35f / 2f;

        Body body = new Body(x, y, width, height);
        body.setOffset(new Vector2(0, 0));

        Shape hitbox = new RectangleShape(body.width - leftMargin - rightMargin, body.height - topMargin - bottomMargin, leftMargin, bottomMargin);
        body.createFixture("HITBOX", hitbox);

        return body;
    }
}
