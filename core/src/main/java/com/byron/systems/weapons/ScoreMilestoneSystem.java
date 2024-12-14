package com.byron.systems.weapons;

import static com.byron.constants.MilestoneType.MAJOR;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.components.WeaponComponent;
import com.byron.components.events.MilestoneEvent;
import com.byron.components.player.PlayerComponent;
import com.byron.constants.Weapon;
import com.byron.factories.SpriteFactory;
import com.byron.utils.Mappers;
import com.byron.utils.Random;

public class ScoreMilestoneSystem extends IteratingSystem {

    private ImmutableArray<Entity> playerArray;
    private int tier;

    public ScoreMilestoneSystem() {
        super(Family.all(MilestoneEvent.class).get());
        tier = 1;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        playerArray = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MilestoneEvent milestoneEvent = Mappers.milestone.get(entity);
        WeaponComponent weapon = Mappers.weapon.get(playerArray.first());

        if (milestoneEvent.type == MAJOR) {
            tier = Math.min(tier + 1, 2);
        }
        randomizeWeapon(weapon);

        getEngine().removeEntity(entity);
    }

    private void randomizeWeapon(WeaponComponent weaponComponent) {
        Weapon newWeapon = Random.fromArray(Weapon.TIER_MAP.get(tier));
        Entity weapon = weaponComponent.weapon;
        Sprite weaponSprite = Mappers.sprite.get(weapon).sprite;
        weaponSprite.set(SpriteFactory.getSprite(newWeapon.name));
        weaponSprite.setOriginCenter();
        Mappers.damage.get(weapon).damage = newWeapon.damage;
    }
}