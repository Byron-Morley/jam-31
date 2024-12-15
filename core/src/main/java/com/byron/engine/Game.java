package com.byron.engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.byron.managers.ScreenManager;
import com.kotcrab.vis.ui.VisUI;

public class Game extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ScreenManager screenManager;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        VisUI.load();
        screenManager = new ScreenManager(new GameResources(new Engine(), spriteBatch, shapeRenderer));
        screenManager.loadStartMenu();

        Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/track_01.ogg"));
        music.setVolume(0.2f);
        music.play();
    }

    @Override
    public void render() {
        clearScreen();
        screenManager.render(Gdx.graphics.getDeltaTime());
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        if (screenManager.getCurrentScreen() != null)
            screenManager.getCurrentScreen().resize(width, height);
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}