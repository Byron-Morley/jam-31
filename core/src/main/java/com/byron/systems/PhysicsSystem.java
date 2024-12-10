package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.BodyComponent;
import com.byron.components.PositionComponent;
import com.byron.models.physics.Body;
import com.byron.utils.Mappers;

public class PhysicsSystem extends IteratingSystem {

    private final ComponentMapper<PositionComponent> pm = Mappers.position;
    private final ComponentMapper<BodyComponent> bm = Mappers.body;

    public PhysicsSystem() {
        super(Family.all(BodyComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = pm.get(entity);
        Vector2 position = positionComponent.position;
        BodyComponent bodyComponent = bm.get(entity);

        if (positionComponent.overridePhysicsSystem) {
            positionComponent.overridePhysicsSystem = false;
            bodyComponent.body.setPosition(new Vector2(position.x, position.y));
        } else {
            Body body = bodyComponent.body;

            position.x = body.calculateXPosition();
            position.y = body.calculateYPosition();
        }
    }
}