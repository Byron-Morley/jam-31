package com.byron.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.AgentComponent;
import com.byron.components.TakeDamageComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.factories.SpriteFactory;
import com.byron.models.equip.EquipSlot;

public class TakeDamageSystem extends IteratingSystem {

    public TakeDamageSystem() {
        super(Family.all(TakeDamageComponent.class, AgentComponent.class, WearComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {

        System.out.println("TakeDamageSystem");

        TakeDamageComponent takeDamageComponent = entity.getComponent(TakeDamageComponent.class);
        AgentComponent agentComponent = entity.getComponent(AgentComponent.class);
        WearComponent wearComponent = entity.getComponent(WearComponent.class);

        Entity effect = new Entity().add(new StackableSpriteComponent(SpriteFactory.get("agent/hurt-" + wearComponent.getSkin())));
        wearComponent.wearables.put(EquipSlot.EFFECT, effect);

    }
}
