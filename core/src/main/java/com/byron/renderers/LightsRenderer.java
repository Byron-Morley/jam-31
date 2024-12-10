package com.byron.renderers;

import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.byron.engine.GameResources;
import com.byron.interfaces.IRenderable;

import java.util.ArrayList;
import java.util.List;

public class LightsRenderer implements IRenderable {

    private final SpriteBatch spriteBatch;
    private final FrameBuffer lightsBuffer;
    private final TextureRegion bufferRegion;
    private final List<Sprite> lights;
    private final Matrix4 projectionMatrix;
    private final OrthographicCamera bufferCamera;
    private final Color ambientColor;

    public LightsRenderer() {
        spriteBatch = GameResources.get().getBatch();
        lightsBuffer = new FrameBuffer(RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        bufferRegion = new TextureRegion(lightsBuffer.getColorBufferTexture(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bufferRegion.flip(false, true);
        lights = new ArrayList<>();
        Texture glow = new Texture("raw/sprites/circleGlow.png");
        Sprite light = new Sprite(glow);
        light.setPosition(5f, 5f);
        light.setSize(10f, 10f);
        lights.add(light);
        projectionMatrix = new Matrix4();
        bufferCamera = new OrthographicCamera();
        bufferCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ambientColor = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void render(float delta) {
        drawLights();

        // Draw what's on the buffer
        spriteBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        spriteBatch.begin();
        spriteBatch.draw(bufferRegion, 0, 0);
        spriteBatch.end();

        // Back to normal blending
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
        for (int i = 0; i < lights.size(); i++) {
            lights.get(i).draw(spriteBatch);
        }
        spriteBatch.end();
        lightsBuffer.end();

        spriteBatch.setProjectionMatrix(projectionMatrix);
    }

    @Override
    public void dispose() {

    }
}