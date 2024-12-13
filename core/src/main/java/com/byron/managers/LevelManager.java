package com.byron.managers;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IItemService;

import static com.byron.utils.Messages.PLAY_MUSIC;
import static com.byron.utils.Messages.PLAY_SOUND;


public class LevelManager {

    private final IAgentService agentService;
    private final IItemService itemService;

    IDungeonService dungeonService;

    public LevelManager(IAgentService agentService, IItemService itemService, IDungeonService dungeonService) {
        this.agentService = agentService;
        this.itemService = itemService;
        this.dungeonService = dungeonService;
    }

    public void init() {
        System.out.println("LevelManager: init()");

        MessageManager.getInstance().dispatchMessage(PLAY_MUSIC, "track_01");
        MessageManager.getInstance().dispatchMessage(PLAY_SOUND, "explosion");

    }
}