package com.byron.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.components.LootComponent;
import com.byron.components.RenderComponent;
import com.byron.components.StatusComponent;
import com.byron.components.items.ItemComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.components.visuals.LightComponent;
import com.byron.factories.SpriteFactory;
import com.byron.models.item.Item;
import com.byron.models.sprite.RawAnimationModel;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.renderers.strategy.DefaultRenderPositionStrategy;
import com.byron.renderers.strategy.ItemRenderPositionStrategy;
import com.byron.renderers.strategy.RenderPriority;
import com.byron.renderers.strategy.SpriteRenderPositionStrategy;

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

    public ItemBuilder isLoot(int value, boolean isArmor) {
        entity.add(new LootComponent(value, isArmor));
        return this;
    }

    public ItemBuilder withAnimations(RawAnimationModel rawAnimationModel, String spriteName) {
        entity.add(new RenderComponent(new ItemRenderPositionStrategy(), RenderPriority.ITEM))
            .add(new StatusComponent(Action.IDLE, Direction.NONE))
            .add(new AnimableSpriteComponent())
            .add(new StackedSpritesComponent(rawAnimationModel))
            .add(new RefreshSpriteRequirementComponent())
            .add(new StackableSpriteComponent(SpriteFactory.getItemComplexSprite(spriteName)));
        return this;
    }

    public ItemBuilder withLight(Sprite lightSprite, float size) {
        entity.add(new LightComponent(lightSprite, size));
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
