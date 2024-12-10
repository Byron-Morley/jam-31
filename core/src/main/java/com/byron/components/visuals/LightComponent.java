package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LightComponent implements Component {

    public final Sprite sprite;

    public LightComponent(Sprite lightSprite) {
        sprite = new Sprite(lightSprite);
        sprite.setSize(10f, 10f);
        sprite.setOriginCenter();
    }

    public Sprite getSprite() {
        return sprite;
    }
}