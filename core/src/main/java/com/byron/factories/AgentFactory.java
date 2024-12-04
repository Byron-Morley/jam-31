package com.byron.factories;

import com.badlogic.gdx.math.Vector2;
import com.byron.builders.AgentBuilder;
import com.byron.models.Agent;
import com.byron.models.sprite.RawAnimationModel;

import java.util.Map;

import static com.byron.utils.Config.PLAYER_START_X;
import static com.byron.utils.Config.PLAYER_START_Y;

public class AgentFactory {
    private AnimationsFactory animationsFactory;
    private Map<String, Agent> agents;

    public AgentFactory() {
        this.agents = ModelFactory.getAgentsModel();
        this.animationsFactory = new AnimationsFactory();
    }

    public AgentBuilder create(String agentId) {
        Agent agent = agents.get(agentId);
        RawAnimationModel rawAnimationModel = animationsFactory.get(agent.getAnimationModel());

        AgentBuilder builder = new AgentBuilder(agentId)
                .withKeyboardControl().at(PLAYER_START_X, PLAYER_START_Y)
                .withVelocity(new Vector2(agent.getVelocityX(), agent.getVelocityY()))
                .withAnimations(rawAnimationModel)
                .isPlayer();
        return builder;
    }
}
