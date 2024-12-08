package com.byron.interfaces;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IWindowManager {
    UI<Group> getUI(int id);

    Stage getStage();
}
