package com.byron.factories;

import com.byron.models.sprite.RawAnimationModel;

import java.util.Map;

public class AnimationsFactory {
    private Map<String, RawAnimationModel> rawModels;

    public AnimationsFactory() {
        this.rawModels = ModelFactory.getAnimationsModels();
    }

    public RawAnimationModel get(String name) {
        if (!rawModels.containsKey(name))
            throw new RuntimeException("Raw animation model " + name + " not found.");
        return rawModels.get(name);
    }
}
