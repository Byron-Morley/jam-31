package com.byron.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.DestinationComponent;
import com.byron.components.PositionComponent;
import com.byron.components.SpeedComponent;
import com.byron.components.StatusComponent;
import com.byron.models.status.Action;
import com.byron.utils.Mappers;

public class SmoothMovementSystem extends IteratingSystem {

    private static final float ARRIVAL_THRESHOLD = 0.01f;

    public SmoothMovementSystem() {
        super(Family.all(
                PositionComponent.class,
                SpeedComponent.class,
                DestinationComponent.class,
                StatusComponent.class
            ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 position = Mappers.position.get(entity).position;
        float speed = Mappers.speed.get(entity).speed;
        Vector2 destination = Mappers.destination.get(entity).destination;
        StatusComponent status = Mappers.status.get(entity);

        Vector2 direction = new Vector2(destination).sub(position);
        float distance = direction.len();

        if (distance < ARRIVAL_THRESHOLD) {
            status.setAction(Action.STANDING);
            position.set(destination); // Snap to the target
            entity.remove(DestinationComponent.class); // Stop moving
            return;
        }

        direction.nor().scl(speed * deltaTime);
        position.add(direction);
    }
}