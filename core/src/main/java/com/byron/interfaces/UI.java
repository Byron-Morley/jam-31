package com.byron.interfaces;

import com.badlogic.gdx.scenes.scene2d.Group;

public interface UI<T extends Group> {
    T get();
}
