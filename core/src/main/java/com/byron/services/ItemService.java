package com.byron.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.byron.builders.ItemBuilder;
import com.byron.components.PositionComponent;
import com.byron.interfaces.IItemManager;
import com.byron.interfaces.IItemService;

public class ItemService extends Service implements IItemService {

    IItemManager itemManager;

    public ItemService(IItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public void spawnItem(Entity entity, GridPoint2 position) {
        entity.add(new PositionComponent(position));
        getEngine().addEntity(entity);
    }

    @Override
    public void spawnItem(Entity entity) {
        entity.add(new PositionComponent(new Vector2(-200, -200)));
        getEngine().addEntity(entity);
    }

    @Override
    public ItemBuilder getItem(String name) {
            return itemManager.getItemFactory().getItem(name);
    }
}
