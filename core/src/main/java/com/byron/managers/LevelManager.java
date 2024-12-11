package com.byron.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;

import java.util.Random;


public class LevelManager {

    IAgentService agentService;
    IItemService itemService;

    IDungeonService dungeonService;

    public LevelManager(IAgentService agentService, IItemService itemService, IDungeonService dungeonService) {
        this.agentService = agentService;
        this.itemService = itemService;
        this.dungeonService = dungeonService;
    }

    public void init(){
        System.out.println("LevelManager: init()");


//        itemService.spawnItem(itemService.getItem("building/tile").build(), new GridPoint2(11, 12));

        /*
        Entity snake  = agentService.spawnAgent(new GridPoint2(12, 12), "snake");
        Entity dragon  = agentService.spawnAgent(new GridPoint2(13, 12), "red-dragon");
        Entity ooze  = agentService.spawnAgent(new GridPoint2(14, 12), "blue-ooze");
        Entity goblin  = agentService.spawnAgent(new GridPoint2(15, 12), "goblin");
        */

        Entity player  = agentService.spawnPlayer(new GridPoint2(10, 10));
    }

}
