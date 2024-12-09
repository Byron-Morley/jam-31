package com.byron.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.components.RenderComponent;
import com.byron.components.items.ItemComponent;
import com.byron.factories.SpriteFactory;
import com.byron.models.item.Item;
import com.byron.renderers.strategy.RenderPriority;

public class ItemBuilder {

    private Entity entity;

    public ItemBuilder(Item item, String id, String name) {
        this.entity = new Entity().add(new ItemComponent(item, id, name));
    }

    public ItemBuilder withRender(String spriteName, float spriteScale, Color color) {
        try {
            Sprite sprite = SpriteFactory.getSprite(spriteName);
            sprite.setScale(spriteScale);
            this.entity.add(new RenderComponent(sprite, RenderPriority.ITEM));
        } catch (Exception e) {
            Gdx.app.error("ItemBuilder", spriteName);
            Gdx.app.error("ItemBuilder", "Error: " + e.getMessage());
        }
        return this;
    }

    public ItemBuilder withRender(String spriteName, float spriteScale) {
        return withRender(spriteName, spriteScale, new Color(Color.WHITE));
    }

    public ItemBuilder add(Component component) {
        entity.add(component);
        return this;
    }


    public Entity build() {
        return entity;
    }
}