package com.byron.systems.weapons;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AgentComponent;
import com.byron.components.PositionComponent;
import com.byron.components.SlashComponent;
import com.byron.components.StatusComponent;
import com.byron.components.WeaponComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.Agent;
import com.byron.models.Stats;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class WeaponSystem extends IteratingSystem {

    ImmutableArray<Entity> enemies;
    private final IPlayerInputManager playerInputManager;
    private final SpriteBatch spriteBatch;
    IAgentService agentService;

    public WeaponSystem(IAgentService agentService, IPlayerInputManager playerInputManager) {
        super(Family.all(
            AgentComponent.class,
            StatusComponent.class,
            PositionComponent.class,
            WeaponComponent.class
        ).get());
        this.playerInputManager = playerInputManager;
        this.agentService = agentService;
        spriteBatch = GameResources.get().getBatch();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        enemies = engine.getEntitiesFor(Family.all(AgentComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite weapon = Mappers.weapon.get(entity).sprite;
        Vector2 direction = Mappers.status.get(entity).getDirection().vector;

        handleAttackAction(entity, weapon, direction);
        weapon.draw(spriteBatch);
    }

    private void handleAttackAction(Entity entity, Sprite weapon, Vector2 direction) {
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();
        if (actionKeysPressed.size() < 1) return;

        PlayerAction action = actionKeysPressed.pop();
        if (action != PlayerAction.ACTION) return;

        Vector2 position = Mappers.position.get(entity).position;

        entity.add(new SlashComponent(position, position.cpy().add(direction)));
        weapon.setRotation(direction.angleDeg() + 225f);

        Map<String, Agent> agents = agentService.getAgents();
        List<Agent> enemyAgents = agents.values().stream()
            .filter(agent -> "enemy".equals(agent.getFactionId()))
            .collect(Collectors.toList());

        Entity player = agentService.getPlayer();
        Vector2 playerPosition = Mappers.position.get(player).position;
        float playerX = playerPosition.x, playerY = playerPosition.y;

        if(direction.hasSameDirection(Direction.UP.vector)) {
            for( Entity targetEnemy : enemies ) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if((int) enemyPosition.x == (int) playerPosition.x || (int) enemyPosition.x == (int) playerPosition.x - 1 || (int) enemyPosition.x == (int) playerPosition.x + 1) {
                    if((int) enemyPosition.y == (int) playerPosition.y + 1) {
                        AgentComponent agentComponent = entity.getComponent(AgentComponent.class);
                        Stats stats = agentComponent.getStats();
                        stats.setHealth(stats.getHealth() - 50);
                    }
                }
            }
        }
        if(direction.hasSameDirection(Direction.DOWN.vector)) {
            for( Entity targetEnemy : enemies ) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if((int) enemyPosition.x == (int) playerPosition.x || (int) enemyPosition.x == (int) playerPosition.x - 1 || (int) enemyPosition.x == (int) playerPosition.x + 1) {
                    if((int) enemyPosition.y == (int) playerPosition.y - 1) {
                        AgentComponent agentComponent = entity.getComponent(AgentComponent.class);
                        Stats stats = agentComponent.getStats();
                        stats.setHealth(stats.getHealth() - 50);
                    }
                }
            }
        }
        if(direction.hasSameDirection(Direction.LEFT.vector)) {
            for( Entity targetEnemy : enemies ) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if((int) enemyPosition.y == (int) playerPosition.y || (int) enemyPosition.y == (int) playerPosition.y - 1 || (int) enemyPosition.y == (int) playerPosition.y + 1) {
                    if((int) enemyPosition.x == (int) playerPosition.x - 1) {
                        AgentComponent agentComponent = entity.getComponent(AgentComponent.class);
                        Stats stats = agentComponent.getStats();
                        stats.setHealth(stats.getHealth() - 50);
                    }
                }
            }
        }
        if(direction.hasSameDirection(Direction.RIGHT.vector)) {
            for( Entity targetEnemy : enemies ) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if((int) enemyPosition.y == (int) playerPosition.y || (int) enemyPosition.y == (int) playerPosition.y - 1 || (int) enemyPosition.y == (int) playerPosition.y + 1) {
                    if((int) enemyPosition.x == (int) playerPosition.x + 1) {
                        AgentComponent agentComponent = entity.getComponent(AgentComponent.class);
                        Stats stats = agentComponent.getStats();
                        stats.setHealth(stats.getHealth() - 50);
                    }
                }
            }
        }

        // TODO: 12/11/2024 Play sound
    }
}