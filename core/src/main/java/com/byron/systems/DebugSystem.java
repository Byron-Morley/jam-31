package com.mygdx.game.systems.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.dto.physics.Fixture;
import com.mygdx.game.engine.GameResources;
import com.mygdx.game.managers.world.CameraManager;
import com.mygdx.game.utils.PhasedIteratingSystem;
import com.mygdx.game.utils.shape.CircleShape;
import com.mygdx.game.utils.shape.RectangleShape;
import static com.mygdx.game.utils.Config.DEBUG;

public class DebugSystem extends PhasedIteratingSystem {
    private Camera camera;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;

    public DebugSystem(CameraManager cameraManager, GameResources gameResources) {
        super(Family.all(BodyComponent.class, PositionComponent.class).get());
        this.renderer = new ShapeRenderer();
        this.spriteBatch = gameResources.getBatch();
        this.camera = cameraManager.getCamera();
    }

    @Override
    protected void beforeFrame() {
        spriteBatch.end();
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (DEBUG) {
            BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(new Color(Color.RED));

//            System.out.println(bodyComponent.body.position.x + " " + bodyComponent.body.position.y);
//            System.out.println(bodyComponent.body.width + " " + bodyComponent.body.height);

            renderer.rect(bodyComponent.body.position.x, bodyComponent.body.position.y, bodyComponent.body.width, bodyComponent.body.height);

            renderer.setColor(new Color(Color.BLUE));

            for (Fixture fixture : bodyComponent.body.getFixtures()) {
                if (fixture.getShape() instanceof RectangleShape) {
                    RectangleShape rect = (RectangleShape) fixture.getShape();

                    float x = bodyComponent.body.getPosition().x + rect.getX();
                    float y = bodyComponent.body.getPosition().y + rect.getY();
                    float w = rect.getWidth();
                    float h = rect.getHeight();

                    renderer.rect(x, y, w, h);
                } else if (fixture.getShape() instanceof CircleShape) {

                    CircleShape circle = (CircleShape) fixture.getShape();

                    float x = bodyComponent.body.getPosition().x + circle.getX() + bodyComponent.body.width / 2;
                    float y = bodyComponent.body.getPosition().y + circle.getY() + bodyComponent.body.height / 2;
                    float r = circle.getRadius();

                    renderer.circle(x, y, r, 80);
                }
            }

            renderer.end();
        }
    }

    @Override
    protected void afterFrame() {
        Gdx.gl.glDisable(GL30.GL_BLEND);
        spriteBatch.begin();
    }
}
