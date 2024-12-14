package com.byron.systems.sprite;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.RenderComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.models.sprite.ComplexSprite;
import com.byron.utils.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StackableSpriteSystem extends IteratingSystem {

    private final ComponentMapper<StackableSpriteComponent> stackable = Mappers.stackableSprite;
    private final ComponentMapper<StackedSpritesComponent> stacked = Mappers.stackedSprites;
    private final ComponentMapper<WearComponent> wm = Mappers.wear;

    public StackableSpriteSystem() {
        super(Family.all(
            RefreshSpriteRequirementComponent.class,
            StackedSpritesComponent.class,
            RenderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        WearComponent wearComponent = wm.get(entity);
        StackedSpritesComponent stackedSpritesComponent = stacked.get(entity);
        if (wearComponent != null) {
            List<ComplexSprite> wearablesComplexSprites = getTexturesToMerge(entity);
            stackedSpritesComponent.setStackedComplexSprites(wearablesComplexSprites);
        } else {
            List<ComplexSprite> sprites = new ArrayList<>();
            sprites.add(stackable.get(entity).getComplexSprite());
            stackedSpritesComponent.setStackedComplexSprites(sprites);
        }
    }

    private List<ComplexSprite> getTexturesToMerge(Entity entity) {
        WearComponent wearComponent = wm.get(entity);
        return wearComponent
            .asList()
            .stream()
            .map(e -> stackable.get(e).getComplexSprite())
            .collect(Collectors.toList());
    }
}