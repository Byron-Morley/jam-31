package com.byron.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.builders.AgentBuilder;
import com.byron.interfaces.IAgentManager;
import com.byron.interfaces.IAgentService;

public class AgentService extends Service implements IAgentService {

    IAgentManager agentManager;

    public AgentService(IAgentManager agentManager) {
        this.agentManager = agentManager;
    }

    public Entity spawnPlayer(GridPoint2 location) {
        return this.spawnAgent(location, "player");
    }

    public Entity spawnAgent(GridPoint2 location, String type) {
        AgentBuilder agentBuilder = agentManager.getAgentFactory().create(type).at(location.x, location.y);
        Entity agentEntity = agentBuilder.build();

        // Print all components
        agentEntity.getComponents().forEach(component -> {
            System.out.println("Component: " + component.getClass().getSimpleName());
        });

        getEngine().addEntity(agentEntity);
        return agentEntity;
    }

}
