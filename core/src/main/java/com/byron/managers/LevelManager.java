package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IItemService;


public class LevelManager {

    IAgentService agentService;
    IItemService itemService;

    public LevelManager(IAgentService agentService, IItemService itemService) {
        this.agentService = agentService;
        this.itemService = itemService;
    }

    public void init(){
        System.out.println("LevelManager: init()");
        itemService.spawnItem(itemService.getItem("stone-floor").build(), new GridPoint2(10, 10));
        Entity player  = agentService.spawnPlayer(new GridPoint2(10, 10));


    }
}
