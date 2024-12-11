package com.byron.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.byron.components.BodyComponent;
import com.byron.components.DestinationComponent;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.components.ShapeComponent;
import com.byron.components.SlashComponent;
import com.byron.components.SpeedComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.WeaponComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.components.player.WearComponent;
import com.byron.components.sprite.AnimableSpriteComponent;
import com.byron.components.sprite.StackableSpriteComponent;
import com.byron.components.sprite.StackedSpritesComponent;
import com.byron.components.visuals.LightComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
    public static final ComponentMapper<StatusComponent> status = ComponentMapper.getFor(StatusComponent.class);
    public static final ComponentMapper<KeyboardComponent> keyboard = ComponentMapper.getFor(KeyboardComponent.class);
    public static final ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<AnimableSpriteComponent> animableSprite = ComponentMapper.getFor(AnimableSpriteComponent.class);
    public static final ComponentMapper<StackedSpritesComponent> stackedSprites = ComponentMapper.getFor(StackedSpritesComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<StackableSpriteComponent> stackableSprite = ComponentMapper.getFor(StackableSpriteComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<WearComponent> wear = ComponentMapper.getFor(WearComponent.class);
    public static final ComponentMapper<ShapeComponent> shape = ComponentMapper.getFor(ShapeComponent.class);
    public static final ComponentMapper<LightComponent> light = ComponentMapper.getFor(LightComponent.class);
    public static final ComponentMapper<DestinationComponent> destination = ComponentMapper.getFor(DestinationComponent.class);
    public static final ComponentMapper<SpeedComponent> speed = ComponentMapper.getFor(SpeedComponent.class);
    public static final ComponentMapper<WeaponComponent> weapon = ComponentMapper.getFor(WeaponComponent.class);
    public static final ComponentMapper<SlashComponent> slash = ComponentMapper.getFor(SlashComponent.class);
}