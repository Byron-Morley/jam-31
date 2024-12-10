package com.byron.systems.sprite;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.byron.components.RenderComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.components.sprite.RefreshSpriteRequirementComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.models.sprite.CellModel;
import com.byron.models.sprite.ComplexSprite;
import com.byron.models.sprite.RawAnimationModel;
import com.byron.models.sprite.SubAnimationModel;
import com.byron.models.status.Status;
import com.byron.utils.Dimensions;
import com.byron.utils.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackedSpritesSystem extends IteratingSystem {

    private final ComponentMapper<StackedSpritesComponent> stacked = Mappers.stackedSprites;
    private final ComponentMapper<AnimableSpriteComponent> animable = Mappers.animableSprite;

    public StackedSpritesSystem() {
        super(Family.all(RefreshSpriteRequirementComponent.class, AnimableSpriteComponent.class, StackedSpritesComponent.class, RenderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StackedSpritesComponent stackedSpritesComponent = stacked.get(entity);
        List<ComplexSprite> complexSprites = stackedSpritesComponent.getStackedComplexSprites();
        RawAnimationModel model = stackedSpritesComponent.getRawAnimationModel();

        List<Map<Status, Animation<?>>> texturesToAnimations = new ArrayList<>();

        for (ComplexSprite complexSprite : complexSprites) {

            Map<Status, Animation<?>> animations = createAnimations(complexSprite, model);
            texturesToAnimations.add(animations);
        }

        AnimableSpriteComponent animableSpriteComponent = animable.get(entity);
        animableSpriteComponent.setTexturesToAnimations(texturesToAnimations);

        entity.remove(RefreshSpriteRequirementComponent.class);
    }

    private Map<Status, Animation<?>> createAnimations(ComplexSprite complexSprite, RawAnimationModel model) {
        Map<Status, SubAnimationModel> animationsModels = model.getSubAnimations();
        Map<Status, Animation<?>> animations = new HashMap<>();

        TextureAtlas atlas = complexSprite.getAtlas();
        String name = complexSprite.getName();

        for (Status status : animationsModels.keySet()) {
            Array<Sprite> segments = new Array<>();
            SubAnimationModel subAnimation = animationsModels.get(status);

            CellModel[] cells = subAnimation.getCells();
            float frameDuration = (subAnimation.hasFrameRate()) ? subAnimation.getFrameDuration() : model.getFrameDuration();

            for (int i = 0; i < cells.length; i++) {

                CellModel cell = cells[i];
                int index = cell.getCell();

                TextureRegion textureRegion = atlas.findRegion(name, index);

                float width = Dimensions.toMeters(textureRegion.getRegionWidth());
                float height = Dimensions.toMeters(textureRegion.getRegionHeight());

                Sprite sprite = new Sprite(textureRegion);
                sprite.setSize(width, height);
//                sprite.setOriginCenter();

                sprite.flip(cell.isFlipX(), cell.isFlipY());
                segments.add(sprite);

                animations.put(status, new Animation<>(frameDuration, segments, subAnimation.getPlayMode()));
            }
        }
        return animations;
    }
}