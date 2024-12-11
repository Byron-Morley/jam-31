package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.BodyComponent;
import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.models.status.Action;
import com.byron.utils.Mappers;


public class MovementSystem extends IteratingSystem {

    private final Action lastAction = Action.STANDING;
    private final ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<BodyComponent> bm = Mappers.body;
    private final ComponentMapper<PlayerComponent> playm = Mappers.player;

    public MovementSystem() {
        super(Family.all(
            PlayerComponent.class,
            StatusComponent.class,
            PositionComponent.class,
            VelocityComponent.class,
            BodyComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocityComponent = vm.get(entity);
        StatusComponent status = sm.get(entity);
        BodyComponent bodyComponent = bm.get(entity);


//        // Apply damping when not accelerating
//        if (velocityComponent.acceleration.x == 0) velocityComponent.velocity.x *= DAMP;
//        if (velocityComponent.acceleration.y == 0) velocityComponent.velocity.y *= DAMP;
//
//        // Add acceleration to velocity
//        velocityComponent.velocity.add(velocityComponent.acceleration.x, velocityComponent.acceleration.y);
//
//        // Clamp velocity to maximum speed
//        if (velocityComponent.velocity.x > MAX_VELOCITY) velocityComponent.velocity.x = MAX_VELOCITY;
//        if (velocityComponent.velocity.x < -MAX_VELOCITY) velocityComponent.velocity.x = -MAX_VELOCITY;
//        if (velocityComponent.velocity.y > MAX_VELOCITY) velocityComponent.velocity.y = MAX_VELOCITY;
//        if (velocityComponent.velocity.y < -MAX_VELOCITY) velocityComponent.velocity.y = -MAX_VELOCITY;
//
//        // Store previous position and update current position
//        bodyComponent.body.previous.set(bodyComponent.body.position);
//        bodyComponent.body.position.add(
//            velocityComponent.velocity.x * deltaTime,
//            velocityComponent.velocity.y * deltaTime
//        );
//
//        // Prevent moving outside boundaries (if needed)
//        if (bodyComponent.body.position.x < 0) bodyComponent.body.position.x = 0;
//        if (bodyComponent.body.position.y < 0) bodyComponent.body.position.y = 0;
//
//        // Update status based on movement
//        if (velocityComponent.velocity.x == 0 && velocityComponent.velocity.y == 0) {
//            status.setAction(Action.STANDING);
//        } else {
//            status.setAction(Action.WALKING);
//        }
    }
}