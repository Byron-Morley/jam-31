package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LightComponent implements Component {

    public final Sprite sprite;

    public LightComponent(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(10f, 10f);
        sprite.setOriginCenter();
    }

    public Sprite getSprite() {
        return sprite;
    }
}