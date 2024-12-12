package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IItemService;

public class LevelManager {

    private final IAgentService agentService;
    private final IItemService itemService;

    public LevelManager(IAgentService agentService, IItemService itemService) {
        this.agentService = agentService;
        this.itemService = itemService;
    }

    public void init() {
        System.out.println("LevelManager: init()");


//        itemService.spawnItem(itemService.getItem("building/tile").build(), new GridPoint2(11, 12));

//        Entity snake  = agentService.spawnAgent(new GridPoint2(12, 12), "snake");
//        Entity dragon  = agentService.spawnAgent(new GridPoint2(13, 12), "red-dragon");
//        Entity ooze  = agentService.spawnAgent(new GridPoint2(14, 12), "blue-ooze");
//        Entity goblin  = agentService.spawnAgent(new GridPoint2(15, 12), "goblin");

        Entity player = agentService.spawnPlayer(new GridPoint2(14, 10));
    }
}