package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.byron.components.BodyComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.status.Action;
import com.byron.utils.Mappers;
import com.byron.models.status.Direction;

import static com.byron.utils.Config.*;

public class PlayerInputSystem extends IteratingSystem {
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private ComponentMapper<BodyComponent> bm = Mappers.body;
    private ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private IPlayerInputManager playerInputManager;
    private long jumpPressedTime;
    private boolean jumpingPressed;

    public PlayerInputSystem(IPlayerInputManager playerInputManager) {
        super(Family.all(KeyboardComponent.class).get());
        this.playerInputManager = playerInputManager;
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {
        StatusComponent status = sm.get(player);
//        Direction newDirection = playerInputManager.getPlayersNewDirection();
        BodyComponent bodyComponent = bm.get(player);
        VelocityComponent velocityComponent = vm.get(player);

        processInput(status, bodyComponent, velocityComponent, deltaTime);
    }


    private void processInput(StatusComponent status, BodyComponent bodyComponent, VelocityComponent velocityComponent, float deltaTime) {

        Direction direction = playerInputManager.getPlayersNewDirection();

        if (direction != null) {
            status.setDirection(direction);
            if (status.getDirection() == Direction.LEFT) {

                if (!(status.getAction() == Action.JUMPING || status.getAction() == Action.FALLING)) {
                    status.setAction(Action.WALKING);
                }

                velocityComponent.acceleration.x = (-MAX_VELOCITY * ACCELERATION * deltaTime);

            } else if (status.getDirection() == Direction.RIGHT) {
                if (!(status.getAction() == Action.JUMPING || status.getAction() == Action.FALLING)) {
                    status.setAction(Action.WALKING);
                }
                velocityComponent.acceleration.x = (MAX_VELOCITY * ACCELERATION * deltaTime);
            }
        } else {
            velocityComponent.acceleration.x = 0;
            velocityComponent.velocity.x = 0;
        }

        if (playerInputManager.isPressingJump()) {
            if (!(status.getAction().equals(Action.JUMPING) || status.getAction().equals(Action.FALLING))) {

                status.setAction(Action.JUMPING);
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                velocityComponent.velocity.y = MAX_JUMP_SPEED;

            } else {
                if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {

                    velocityComponent.acceleration.y = GRAVITY;
                    jumpingPressed = false;

                } else {
                    if (jumpingPressed) {
                        velocityComponent.velocity.y = MAX_JUMP_SPEED;
                    }
                }
            }
        } else {
            if (status.getAction() == Action.JUMPING || status.getAction() == Action.FALLING) {
                velocityComponent.acceleration.y = GRAVITY;
            }
        }
    }
}
