package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AIComponent;
import com.byron.components.BodyComponent;
//import com.byron.components.PositionComponent;
import com.byron.components.DestinationComponent;
import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
//import com.byron.components.player.KeyboardComponent;
//import com.byron.interfaces.ICameraService;
import com.byron.components.player.PlayerComponent;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IDungeonService;
//import com.byron.interfaces.IPlayerInputManager;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.managers.PlayerInputManager;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import static com.byron.utils.Config.ENEMY_ATTACK_DISTANCE;

public class AISystem extends IteratingSystem {

    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private IPlayerInputManager playerInputManager;
    IDungeonService dungeonService;
    IAgentService agentService;

    public AISystem(IDungeonService dungeonService, IAgentService agentService, IPlayerInputManager playerInputManager) {
        super(Family.all(AIComponent.class).get());
        this.playerInputManager = playerInputManager;
        this.dungeonService = dungeonService;
        this.agentService = agentService;
    }

    @Override
    protected void processEntity(Entity enemy, float deltaTime) {

        AIComponent aiComponent = Mappers.ai.get(enemy);

        StatusComponent status = sm.get(enemy);
        Vector2 enemyPosition = Mappers.position.get(enemy).position;

        Entity player = agentService.getPlayer();
        Vector2 playerPosition = Mappers.position.get(player).position;

        if (playerInputManager.playerHasTakenTheirTurn() && inRangeOfTarget(enemyPosition, playerPosition)) {
            aiComponent.moveCounter --;

            if (aiComponent.canMove()) {
                basicFollowMovement(enemy, status, playerPosition, enemyPosition);
            }
        }
    }

    private void basicFollowMovement(Entity enemy, StatusComponent status, Vector2 playerPosition, Vector2 enemyPosition) {
        if (status.getAction() == Action.WALKING) return;
        //status.setAction(Action.STANDING);

        //status.setDirection(Direction.UP);
        float xDelta = playerPosition.x - enemyPosition.x;
        float yDelta = playerPosition.y - enemyPosition.y;
        if (Math.abs(xDelta) >= Math.abs(yDelta)) {
            if (xDelta < 0 && dungeonService.isWalkable((int) enemyPosition.x - 1, (int) (enemyPosition.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.LEFT);
                enemy.add(new DestinationComponent(enemyPosition.x - 1, enemyPosition.y));
            }
            if (xDelta > 0 && dungeonService.isWalkable((int) enemyPosition.x + 1, (int) (enemyPosition.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.RIGHT);
                enemy.add(new DestinationComponent(enemyPosition.x + 1, enemyPosition.y));
            }
        }
        if (Math.abs(xDelta) < Math.abs(yDelta)) {
            if (yDelta < 0 && dungeonService.isWalkable((int) enemyPosition.x, (int) (enemyPosition.y - 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.DOWN);
                enemy.add(new DestinationComponent(enemyPosition.x, enemyPosition.y - 1));
            }
            if (yDelta > 0 && dungeonService.isWalkable((int) enemyPosition.x, (int) (enemyPosition.y + 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.UP);
                enemy.add(new DestinationComponent(enemyPosition.x, enemyPosition.y + 1));
            }
        }
    }

    private boolean inRangeOfTarget(Vector2 enemy, Vector2 player) {
        float distance = enemy.dst(player);
        return distance <= ENEMY_ATTACK_DISTANCE;
    }
}