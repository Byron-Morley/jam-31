package com.byron.systems;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.byron.components.HUDProgressBarComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;

public class HUDRenderSystem extends EntitySystem {

    private final Color BAR_FRAME_COLOR = new Color(0f, 0.2f, 0f, 1f);
    private final Color HEALTH_BAR_COLOR = new Color(0.4f, 0f, 0f, 1f);
    private final Color ARMOR_BAR_COLOR = new Color(0f, 0f, 0.4f, 1f);

    private ImmutableArray<Entity> progressBars;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    public HUDRenderSystem() {
        spriteBatch = GameResources.get().getBatch();
        shapeRenderer = GameResources.get().getShapeRenderer();
        camera = GameResources.get().getCamera();
    }

    @Override
    public void addedToEngine(Engine engine) {
        progressBars = engine.getEntitiesFor(Family.all(HUDProgressBarComponent.class).get());

        float barHeightPortion = 0.03f;
        float healthBarYPortion = 1f - barHeightPortion;
        float armorBarYPortion = healthBarYPortion - barHeightPortion;

        Entity healthBar = new Entity();
        HUDProgressBarComponent healthBarComponent = new HUDProgressBarComponent(0f, healthBarYPortion, 1f, barHeightPortion);
        healthBarComponent.color.set(HEALTH_BAR_COLOR);
        healthBarComponent.progress = 1f;
        healthBar.add(healthBarComponent);
        engine.addEntity(healthBar);

        Entity armorBar = new Entity();
        armorBar.add(new HUDProgressBarComponent(0f, armorBarYPortion, 1f, barHeightPortion, ARMOR_BAR_COLOR));
        engine.addEntity(armorBar);
    }

    @Override
    public void update(float deltaTime) {
        spriteBatch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glLineWidth(4f);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();

        HUDProgressBarComponent progressBar;
        shapeRenderer.set(Filled);
        for (int i = 0; i < progressBars.size(); i++) {
            progressBar = Mappers.progressBar.get(progressBars.get(i));
            updateProgressBar(progressBar);
            drawProgressBarInner(progressBar);
        }

        shapeRenderer.set(Line);
        for (int i = 0; i < progressBars.size(); i++) {
            progressBar = Mappers.progressBar.get(progressBars.get(i));
            drawProgressBarFrame(progressBar);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        spriteBatch.begin();
    }

    private void updateProgressBar(HUDProgressBarComponent progressBar) {
        float camW = camera.viewportWidth * camera.zoom;
        float camH = camera.viewportHeight * camera.zoom;

        Rectangle rect = progressBar.rectangle;
        rect.x = camera.position.x - camW / 2f + camW * progressBar.xPortion;
        rect.y = camera.position.y - camH / 2f + camH * progressBar.yPortion;
        rect.width = camW * progressBar.wPortion;
        rect.height = camH * progressBar.hPortion;
    }

    private void drawProgressBarInner(HUDProgressBarComponent progressBar) {
        Rectangle rectangle = progressBar.rectangle;
        shapeRenderer.setColor(progressBar.color);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width * progressBar.progress, rectangle.height);
    }

    private void drawProgressBarFrame(HUDProgressBarComponent progressBar) {
        Rectangle rectangle = progressBar.rectangle;
        shapeRenderer.setColor(BAR_FRAME_COLOR);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}