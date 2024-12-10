package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.BodyComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import java.util.Stack;

public class PlayerInputSystem extends IteratingSystem {

    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<BodyComponent> bm = Mappers.body;
    private final ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private final IPlayerInputManager playerInputManager;
    OrthographicCamera camera;
    IDungeonService dungeonService;

    public PlayerInputSystem(IPlayerInputManager playerInputManager, IDungeonService dungeonService) {
        super(Family.all(KeyboardComponent.class).get());
        this.playerInputManager = playerInputManager;
        this.dungeonService = dungeonService;
        camera = GameResources.get().getCamera();
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {
        StatusComponent status = sm.get(player);
        BodyComponent bodyComponent = bm.get(player);
        Vector2 position = Mappers.position.get(player).position;

        Stack<Direction> movementKeysPressed = playerInputManager.getMovementKeysPressed();
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();

        if (movementKeysPressed.size() > 0) {
            status.setAction(Action.WALKING);

            Direction direction = movementKeysPressed.pop();

            if (direction.equals(Direction.UP)) {
                status.setDirection(Direction.UP);
                if (dungeonService.isWalkable((int) position.x, (int) (position.y + 1))) {
                    position.y++;
                }
            }
            if (direction.equals(Direction.DOWN)) {
                status.setDirection(Direction.DOWN);
                if (dungeonService.isWalkable((int) position.x, (int) (position.y - 1))) {
                    position.y--;
                }
            }
            if (direction.equals(Direction.LEFT)) {
                status.setDirection(Direction.LEFT);
                if (dungeonService.isWalkable((int) (position.x - 1), (int) position.y)) {
                    position.x--;
                }
            }
            if (direction.equals(Direction.RIGHT)) {
                status.setDirection(Direction.RIGHT);
                if (dungeonService.isWalkable((int) (position.x + 1), (int) position.y)) {
                    position.x++;
                }
            }
        } else {
            status.setAction(Action.STANDING);
        }
    }
}