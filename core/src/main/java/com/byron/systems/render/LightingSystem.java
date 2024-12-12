package com.byron.systems.render;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.components.visuals.LightComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;

public class LightingSystem extends EntitySystem {

    private ImmutableArray<Entity> lightEntities;

    private final SpriteBatch spriteBatch;
    private final FrameBuffer lightsBuffer;
    private final TextureRegion bufferRegion;
    private final Matrix4 projectionMatrix;
    private final OrthographicCamera bufferCamera;
    private final Color ambientColor;

    public LightingSystem() {
        spriteBatch = GameResources.get().getBatch();
        lightsBuffer = new FrameBuffer(RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        bufferRegion = new TextureRegion(lightsBuffer.getColorBufferTexture(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bufferRegion.flip(false, true);
        projectionMatrix = new Matrix4();
        bufferCamera = new OrthographicCamera();
        bufferCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ambientColor = new Color(0f, 0f, 0.1f, 1f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        lightEntities = engine.getEntitiesFor(Family.all(LightComponent.class, PositionComponent.class, RenderComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.end();
        drawLights();

        // Draw what's on the buffer
        spriteBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        spriteBatch.begin();
        spriteBatch.draw(bufferRegion, 0, 0);
        spriteBatch.end();

        // Back to normal blending
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();
    }

    private void drawLights() {
        projectionMatrix.set(spriteBatch.getProjectionMatrix());
        spriteBatch.setProjectionMatrix(bufferCamera.combined);

        lightsBuffer.begin();
        spriteBatch.begin();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        if (Gdx.input.isKeyJustPressed(L)) {
            ambientColor.set(MathUtils.random(), MathUtils.random(), MathUtils.random(), 0f);
        }

        ScreenUtils.clear(ambientColor);
        Entity entity;
        Sprite sprite;
        Vector2 position;
        RenderComponent renderComponent;
        int width, height;
        for (int i = 0; i < lightEntities.size(); i++) {
            entity = lightEntities.get(i);
            sprite = Mappers.light.get(entity).sprite;
            position = Mappers.position.get(entity).position;
            renderComponent = Mappers.render.get(entity);
            width = renderComponent.getWidth();
            height = renderComponent.getHeight();
            sprite.setOriginBasedPosition(position.x + width / 2f, position.y + height / 2f);
            sprite.draw(spriteBatch);
        }
        spriteBatch.end();
        lightsBuffer.end();

        spriteBatch.setProjectionMatrix(projectionMatrix);
    }
}