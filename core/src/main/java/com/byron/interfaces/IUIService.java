package com.byron.interfaces;

import com.badlogic.gdx.scenes.scene2d.Group;

public interface IUIService {
    UI<Group> getUI(int id);
}
