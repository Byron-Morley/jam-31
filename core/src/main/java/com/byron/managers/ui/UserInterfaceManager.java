package com.byron.managers.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.byron.engine.GameResources;
import com.byron.interfaces.*;
import com.byron.managers.debug.DebugOverlayManager;
import com.byron.services.ui.UIService;
import com.byron.interfaces.UI;
import com.byron.ui.controller.OverlayUIController;
import com.byron.ui.view.debug.TopRightDebugUI;

import java.util.HashMap;
import java.util.Map;

import static com.byron.utils.ui.Panels.TOP_RIGHT_DEBUG;


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
}
