package com.byron.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentManager;
import com.byron.interfaces.ICameraManager;
import com.byron.interfaces.IDungeonManager;
import com.byron.interfaces.IItemManager;
import com.byron.interfaces.IMapManager;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.interfaces.IRenderable;
import com.byron.interfaces.ISoundManager;
import com.byron.managers.AgentManager;
import com.byron.managers.CameraManager;
import com.byron.managers.DungeonManager;
import com.byron.managers.ItemManager;
import com.byron.managers.LevelManager;
import com.byron.managers.PlayerInputManager;
import com.byron.managers.SoundManager;
import com.byron.managers.ui.UserInterfaceManager;
import com.byron.renderers.GridRenderer;
import com.byron.systems.*;
import com.byron.systems.debug.DebugOverlaySystem;
import com.byron.systems.debug.DebugSystem;
import com.byron.systems.hud.FadingTextSystem;
import com.byron.systems.hud.ScoreChangeSystem;
import com.byron.systems.hud.ScoreRenderer;
import com.byron.systems.hud.ScoreWidgetSpawner;
import com.byron.systems.render.LightingSystem;
import com.byron.systems.render.RenderSystem;
import com.byron.systems.render.ShapeRenderSystem;
import com.byron.systems.sprite.AnimatableSpriteSystem;
import com.byron.systems.sprite.StackableSpriteSystem;
import com.byron.systems.sprite.StackedSpritesSystem;
import com.byron.systems.weapons.ScoreMilestoneSystem;
import com.byron.systems.weapons.SlashSystem;
import com.byron.systems.weapons.WeaponAttachSystem;
import com.byron.systems.weapons.WeaponSystem;

public class GameScreen extends ScreenAdapter {

    // Core
    private Stage stage;
    private final GameResources resources;
    private final OrthographicCamera camera;

    // Managers
    private ICameraManager cameraManager;
    private IMapManager mapManager;
    private ISoundManager soundManager;
    private IAgentManager agentManager;
    private IPlayerInputManager playerInputManager;
    private IDungeonManager dungeonManager;
    private IItemManager itemManager;
    private LevelManager levelManager;
    private UserInterfaceManager userInterfaceManager;

    private IRenderable gridRenderer;
    private IRenderable lightsRenderer;
    private int seed;


    public GameScreen(int seed) {

        this.resources = GameResources.get();
        this.seed = seed;
        initializeLogs();
        initializeStage();
        initializeRenderers();
        initializeManagers();
        initializeListeners();
        initializeSystems();
        initGame();
        camera = GameResources.get().getCamera();

    }

    private void initializeRenderers() {
        gridRenderer = new GridRenderer();
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
        cameraManager = new CameraManager();
        soundManager = new SoundManager();
        agentManager = new AgentManager();
        itemManager = new ItemManager(agentManager.getAgentFactory());
        dungeonManager = new DungeonManager(this.seed, itemManager.getItemService(), agentManager.getAgentService());
        userInterfaceManager = new UserInterfaceManager();
        playerInputManager = new PlayerInputManager(userInterfaceManager.getUiService());
        levelManager = new LevelManager(agentManager.getAgentService(), itemManager.getItemService(), dungeonManager.getDungeonService());
    }

    private void initializeListeners() {
        this.stage.addListener((ClickListener) playerInputManager);
    }

    private void initializeSystems() {
        Engine engine = resources.getEngine();
        engine.addSystem(new StackableSpriteSystem());
        engine.addSystem(new StackedSpritesSystem());
        engine.addSystem(new AnimatableSpriteSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new AISystem(dungeonManager.getDungeonService(), agentManager.getAgentService(), playerInputManager));
        engine.addSystem(new PlayerInputSystem(playerInputManager, dungeonManager.getDungeonService(), agentManager.getAgentService()));
        engine.addSystem(new CameraFocusSystem(cameraManager.getCameraService()));
        engine.addSystem(new SmoothMovementSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new ScoreMilestoneSystem());
        engine.addSystem(new RenderSystem());
        engine.addSystem(new WeaponAttachSystem());
        engine.addSystem(new SlashSystem());
        engine.addSystem(new WeaponSystem(agentManager.getAgentService(), playerInputManager));
        engine.addSystem(new ShapeRenderSystem());
        engine.addSystem(new DebugSystem());
        engine.addSystem(new DebugOverlaySystem());
        engine.addSystem(new LightingSystem());
        engine.addSystem(new HUDRenderSystem());
        engine.addSystem(new TakeDamageSystem());
        engine.addSystem(new ScoreChangeSystem());
        engine.addSystem(new FadingTextSystem());
        engine.addSystem(new ScoreRenderer());
        engine.addSystem(new ScoreWidgetSpawner());
        engine.addSystem(new LootSystem(agentManager.getAgentService()));
    }

    private void initGame() {
        levelManager.init();
    }

    @Override
    public void render(float delta) {

        if (resources.isRestart()) {
//            GameResources.get().setRestart(false);
//            resources.getEngine().removeAllEntities();
//            GameResources.get().getScreenManager().setCurrentScreen(new GameScreen(100000));
        }

        cameraManager.render(delta);

        resources.getBatch().setProjectionMatrix(camera.combined);
        resources.getBatch().begin();
        resources.getEngine().update(delta);
        resources.getBatch().end();

//        gridRenderer.render(delta);
        userInterfaceManager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}