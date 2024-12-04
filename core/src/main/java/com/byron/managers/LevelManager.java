package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IAgentService;

public class LevelManager {

    IAgentService agentService;

    public LevelManager(IAgentService agentService) {
        this.agentService = agentService;
    }

    public void init(){
        System.out.println("LevelManager: init()");
        Entity player  = agentService.spawnPlayer(new GridPoint2(1, 1));
    }
}
