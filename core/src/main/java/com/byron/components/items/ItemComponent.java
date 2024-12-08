package com.byron.components.items;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.factories.SpriteFactory;
import com.byron.models.item.Item;

public class ItemComponent implements Component {
    final String id;
    final String name;
    final String label;
    final Sprite sprite;
    final Item item;

    public ItemComponent(Item item, String id, String name) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.label = item.getLabel();
        this.sprite = SpriteFactory.getSprite(item.getSprite());
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
