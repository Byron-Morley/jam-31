package com.byron.systems.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AgentComponent;
import com.byron.components.PositionComponent;
import com.byron.components.SlashComponent;
import com.byron.components.StatusComponent;
import com.byron.components.WeaponComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.player.PlayerAction;
import com.byron.utils.Mappers;

import java.util.Stack;

public class WeaponSystem extends IteratingSystem {

    private final IPlayerInputManager playerInputManager;
    private final SpriteBatch spriteBatch;

    public WeaponSystem(IPlayerInputManager playerInputManager) {
        super(Family.all(
            AgentComponent.class,
            StatusComponent.class,
            PositionComponent.class,
            WeaponComponent.class
        ).get());
        this.playerInputManager = playerInputManager;
        spriteBatch = GameResources.get().getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite weapon = Mappers.weapon.get(entity).sprite;
        Vector2 direction = Mappers.status.get(entity).getDirection().vector;

        handleAttackAction(entity, weapon, direction);
        weapon.draw(spriteBatch);
    }

    private void handleAttackAction(Entity entity, Sprite weapon, Vector2 direction) {
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();
        if (actionKeysPressed.size() < 1) return;

        PlayerAction action = actionKeysPressed.pop();
        if (action != PlayerAction.ACTION) return;

        Vector2 position = Mappers.position.get(entity).position;

        entity.add(new SlashComponent(position, position.cpy().add(direction)));
        weapon.setRotation(direction.angleDeg() + 225f);
        // TODO: 12/11/2024 Play sound
    }
}