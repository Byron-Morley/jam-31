package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WeaponComponent implements Component {

    public final Sprite sprite;
    public int damage;

    public WeaponComponent(Sprite weaponSprite, int damage) {
        sprite = weaponSprite;
        sprite.setOriginCenter();
        this.damage = damage;
    }
}