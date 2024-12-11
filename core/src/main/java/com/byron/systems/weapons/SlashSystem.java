package com.byron.systems.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.SlashComponent;
import com.byron.components.WeaponComponent;
import com.byron.utils.Mappers;

public class SlashSystem extends IteratingSystem {

    private static final float SLASH_DURATION = 0.2f;

    public SlashSystem() {
        super(Family.all(WeaponComponent.class, SlashComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite weapon = Mappers.weapon.get(entity).sprite;
        SlashComponent slash = Mappers.slash.get(entity);
        Vector2 start = slash.start, current = slash.current, end = slash.end;
        float progress = slash.progress;

        progress = slash.progress = Math.min(progress + deltaTime / SLASH_DURATION, 1f);
        current.set(start).lerp(end, progress);
        weapon.setPosition(current.x, current.y);

        if (progress == 1f) {
            weapon.setPosition(start.x, start.y);
            entity.remove(SlashComponent.class);
        }
    }
}