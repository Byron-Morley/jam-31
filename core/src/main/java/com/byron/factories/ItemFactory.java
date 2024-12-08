package com.byron.factories;

import com.badlogic.ashley.core.ComponentMapper;
import com.byron.builders.ItemBuilder;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.models.item.Item;
import com.byron.utils.Mappers;

import java.util.Map;

public class ItemFactory {
    Map<String, Item> models;
    ComponentMapper<PositionComponent> pm = Mappers.position;
    ComponentMapper<RenderComponent> rm = Mappers.render;
    int itemCount = 0;

    public ItemFactory() {
        this.models = ModelFactory.getItemsModel();
    }

    public ItemBuilder getItem(String name) {
        return this.getItem(name, 1);
    }

    public ItemBuilder getItem(String name, int quantity) {
        String id = name + "_" + ++itemCount;
        Item model = models.get(name);

        ItemBuilder itemBuilder = new ItemBuilder(model, id, name)
            .withRender(model.getSprite(), model.getSpriteScale());

        return itemBuilder;
    }


}
