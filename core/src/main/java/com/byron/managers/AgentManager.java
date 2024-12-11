package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.byron.factories.AgentFactory;
import com.byron.interfaces.IAgentManager;
import com.byron.interfaces.IAgentService;
import com.byron.models.Agent;
import com.byron.services.AgentService;

import java.util.Map;

public class AgentManager implements IAgentManager {
    AgentFactory agentFactory;
    IAgentService agentService;
    Entity player;


    public AgentManager() {
        this.agentFactory = new AgentFactory();
        this.agentService = new AgentService(this);
    }

    public AgentFactory getAgentFactory() {
        return agentFactory;
    }

    @Override
    public Map<String, Agent> getAgents() {
        return agentFactory.getAgents();
    }

    public IAgentService getAgentService() {
        return agentService;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Entity getPlayer() {
        return player;
    }
}
