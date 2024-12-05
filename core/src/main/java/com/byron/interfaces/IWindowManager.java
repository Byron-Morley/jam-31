package com.byron.interfaces;

import com.badlogic.gdx.scenes.scene2d.Group;

public interface IWindowManager {
    UI<Group> getUI(int id);
}
