package com.byron.factories;

import static com.byron.constants.GeneralConstants.LIGHT_COLOR;

import com.badlogic.ashley.core.ComponentMapper;
import com.byron.builders.ItemBuilder;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.models.item.Item;
import com.byron.models.sprite.RawAnimationModel;
import com.byron.utils.Mappers;

import java.util.Map;

public class ItemFactory {

    private final Map<String, Item> models;
    ComponentMapper<PositionComponent> pm = Mappers.position;
    ComponentMapper<RenderComponent> rm = Mappers.render;
    private int itemCount = 0;
    private final AnimationsFactory animationsFactory;

    public ItemFactory(AgentFactory agentFactory) {
        this.animationsFactory = agentFactory.getAnimationsFactory();
        this.models = ModelFactory.getItemsModel();
    }

    public ItemBuilder getItem(String name) {
        return this.getItem(name, 1);
    }

    public ItemBuilder getItem(String name, int quantity) {
        String id = name + "_" + ++itemCount;
        Item model = models.get(name);

        RawAnimationModel rawAnimationModel = animationsFactory.get(model.getAnimationModel());

        ItemBuilder itemBuilder = new ItemBuilder(model, id, name)
            .withAnimations(rawAnimationModel, model.getSprite());

        if (model.isExit()) {
            itemBuilder.withExit();
        }

        if (model.isPickupable()) {
            itemBuilder.isLoot(model.getValue(), model.isArmor());
        }

        if (model.isLight()) {
            itemBuilder.withLight(SpriteFactory.getSprite("circleGlow"), 5f, LIGHT_COLOR);
        }

        return itemBuilder;
    }
}