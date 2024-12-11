package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WeaponComponent implements Component {

    public final Sprite sprite;

    public WeaponComponent(Sprite weaponSprite) {
        sprite = weaponSprite;
        sprite.setOriginCenter();
    }
}