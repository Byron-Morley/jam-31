package com.byron.services;

import com.badlogic.ashley.core.Engine;
import com.byron.engine.GameResources;

public abstract class Service {
    private final Engine engine;

    public Service() {
        this.engine = GameResources.get().getEngine();
    }

    protected Engine getEngine() {
        return engine;
    }
}
