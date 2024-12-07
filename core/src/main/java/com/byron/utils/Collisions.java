package com.mygdx.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.dto.physics.Body;
import com.mygdx.game.dto.physics.Collider;
import com.mygdx.game.dto.physics.Fixture;

public class Collisions {

    public static boolean collision(Collider c1, Collider c2) {
        return c1.getX() + c1.getWidth() > c2.getX()
                && c1.getX() < c2.getX() + c2.getWidth()
                && c1.getY() + c1.getHeight() > c2.getY()
                && c1.getY() < c2.getY() + c2.getHeight();
    }

    public static boolean bottomCollision(Collider c1, Collider c2) {
        return c1.getY() <= c2.getY() + c2.getHeight() &&
                c1.getPreviousY() >= c2.getPreviousY() + c2.getHeight();
    }

    public static boolean topCollision(Collider c1, Collider c2) {
        return c1.getTop() >= c2.getBottom() &&
                c1.getPreviousY() + c1.getHeight() <= c2.getPreviousY();
    }

    public static boolean leftCollision(Collider c1, Collider c2) {
        return c1.getLeft() <= c2.getRight() &&
                c1.getPreviousX() >= c2.getPreviousX() + c2.getWidth();
    }

    public static boolean rightCollision(Collider c1, Collider c2) {
        return c1.getRight() >= c2.getLeft() &&
                c1.getPreviousX() + c1.getWidth() <= c2.getPreviousX();
    }

    public static boolean lineRect(float lineX1, float lineY1, float lineX2, float lineY2, float rectX, float rectY, float rectWidth, float rh) {

        boolean left = lineLine(lineX1, lineY1, lineX2, lineY2, rectX, rectY, rectX, rectY + rh);
        boolean right = lineLine(lineX1, lineY1, lineX2, lineY2, rectX + rectWidth, rectY, rectX + rectWidth, rectY + rh);
        boolean top = lineLine(lineX1, lineY1, lineX2, lineY2, rectX, rectY, rectX + rectWidth, rectY);
        boolean bottom = lineLine(lineX1, lineY1, lineX2, lineY2, rectX, rectY + rh, rectX + rectWidth, rectY + rh);

        return (left || right || top || bottom);
    }

    public static boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

        float v = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / v;
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / v;

        return (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1);
    }

    public static boolean doesFixtureCollideWithBody(Fixture fixture, Body body) {
        Rectangle fixtureRect = new Rectangle(fixture.getX(), fixture.getY(), fixture.getWidth(), fixture.getHeight());
        Rectangle rect = new Rectangle(body.position.x, body.position.y, body.width, body.height);
        return fixtureRect.overlaps(rect);
    }
}
