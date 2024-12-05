package com.byron.services.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.byron.interfaces.IUIService;
import com.byron.interfaces.IWindowManager;
import com.byron.services.Service;
import com.byron.interfaces.UI;

public class UIService extends Service implements IUIService {

    IWindowManager windowManager;

    public UIService(IWindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public IWindowManager getWindowManager() {
        return windowManager;
    }

    public UI<Group> getUI(int id) {
        return windowManager.getUI(id);
    }
}
