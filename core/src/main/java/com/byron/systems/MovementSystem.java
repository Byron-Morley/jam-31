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

import static com.byron.utils.Config.*;


public class MovementSystem extends IteratingSystem {
    private Action lastAction = Action.STANDING;
    private ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private ComponentMapper<BodyComponent> bm = Mappers.body;
    private ComponentMapper<PlayerComponent> playm = Mappers.player;

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

        //slows the player down when not accelerating
        if (velocityComponent.acceleration.x == 0) velocityComponent.velocity.x *= DAMP;

        //calculate velocity and set position
        velocityComponent.velocity.add(velocityComponent.acceleration.x, velocityComponent.acceleration.y);

        //control the jumping height
        if (velocityComponent.velocity.y > MAX_JUMP_SPEED) velocityComponent.velocity.y = MAX_JUMP_SPEED;

        //prevent player from exceeding maximum velocity
        if (velocityComponent.velocity.x > MAX_VELOCITY) velocityComponent.velocity.x = MAX_VELOCITY;
        if (velocityComponent.velocity.x < -MAX_VELOCITY) velocityComponent.velocity.x = -MAX_VELOCITY;


        bodyComponent.body.previous.set(bodyComponent.body.position.x, bodyComponent.body.position.y);
        bodyComponent.body.position.add(velocityComponent.velocity.x * deltaTime, velocityComponent.velocity.y * deltaTime);


        if (velocityComponent.velocity.x == 0) {
            if (!(status.getAction() == Action.JUMPING || status.getAction() == Action.FALLING)) {
                status.setAction(Action.STANDING);
            }
        }

        if (velocityComponent.velocity.y == 0 && status.getAction() == Action.JUMPING) {
            status.setAction(Action.FALLING);
        }

        if (bodyComponent.body.getSpeed().y < -0.03f) {
            status.setAction(Action.FALLING);
        }

        if (bodyComponent.body.getSpeed().y < 0f) {
            status.setAction(Action.FALLING);
        }
    }
}
