package com.byron.builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.*;
import com.byron.components.player.KeyboardComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.components.visuals.LightComponent;
import com.byron.models.Agent;
import com.byron.models.equip.EquipSlot;
import com.byron.models.sprite.RawAnimationModel;
import com.byron.renderers.strategy.RenderPriority;
import com.byron.renderers.strategy.SpriteRenderPositionStrategy;

import java.util.Map;

public class AgentBuilder {

    Entity entity;

    public AgentBuilder(Agent agent, String agentId) {

        entity = new Entity()
            .add(new AgentComponent(agent.getStats()))
            .add(new StatusComponent());
    }

    public AgentBuilder isPlayer() {
        entity.add(new PlayerComponent());
        entity.add(new TakeDamageComponent());
        return this;
    }

    public AgentBuilder isAI(int movementSpeed) {
        entity.add(new AIComponent(movementSpeed));
        return this;
    }

    public AgentBuilder withVelocity(Vector2 velocity) {
        entity.add(new VelocityComponent(velocity));
        return this;
    }

    public AgentBuilder withKeyboardControl() {
        entity.add(new KeyboardComponent());
        return this;
    }

    public AgentBuilder at(float x, float y) {
        entity.add(new PositionComponent(x, y));
        return this;
    }

    public AgentBuilder withAnimations(RawAnimationModel rawAnimationModel) {
        entity.add(new RenderComponent(new SpriteRenderPositionStrategy(0.15f / 2f, -0.15f / 2f), RenderPriority.AGENT))
            .add(new AnimableSpriteComponent())
            .add(new StackedSpritesComponent(rawAnimationModel))
            .add(new RefreshSpriteRequirementComponent());
        return this;
    }

    public AgentBuilder withEquipment(Map<EquipSlot, Entity> equipment, String skin) {
        entity.add(new WearComponent(equipment, skin));
        return this;
    }

    public AgentBuilder withLight(Sprite lightSprite, float size) {
        entity.add(new LightComponent(lightSprite, size));
        return this;
    }

    public AgentBuilder withSpeed(float speed) {
        entity.add(new SpeedComponent(speed));
        return this;
    }

    public AgentBuilder withWeapon(Sprite weaponSprite, int damage) {
        entity.add(new WeaponComponent(weaponSprite, damage));
        return this;
    }

    public Entity build() {
        return entity;
    }
}
