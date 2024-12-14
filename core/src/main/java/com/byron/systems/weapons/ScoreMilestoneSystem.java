package com.byron.systems.weapons;

import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.byron.constants.MilestoneType.MAJOR;
import static com.byron.models.status.Direction.UP;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.WeaponComponent;
import com.byron.components.events.MilestoneEvent;
import com.byron.components.hud.TextComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.components.visuals.ColorInterpComponent;
import com.byron.components.visuals.PositionInterpComponent;
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
        Vector2 playerPos = Mappers.position.get(playerArray.first()).position;

        if (milestoneEvent.type == MAJOR) {
            tier = Math.min(tier + 1, 2);
        }
        randomizeWeapon(weapon, playerPos);
        displayWeaponDamage(weapon);

        getEngine().removeEntity(entity);
    }

    private void randomizeWeapon(WeaponComponent weaponComponent, Vector2 position) {
        Weapon newWeapon = Random.fromArray(Weapon.TIER_MAP.get(tier));
        Entity weapon = weaponComponent.weapon;
        Sprite weaponSprite = Mappers.sprite.get(weapon).sprite;
        weaponSprite.set(SpriteFactory.getSprite(newWeapon.name));
        weaponSprite.setOriginCenter();
        weaponSprite.setPosition(position.x, position.y);
        Mappers.damage.get(weapon).damage = newWeapon.damage;
    }

    private void displayWeaponDamage(WeaponComponent weaponComponent) {
        int damage = Mappers.damage.get(weaponComponent.weapon).damage;
        Sprite weaponSprite = Mappers.sprite.get(weaponComponent.weapon).sprite;
        Vector2 start = new Vector2(weaponSprite.getX(), weaponSprite.getY());

        Entity damageIndicator = new Entity()
            .add(new ColorInterpComponent(BLUE, CLEAR))
            .add(new PositionInterpComponent(start.cpy(), start.cpy().add(UP.vector)))
            .add(new TextComponent(new BitmapFont(Gdx.files.internal("raw/fonts/pixelFont.fnt")), "x" + damage, 0.05f));
        getEngine().addEntity(damageIndicator);
    }
}