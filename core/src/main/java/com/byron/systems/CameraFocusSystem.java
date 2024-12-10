package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.PositionComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.interfaces.ICameraService;
import com.byron.utils.Mappers;

public class CameraFocusSystem extends IteratingSystem {

    private final ComponentMapper<PositionComponent> pm = Mappers.position;

    ICameraService cameraService;

    public CameraFocusSystem(ICameraService cameraService) {
        super(Family.all(PositionComponent.class, KeyboardComponent.class).get());
        this.cameraService = cameraService;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 position = pm.get(entity).position;
        cameraService.setPosition(position.x, position.y);
    }
}