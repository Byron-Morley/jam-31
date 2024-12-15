package com.byron.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.AgentComponent;
import com.byron.components.TakeDamageComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.factories.SpriteFactory;
import com.byron.models.equip.EquipSlot;

public class TakeDamageSystem extends IteratingSystem {

    public TakeDamageSystem() {
        super(Family.all(TakeDamageComponent.class, AgentComponent.class, WearComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {

        TakeDamageComponent takeDamageComponent = entity.getComponent(TakeDamageComponent.class);
        WearComponent wearComponent = entity.getComponent(WearComponent.class);

        if (takeDamageComponent.counter == 0) {
            wearComponent.wearables.remove(EquipSlot.EFFECT);
            entity.add(new RefreshSpriteRequirementComponent());
            entity.remove(TakeDamageComponent.class);
            return;
        }

        if (wearComponent.wearables.containsKey(EquipSlot.EFFECT)) {
            wearComponent.wearables.remove(EquipSlot.EFFECT);
        } else {
            Entity effect = new Entity().add(new StackableSpriteComponent(SpriteFactory.get("agent/hurt-" + wearComponent.getSkin())));
            wearComponent.wearables.put(EquipSlot.EFFECT, effect);
        }

        entity.add(new RefreshSpriteRequirementComponent());
        takeDamageComponent.counter--;
    }
}
