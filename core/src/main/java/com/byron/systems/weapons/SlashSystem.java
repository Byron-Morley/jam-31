package com.byron.systems.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.byron.components.sprite.SpriteComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.components.visuals.RotationInterpComponent;
import com.byron.components.weapons.WeaponTag;
import com.byron.utils.Mappers;

public class SlashSystem extends IteratingSystem {

    private static final float SLASH_DURATION = 0.25f;

    public SlashSystem() {
        super(Family.all(
            WeaponTag.class,
            SpriteComponent.class,
            PositionInterpComponent.class,
            RotationInterpComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite weapon = Mappers.sprite.get(entity).sprite;
        PositionInterpComponent posInterp = Mappers.positionInterp.get(entity);
        RotationInterpComponent rotInterp = Mappers.rotationInterp.get(entity);

        posInterp.progress = Math.min(posInterp.progress + deltaTime / SLASH_DURATION, 1f);
        posInterp.current.set(posInterp.start).lerp(posInterp.end, posInterp.interpolation.apply(posInterp.progress));
        weapon.setPosition(posInterp.current.x, posInterp.current.y);

        rotInterp.progress = Math.min(rotInterp.progress + deltaTime / SLASH_DURATION, 1f);
        rotInterp.current = MathUtils.lerp(rotInterp.start, rotInterp.end, rotInterp.interpolation.apply(rotInterp.progress));
        weapon.setRotation(rotInterp.current);

        if (posInterp.progress == 1f && rotInterp.progress == 1f) {
            entity.remove(PositionInterpComponent.class);
            entity.remove(RotationInterpComponent.class);
        }
    }
}