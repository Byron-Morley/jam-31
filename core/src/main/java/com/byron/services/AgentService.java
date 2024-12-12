package com.byron.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.builders.AgentBuilder;
import com.byron.components.AgentComponent;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.interfaces.IAgentManager;
import com.byron.interfaces.IAgentService;
import com.byron.models.Agent;
import com.byron.utils.Mappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AgentService extends Service implements IAgentService {

    IAgentManager agentManager;

    public AgentService(IAgentManager agentManager) {
        this.agentManager = agentManager;
    }

    public Entity spawnPlayer(GridPoint2 location) {
        Entity player = this.spawnAgent(location, "player");
        agentManager.setPlayer(player);
        return player;
    }

    @Override
    public Map<String, Agent> getAgents() {
        return agentManager.getAgents();
    }

    @Override
    public Entity getPlayer() {
        return agentManager.getPlayer();
    }


    public Entity spawnAgent(GridPoint2 location, String type) {
        AgentBuilder agentBuilder = agentManager.getAgentFactory()
            .create(type)
            .at(location.x, location.y);
        Entity agentEntity = agentBuilder.build();

        // Print all components
        agentEntity.getComponents().forEach(component -> {
            System.out.println("Component: " + component.getClass().getSimpleName());
        });

        getEngine().addEntity(agentEntity);
        return agentEntity;
    }

    public boolean isPositionFree(GridPoint2 gridPoint) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(Family.all(PositionComponent.class, RenderComponent.class, AgentComponent.class).get());

        for (Entity entity : entities) {
            PositionComponent positionComponent = Mappers.position.get(entity);
            if (positionComponent.position.x == gridPoint.x && positionComponent.position.y == gridPoint.y) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPositionFree(int x, int y) {
        return isPositionFree(new GridPoint2(x, y));
    }


}