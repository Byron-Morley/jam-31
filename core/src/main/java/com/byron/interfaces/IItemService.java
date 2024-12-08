package com.byron.interfaces;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.byron.builders.ItemBuilder;

public interface IItemService {

    void spawnItem(Entity entity, GridPoint2 position);

    void spawnItem(Entity entity);

    ItemBuilder getItem(String name);

}
