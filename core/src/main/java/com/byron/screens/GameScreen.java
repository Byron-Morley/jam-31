package com.byron.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.byron.engine.GameResources;
import com.byron.interfaces.ICameraManager;
import com.byron.interfaces.IMapManager;
import com.byron.interfaces.IRenderable;
import com.byron.managers.CameraManager;
import com.byron.managers.MapManager;
import com.byron.managers.ui.UIManager;
import com.byron.systems.RenderSystem;

public class GameScreen implements Screen {

    //Core
    Stage stage;
    GameResources resources;

    //Managers
    ICameraManager cameraManager;
    IRenderable uiManager;
    IMapManager mapManager;

    public GameScreen() {
        this.resources = GameResources.get();
        initializeLogs();
        initializeStage();
        initializeManagers();
    }


    private void initializeLogs() {

        //    int LOG_NONE = 0;
        //    int LOG_DEBUG = 3;
        //    int LOG_INFO = 2;
        //    int LOG_ERROR = 1;

        Gdx.app.setLogLevel(Application.LOG_NONE);
        Gdx.app.debug("GENERAL", "Debug Log Active");
        Gdx.app.log("GENERAL", "Info Log Active");
        Gdx.app.error("GENERAL", "Error Log Active");
    }

    private void initializeStage() {
        ScreenViewport viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(1f);
        this.stage = new Stage(viewport);
        GameResources.get().setStage(stage);
    }

    private void initializeManagers() {
        this.cameraManager = new CameraManager();
        this.mapManager = new MapManager();
        this.uiManager = new UIManager();
    }

    private void initializeSystems() {
        Engine engine = resources.getEngine();
        engine.addSystem(new RenderSystem());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        cameraManager.render(delta);

        mapManager.render(delta);
        resources.getBatch().begin();
        resources.getEngine().update(delta);
        resources.getBatch().end();

        uiManager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
