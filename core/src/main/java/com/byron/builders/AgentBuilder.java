package com.byron.builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.*;
import com.byron.components.player.KeyboardComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.components.visuals.LightComponent;
import com.byron.factories.PhysicsFactory;
import com.byron.factories.SpriteFactory;
import com.byron.models.equip.EquipSlot;
import com.byron.models.sprite.RawAnimationModel;
import com.byron.renderers.strategy.RenderPriority;
import com.byron.renderers.strategy.SpriteRenderPositionStrategy;

import java.util.Map;

public class AgentBuilder {

    Entity entity;

    public AgentBuilder(String agentId) {
        entity = new Entity()
            .add(new AgentComponent())
            .add(new StatusComponent());
    }

    public AgentBuilder isPlayer() {
        entity.add(new PlayerComponent());
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

    public AgentBuilder withEquipment(Map<EquipSlot, Entity> equipment) {
        entity.add(new WearComponent(equipment));
        return this;
    }

    public AgentBuilder withBody(float x, float y, float width, float height) {
        BodyComponent bodyComponent = new BodyComponent(PhysicsFactory.get().createPlayerBody(x, y, width, height));
        entity.add(bodyComponent);

        return this;
    }

    public AgentBuilder withLight(Texture texture) {
        entity.add(new LightComponent(texture));
        return this;
    }

    public Entity build() {
        return entity;
    }
}
