package com.byron.systems.sprite;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.components.RenderComponent;
import com.byron.components.StatusComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.models.status.Status;
import com.byron.utils.Mappers;

import java.util.List;
import java.util.Map;

public class AnimableSpriteSystem extends IteratingSystem {

    private final ComponentMapper<AnimableSpriteComponent> asm = Mappers.animableSprite;
    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<RenderComponent> rm = Mappers.render;

    public AnimableSpriteSystem() {
        super(Family.all(StatusComponent.class, AnimableSpriteComponent.class, RenderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimableSpriteComponent animableSpriteComponent = asm.get(entity);
        StatusComponent statusComponent = sm.get(entity);

        Action action = statusComponent.getAction();
        Direction direction = statusComponent.getDirection();

        putNextFrameOnRenderComponent(entity, action, direction, animableSpriteComponent);
        tickOrResetStateTime(deltaTime, animableSpriteComponent, statusComponent);

    }

    private void putNextFrameOnRenderComponent(Entity entity, Action a, Direction d, AnimableSpriteComponent animableSpriteComponent) {
        Status status = new Status(a, d);
        RenderComponent renderComponent = rm.get(entity);

        renderComponent.clear();

        List<Map<Status, Animation<?>>> texturesToAnimations = animableSpriteComponent.getTexturesToAnimations();

        for (Map<Status, Animation<?>> animations : texturesToAnimations) {
            Animation<?> animation = animations.get(status);
            Sprite sprite = (Sprite) animation.getKeyFrame(animableSpriteComponent.stateTime);
            renderComponent.add(sprite);
        }
    }

    /* TODO: see if I can make this prettier */
    private void tickOrResetStateTime(float deltaTime, AnimableSpriteComponent animableSpriteComponent, StatusComponent statusComponent) {
        if (statusComponent.isChangeInActionOrDirection()) {
            animableSpriteComponent.stateTime = 0f;
            statusComponent.setChangeInActionOrDirection(false);
        } else {
            animableSpriteComponent.stateTime += deltaTime;
        }
    }
}