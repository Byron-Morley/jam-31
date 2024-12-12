package com.byron.managers.ui;

import static com.byron.utils.Messages.ACCELERATION;
import static com.byron.utils.Messages.BODY_POSITION;
import static com.byron.utils.Messages.PLAYER_DIRECTION;
import static com.byron.utils.Messages.PLAYER_POSITION;
import static com.byron.utils.Messages.PLAYER_STATUS;
import static com.byron.utils.Messages.PREVIOUS_POSITION;
import static com.byron.utils.Messages.SPEED;
import static com.byron.utils.Messages.VELOCITY;
import static com.byron.utils.ui.Panels.TOP_RIGHT_DEBUG;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.byron.engine.GameResources;
import com.byron.interfaces.IRenderable;
import com.byron.interfaces.IUIService;
import com.byron.interfaces.IUpdatable;
import com.byron.interfaces.IWindowManager;
import com.byron.interfaces.UI;
import com.byron.managers.debug.DebugOverlayManager;
import com.byron.services.ui.UIService;
import com.byron.ui.controller.OverlayUIController;
import com.byron.ui.view.debug.TopRightDebugUI;

import java.util.HashMap;
import java.util.Map;

public class UserInterfaceManager implements IRenderable, IWindowManager, IUpdatable {

    final Map<Integer, UI<Group>> ui;
    final Stage stage;

    //    MANAGERS
    DebugOverlayManager debugOverlayManager;

    //    SERVICES
    IUIService uiService;

    //    CONTROLLERS
    OverlayUIController overlayUIController;

    public UserInterfaceManager() {
        this.stage = GameResources.get().getStage();
        ui = new HashMap<>();
        uiService = new UIService(this);
        init();
    }

    public void init() {
        initializeManagers();
        initializeViews();
        initializeControllers();
        subscribeListeners();
    }

    private void initializeManagers() {
        this.debugOverlayManager = new DebugOverlayManager();
    }

    public void registerUI(int id, UI<Group> window) {
        stage.addActor(window.get());
        ui.put(id, window);
    }

    private void initializeViews() {
        System.out.println("Initializing UI");

        registerUI(TOP_RIGHT_DEBUG, new TopRightDebugUI());
    }

    public void initializeControllers() {
        overlayUIController = new OverlayUIController(uiService);
    }

    private void subscribeListeners() {
        MessageDispatcher messageDispatcher = MessageManager.getInstance();
        messageDispatcher.addListeners(this.overlayUIController,
            PLAYER_POSITION,
            PLAYER_STATUS,
            PLAYER_DIRECTION,
            VELOCITY,
            ACCELERATION,
            BODY_POSITION,
            PREVIOUS_POSITION,
            SPEED
        );
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();
        stage.act();
        stage.draw();
        stage.setDebugAll(false);
        update();
    }

    @Override
    public void update() {
        for (UI<Group> ui : ui.values()) {
            if (ui instanceof IUpdatable) {
                ((IUpdatable) ui).update();
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public UI<Group> getUI(int id) {
        return ui.get(id);
    }

    public Stage getStage() {
        return stage;
    }

    public IUIService getUiService() {
        return uiService;
    }
}