package com.byron.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.byron.engine.GameResources;
import com.byron.interfaces.*;
import com.byron.managers.*;
import com.byron.managers.ui.UserInterfaceManager;
import com.byron.systems.*;
import com.byron.systems.debug.DebugOverlaySystem;
import com.byron.systems.debug.DebugSystem;
import com.byron.systems.render.RenderSystem;
import com.byron.systems.render.ShapeRenderSystem;
import com.byron.systems.sprite.AnimableSpriteSystem;
import com.byron.systems.sprite.StackableSpriteSystem;
import com.byron.systems.sprite.StackedSpritesSystem;

public class GameScreen implements Screen {

    //Core
    Stage stage;
    GameResources resources;

    //Managers
    ICameraManager cameraManager;
    IMapManager mapManager;
    ISoundManager soundManager;
    IAgentManager agentManager;
    IPlayerInputManager playerInputManager;
    LevelManager levelManager;
    UserInterfaceManager userInterfaceManager;

    public GameScreen() {
        this.resources = GameResources.get();
        initializeLogs();
        initializeStage();
        initializeManagers();
        initializeListeners();
        initializeSystems();
        initGame();
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
        Gdx.input.setInputProcessor(stage);
    }

    private void initializeManagers() {
        this.cameraManager = new CameraManager();
        this.soundManager = new SoundManager();
        this.mapManager = new MapManager();
        this.agentManager = new AgentManager();
        this.playerInputManager = new PlayerInputManager();
        this.userInterfaceManager = new UserInterfaceManager();
        this.levelManager = new LevelManager(agentManager.getAgentService());
    }

    private void initializeListeners() {
        this.stage.addListener((ClickListener) playerInputManager);
    }

    private void initializeSystems() {
        Engine engine = resources.getEngine();
        engine.addSystem(new StackableSpriteSystem());
        engine.addSystem(new StackedSpritesSystem());
        engine.addSystem(new AnimableSpriteSystem());
        engine.addSystem(new PlayerInputSystem(playerInputManager));
        engine.addSystem(new CameraFocusSystem(cameraManager.getCameraService()));
        engine.addSystem(new RenderSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new ShapeRenderSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new DebugSystem());
        engine.addSystem(new DebugOverlaySystem());
    }

    private void initGame() {
        levelManager.init();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        cameraManager.render(delta);

        ((IRenderable) mapManager).render(delta);
        resources.getBatch().begin();
        resources.getEngine().update(delta);
        resources.getBatch().end();

        userInterfaceManager.render(delta);
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
