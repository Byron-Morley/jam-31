package com.byron.managers;

import com.byron.factories.AgentFactory;
import com.byron.factories.ItemFactory;
import com.byron.interfaces.IItemManager;
import com.byron.interfaces.IItemService;
import com.byron.services.ItemService;

public class ItemManager implements IItemManager {

    ItemFactory itemFactory;
    IItemService itemService;

    public ItemManager(AgentFactory agentFactory) {
        itemFactory = new ItemFactory(agentFactory);
        itemService = new ItemService(this);
    }

    public IItemService getItemService() {
        return itemService;
    }

    @Override
    public ItemFactory getItemFactory() {
        return itemFactory;
    }
}
