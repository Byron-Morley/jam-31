package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.DestinationComponent;
import com.byron.components.StatusComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import java.util.Stack;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final IPlayerInputManager playerInputManager;
    private final IDungeonService dungeonService;

    public PlayerInputSystem(IPlayerInputManager playerInputManager, IDungeonService dungeonService) {
        super(Family.all(KeyboardComponent.class).get());
        this.playerInputManager = playerInputManager;
        this.dungeonService = dungeonService;
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {
        StatusComponent status = sm.get(player);
        Vector2 position = Mappers.position.get(player).position;

        Stack<Direction> movementKeysPressed = playerInputManager.getMovementKeysPressed();
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();

        if (status.getAction() == Action.WALKING) return;

        if (movementKeysPressed.size() > 0) {
            Direction direction = movementKeysPressed.pop();
            status.setDirection(direction);

            int targetX = (int) position.x, targetY = (int) position.y;
            switch (direction) {
                case UP:
                    targetY++;
                    break;
                case LEFT:
                    targetX--;
                    break;
                case DOWN:
                    targetY--;
                    break;
                case RIGHT:
                    targetX++;
                    break;
                default:
                    return;
            }

            if (dungeonService.isWalkable(targetX, targetY)) {
                status.setAction(Action.WALKING);
                player.add(new DestinationComponent(targetX, targetY));
            }
        }
    }
}