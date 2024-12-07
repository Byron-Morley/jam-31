package com.byron.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.PositionComponent;
import com.byron.components.ShapeComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;
import com.byron.utils.PhasedIteratingSystem;
import com.byron.utils.shape.Shape;

public class ShapeRenderSystem extends PhasedIteratingSystem {
    private ComponentMapper<ShapeComponent> sm = Mappers.shape;
    private ComponentMapper<PositionComponent> pm = Mappers.position;
    private Camera camera;
    private ShapeRenderer renderer;
    private SpriteBatch spriteBatch;

    public ShapeRenderSystem() {
        super(Family.all(ShapeComponent.class, PositionComponent.class).get());
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

        ShapeComponent shapeComponent = sm.get(entity);
        PositionComponent positionComponent = pm.get(entity);
        Vector2 position = positionComponent.getPosition();
        Shape shape = shapeComponent.getShape();

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(shape.getShapeType());
        renderer.setColor(shape.getColor());

        Vector2 pos = shapeComponent.getRenderPositionStrategy().process(position.x, position.y);

        if (camera.frustum.pointInFrustum(pos.x, pos.y, 0)) {
//            shape.render(renderer, pos);
        }

        shape.render(renderer, pos);

        renderer.end();
    }

    @Override
    protected void afterFrame() {
        Gdx.gl.glDisable(GL30.GL_BLEND);
        spriteBatch.begin();
    }
}
