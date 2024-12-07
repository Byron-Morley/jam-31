package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IAgentService;

import static com.byron.utils.Config.PLAYER_START_X;
import static com.byron.utils.Config.PLAYER_START_Y;

public class LevelManager {

    IAgentService agentService;

    public LevelManager(IAgentService agentService) {
        this.agentService = agentService;
    }

    public void init(){
        System.out.println("LevelManager: init()");
        Entity player  = agentService.spawnPlayer(new GridPoint2(PLAYER_START_X, PLAYER_START_Y));
    }
}
