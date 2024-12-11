package com.byron.interfaces;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.models.Agent;

import java.util.Map;

public interface IAgentService {
    Entity spawnAgent(GridPoint2 location, String type);
    Entity spawnPlayer(GridPoint2 location);
    Map<String, Agent> getAgents();
    Entity getPlayer();
}
