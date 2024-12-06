package com.byron.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.byron.components.BodyComponent;
import com.byron.components.PositionComponent;
import com.byron.engine.GameResources;
import com.byron.models.physics.Fixture;
import com.byron.utils.PhasedIteratingSystem;
import com.byron.utils.shape.CircleShape;
import com.byron.utils.shape.RectangleShape;

import static com.byron.utils.Config.DEBUG;

public class DebugSystem extends PhasedIteratingSystem {
    private Camera camera;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;

    public DebugSystem() {
        super(Family.all(BodyComponent.class, PositionComponent.class).get());
        this.renderer = new ShapeRenderer();
        this.spriteBatch = GameResources.get().getBatch();
        this.camera = GameResources.get().getCamera();
    }

    @Override
    protected void beforeFrame() {
        spriteBatch.end();
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        if (DEBUG) {
//            BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
//            renderer.setProjectionMatrix(camera.combined);
//            renderer.begin(ShapeRenderer.ShapeType.Line);
//            renderer.setColor(new Color(Color.RED));
//
////            System.out.println(bodyComponent.body.position.x + " " + bodyComponent.body.position.y);
////            System.out.println(bodyComponent.body.width + " " + bodyComponent.body.height);
//
//            renderer.rect(bodyComponent.body.getX(), bodyComponent.body.getY(), bodyComponent.body.width, bodyComponent.body.height);
//
//            renderer.setColor(new Color(Color.BLUE));
//
//            for (Fixture fixture : bodyComponent.body.getFixtures()) {
//                if (fixture.getShape() instanceof RectangleShape) {
//                    RectangleShape rect = (RectangleShape) fixture.getShape();
//
//                    float x = bodyComponent.body.getPosition().x + rect.getX();
//                    float y = bodyComponent.body.getPosition().y + rect.getY();
//                    float w = rect.getWidth();
//                    float h = rect.getHeight();
//
//                    renderer.rect(x, y, w, h);
//                } else if (fixture.getShape() instanceof CircleShape) {
//
//                    CircleShape circle = (CircleShape) fixture.getShape();
//
//                    float x = bodyComponent.body.getPosition().x + circle.getX() + bodyComponent.body.width / 2;
//                    float y = bodyComponent.body.getPosition().y + circle.getY() + bodyComponent.body.height / 2;
//                    float r = circle.getRadius();
//
//                    renderer.circle(x, y, r, 80);
//                }
//            }
//
//            renderer.end();
//        }
    }

    @Override
    protected void afterFrame() {
        Gdx.gl.glDisable(GL30.GL_BLEND);
        spriteBatch.begin();
    }
}
