package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.BodyComponent;
import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Action;
import com.byron.utils.Mappers;
import com.byron.models.status.Direction;

import java.util.Stack;

public class PlayerInputSystem extends IteratingSystem {
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private ComponentMapper<BodyComponent> bm = Mappers.body;
    private ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private IPlayerInputManager playerInputManager;
    OrthographicCamera camera;

    public PlayerInputSystem(IPlayerInputManager playerInputManager) {
        super(Family.all(KeyboardComponent.class).get());
        this.playerInputManager = playerInputManager;
        camera = GameResources.get().getCamera();
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {

        StatusComponent status = sm.get(player);
        BodyComponent bodyComponent = bm.get(player);

        Stack<Direction> movementKeysPressed = playerInputManager.getMovementKeysPressed();
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();

        if (movementKeysPressed.size() > 0) {
            status.setAction(Action.WALKING);

            if (movementKeysPressed.contains(Direction.UP)) {
                status.setDirection(Direction.UP);
                bodyComponent.body.position.y += 0.05f;
            }
            if (movementKeysPressed.contains(Direction.DOWN)) {
                status.setDirection(Direction.DOWN);
                bodyComponent.body.position.y -= 0.05f;
            }
            if (movementKeysPressed.contains(Direction.LEFT)) {
                status.setDirection(Direction.LEFT);
                bodyComponent.body.position.x -= 0.05f;
            }
            if (movementKeysPressed.contains(Direction.RIGHT)) {
                status.setDirection(Direction.RIGHT);
                bodyComponent.body.position.x += 0.05f;
            }
        } else {
            status.setAction(Action.STANDING);
        }
    }
}
