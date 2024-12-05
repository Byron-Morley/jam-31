package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.PositionComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.interfaces.ICameraService;
import com.byron.utils.Mappers;

public class CameraFocusSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = Mappers.position;

    ICameraService cameraService;

    public CameraFocusSystem(ICameraService cameraService) {
        super(Family.all(PositionComponent.class, KeyboardComponent.class).get());
        this.cameraService = cameraService;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        cameraService.setPosition(position.getX(), position.getY());
    }
}
