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

import java.util.List;
import java.util.stream.Collectors;


public class StackableSpriteSystem extends IteratingSystem {
    private ComponentMapper<StackableSpriteComponent> stackable = Mappers.stackableSprite;
    private ComponentMapper<StackedSpritesComponent> stacked = Mappers.stackedSprites;
    private ComponentMapper<WearComponent> wm = Mappers.wear;

    public StackableSpriteSystem() {
        super(Family.all(
                RefreshSpriteRequirementComponent.class,
                StackedSpritesComponent.class,
                RenderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        List<ComplexSprite> wearablesComplexSprites = getTexturesToMerge(entity);
        StackedSpritesComponent stackedSpritesComponent = stacked.get(entity);
        stackedSpritesComponent.setStackedComplexSprites(wearablesComplexSprites);
    }

    private List<ComplexSprite> getTexturesToMerge(Entity entity){
        WearComponent wearComponent = wm.get(entity);

        return wearComponent
                .asList()
                .stream()
                .map(e -> stackable.get(e).getComplexSprite())
                .collect(Collectors.toList());
    }
}
