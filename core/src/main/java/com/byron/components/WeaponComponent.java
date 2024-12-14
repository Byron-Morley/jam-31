package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.byron.utils.ComponentUtils;
import com.byron.utils.Mappers;

public class WeaponComponent implements Component {

    public final Entity weapon;

    public WeaponComponent(Entity weapon) {
        if (!ComponentUtils.hasAll(weapon, Mappers.weaponTag, Mappers.sprite, Mappers.damage)) {
            throw new IllegalArgumentException("Weapon component requires an entity with proper components");
        }
        this.weapon = weapon;
    }
}