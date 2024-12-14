package com.byron.components.sprite;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {

    public final Sprite sprite;

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}