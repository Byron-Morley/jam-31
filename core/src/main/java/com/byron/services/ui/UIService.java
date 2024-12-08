package com.byron.services.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.byron.interfaces.IUIService;
import com.byron.interfaces.IWindowManager;
import com.byron.services.Service;
import com.byron.interfaces.UI;

public class UIService extends Service implements IUIService {

    IWindowManager windowManager;
    Stage stage;

    public UIService(IWindowManager windowManager) {
        this.windowManager = windowManager;
        stage = windowManager.getStage();
    }

    public IWindowManager getWindowManager() {
        return windowManager;
    }

    public UI<Group> getUI(int id) {
        return windowManager.getUI(id);
    }

    @Override
    public boolean isMouseOverUI() {
        Vector2 mouseCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        mouseCoords.y = stage.getViewport().getScreenHeight() - mouseCoords.y;
        Actor actor = stage.hit(mouseCoords.x, mouseCoords.y, true);
        return actor != null;
    }


}
