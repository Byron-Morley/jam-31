package com.byron.interfaces;

import com.badlogic.ashley.core.Entity;
import com.byron.factories.AgentFactory;
import com.byron.models.Agent;

import java.util.Map;

public interface IAgentManager {

    IAgentService getAgentService();

    AgentFactory getAgentFactory();

    Map<String, Agent> getAgents();

    void setPlayer(Entity player);

    Entity getPlayer();
}
