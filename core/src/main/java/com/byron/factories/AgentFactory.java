package com.byron.factories;

import static com.byron.constants.GeneralConstants.AGENT_SPEED;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.byron.builders.AgentBuilder;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.models.Agent;
import com.byron.models.equip.EquipSlot;
import com.byron.models.sprite.RawAnimationModel;

import java.util.HashMap;
import java.util.Map;

public class AgentFactory {

    private final AnimationsFactory animationsFactory;
    private final Map<String, Agent> agents;

    public AgentFactory() {
        this.agents = ModelFactory.getAgentsModel();
        this.animationsFactory = new AnimationsFactory();
    }

    public AgentBuilder create(String agentId) {
        Agent agent = agents.get(agentId);
        RawAnimationModel rawAnimationModel = animationsFactory.get(agent.getAnimationModel());
        Map<EquipSlot, Entity> equipment = buildEquipment(agent.getBody());
        AgentBuilder builder = new AgentBuilder(agentId)
            .withVelocity(new Vector2(agent.getVelocityX(), agent.getVelocityY()))
            .withAnimations(rawAnimationModel)
            .withEquipment(equipment)
            .withSpeed(AGENT_SPEED);

        if (agent.isPlayer()) {
            builder
                .withKeyboardControl()
                .isPlayer()
                .withLight(SpriteFactory.getSprite("circleGlow"));
        } else {
            builder.isAI();
        }

        return builder;
    }

    private Map<EquipSlot, Entity> buildEquipment(Map<EquipSlot, String> body) {
        Map<EquipSlot, Entity> equipment = new HashMap<>();

        for (Map.Entry<EquipSlot, String> entry : body.entrySet()) {
            Entity overlay = new Entity().add(new StackableSpriteComponent(SpriteFactory.get(entry.getValue())));
            equipment.put(entry.getKey(), overlay);
        }

        return equipment;
    }
}