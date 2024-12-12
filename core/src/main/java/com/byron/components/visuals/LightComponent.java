package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LightComponent implements Component {

    public final Sprite sprite;

    public LightComponent(Sprite lightSprite, float size) {
        sprite = new Sprite(lightSprite);
        sprite.setSize(size, size);
        sprite.setOriginCenter();
    }

    public LightComponent(Sprite lightSprite) {
        this(lightSprite, 10f);
    }
}