package com.byron.systems.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.PositionComponent;
import com.byron.components.WeaponComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.utils.Mappers;

public class WeaponAttachSystem extends IteratingSystem {

    public WeaponAttachSystem() {
        super(Family
            .all(
                WeaponComponent.class,
                PositionComponent.class
            ).exclude(
                PositionInterpComponent.class
            ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 position = Mappers.position.get(entity).position;
        Vector2 direction = Mappers.status.get(entity).getDirection().vector;

        Entity weapon = Mappers.weapon.get(entity).weapon;
        Sprite weaponSprite = Mappers.sprite.get(weapon).sprite;

        weaponSprite.setPosition(position.x, position.y);
        weaponSprite.setRotation(direction.angleDeg() + 225f);
    }
}