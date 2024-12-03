package com.byron.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
}
