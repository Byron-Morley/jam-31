package com.byron.systems;

import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.byron.models.status.Direction.UP;
import static com.byron.utils.Config.ENEMY_ATTACK_DISTANCE;
import static com.byron.utils.Config.FONT;
import static com.byron.utils.Config.PERCENTAGE_CHANCE_ENEMY_MOVES_RANDOM_DIRECTION;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AIComponent;
import com.byron.components.AgentComponent;
import com.byron.components.DestinationComponent;
import com.byron.components.HUDProgressBarComponent;
import com.byron.components.StatusComponent;
import com.byron.components.TakeDamageComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.hud.TextComponent;
import com.byron.components.visuals.ColorInterpComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IDungeonService;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.Stats;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import java.util.Random;

public class AISystem extends IteratingSystem {

    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    private final IPlayerInputManager playerInputManager;
    private final IDungeonService dungeonService;
    private final IAgentService agentService;
    private final Random random;
    private final BitmapFont bitmapFont;
    private final Engine engine;

    public AISystem(IDungeonService dungeonService, IAgentService agentService, IPlayerInputManager playerInputManager) {
        super(Family.all(AIComponent.class).get());
        this.playerInputManager = playerInputManager;
        this.dungeonService = dungeonService;
        this.agentService = agentService;
        random = new Random();
        bitmapFont = new BitmapFont(Gdx.files.internal(FONT));
        this.engine = GameResources.get().getEngine();
    }

    @Override
    protected void processEntity(Entity enemy, float deltaTime) {
        AIComponent aiComponent = Mappers.ai.get(enemy);

        StatusComponent status = sm.get(enemy);
        Vector2 enemyPosition = Mappers.position.get(enemy).position;

        Entity player = agentService.getPlayer();
        AgentComponent agentComponent = player.getComponent(AgentComponent.class);
        Vector2 playerPosition = Mappers.position.get(player).position;
        Vector2 enemyDirection = Mappers.status.get(enemy).getDirection().vector;
        boolean aiCanMoveNow;

        if (playerInputManager.playerHasTakenTheirTurn() && inRangeOfTarget(enemyPosition, playerPosition)) {
            aiComponent.moveCounter--;

            aiCanMoveNow = aiComponent.canMove();
            if (aiCanMoveNow) {
                if (random.nextInt(100) < PERCENTAGE_CHANCE_ENEMY_MOVES_RANDOM_DIRECTION &&
                    Mappers.status.get(enemy).getAction() != Action.ENGAGING &&
                    aiComponent.state != AIComponent.State.ATTACKING
                ) {
//                      Move Random Direction
                    // Note from Johnny: don't move if the enemy is already engaged with the player (already added to the condition)
                } else {
                    basicFollowMovement(enemy, status, playerPosition, enemyPosition);
                }
            }

            // Attack the player if they're in front if the enemy on their turn
            if (aiCanMoveNow &&
                (enemyDirection.hasSameDirection(Direction.UP.vector) && (int) enemyPosition.x == (int) playerPosition.x && (int) enemyPosition.y + 1 == (int) playerPosition.y
                    || enemyDirection.hasSameDirection(Direction.DOWN.vector) && (int) enemyPosition.x == (int) playerPosition.x && (int) enemyPosition.y - 1 == (int) playerPosition.y
                    || enemyDirection.hasSameDirection(Direction.LEFT.vector) && (int) enemyPosition.x - 1 == (int) playerPosition.x && (int) enemyPosition.y == (int) playerPosition.y
                    || enemyDirection.hasSameDirection(Direction.RIGHT.vector) && (int) enemyPosition.x + 1 == (int) playerPosition.x && (int) enemyPosition.y == (int) playerPosition.y
                )) {
                aiComponent.state = AIComponent.State.ATTACKING;//Mappers.status.get(enemy).setAction(Action.ENGAGING);

                Stats enemyStats = getStats(enemy);

                if (agentComponent.damageTimer <= 0) {
                    player.add(new TakeDamageComponent());
                    takeDamage(player, enemyStats.getAttack());
                    showNumbers(playerPosition, Color.RED, "-" + enemyStats.getAttack());
                    agentComponent.damageTimer = 100f;
                }
            }
        }
        agentComponent.damageTimer -= deltaTime;
    }

    private static Stats getStats(Entity entity) {
        return Mappers.agent.get(entity).getStats();
    }

    private void takeDamage(Entity player, int damage) {
        ImmutableArray<Entity> progressBars = engine.getEntitiesFor(Family.all(HUDProgressBarComponent.class).get());

        Entity LifeEntity = progressBars.get(0);
        Entity ArmorEntity = progressBars.get(1);

        HUDProgressBarComponent lifeBar = LifeEntity.getComponent(HUDProgressBarComponent.class);
        HUDProgressBarComponent armorBar = ArmorEntity.getComponent(HUDProgressBarComponent.class);

        float damageValue = (float) damage / 100;
        float currentArmor = armorBar.getProgress();

        if (currentArmor > 0) {
            float remainingDamage = damageValue;
            float newArmorValue = Math.max(0, currentArmor - remainingDamage);
            armorBar.setProgress(newArmorValue);

            // If there's remaining damage after armor is depleted
            if (currentArmor < remainingDamage) {
                float damageToLife = remainingDamage - currentArmor;
                float newLifeValue = Math.max(0, lifeBar.getProgress() - damageToLife);
                lifeBar.setProgress(newLifeValue);
            }
        } else {
            // If no armor, damage goes straight to life
            float newLifeValue = Math.max(0, lifeBar.getProgress() - damageValue);
            lifeBar.setProgress(newLifeValue);
        }
        Stats playerStats = getStats(player);
        playerStats.setHealth((int) MathUtils.map(0f, 1f, 0, 100, lifeBar.progress));
        if (lifeBar.progress <= 0) getEngine().removeEntity(player);
    }


    private void showNumbers(Vector2 position, Color color, String text) {
        Entity damage = new Entity()
            .add(new ColorInterpComponent(color, CLEAR))
            .add(new PositionInterpComponent(position, position.cpy().add(UP.vector)))
            .add(new TextComponent(bitmapFont, text, 0.05f));
        getEngine().addEntity(damage);
    }

    private void basicFollowMovement(Entity enemy, StatusComponent status, Vector2 playerPosition, Vector2 enemyPosition) {
        AIComponent aiComponent = Mappers.ai.get(enemy);
        if (status.getAction() == Action.WALKING) return;

        float xDelta = playerPosition.x - enemyPosition.x;
        float yDelta = playerPosition.y - enemyPosition.y;

        if (Math.abs(xDelta) >= Math.abs(yDelta)) {
            if (xDelta < 0 && isWalkable((int) enemyPosition.x - 1, (int) (enemyPosition.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.LEFT);
                enemy.add(new DestinationComponent(enemyPosition.x - 1, enemyPosition.y));
            }
            if (xDelta > 0 && isWalkable((int) enemyPosition.x + 1, (int) (enemyPosition.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.RIGHT);
                enemy.add(new DestinationComponent(enemyPosition.x + 1, enemyPosition.y));
            }
        }
        if (Math.abs(xDelta) < Math.abs(yDelta)) {
            if (yDelta < 0 && isWalkable((int) enemyPosition.x, (int) (enemyPosition.y - 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.DOWN);
                enemy.add(new DestinationComponent(enemyPosition.x, enemyPosition.y - 1));
            }
            if (yDelta > 0 && isWalkable((int) enemyPosition.x, (int) (enemyPosition.y + 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.UP);
                enemy.add(new DestinationComponent(enemyPosition.x, enemyPosition.y + 1));
            }
        }

        // Turn toward player when you're adjacent
        if ((int) enemyPosition.x == (int) playerPosition.x && (int) enemyPosition.y + 1 == (int) playerPosition.y) {
            status.setDirection(Direction.UP);
            aiComponent.state = AIComponent.State.ATTACKING;//Mappers.status.get(enemy).setAction(Action.ENGAGING);
        }
        if ((int) enemyPosition.x == (int) playerPosition.x && (int) enemyPosition.y - 1 == (int) playerPosition.y) {
            //
            status.setDirection(Direction.DOWN);
            aiComponent.state = AIComponent.State.ATTACKING;//Mappers.status.get(enemy).setAction(Action.ENGAGING);
        }
        if ((int) enemyPosition.x - 1 == (int) playerPosition.x && (int) enemyPosition.y == (int) playerPosition.y) {
            status.setDirection(Direction.LEFT);
            aiComponent.state = AIComponent.State.ATTACKING;//Mappers.status.get(enemy).setAction(Action.ENGAGING);
        }
        if ((int) enemyPosition.x + 1 == (int) playerPosition.x && (int) enemyPosition.y == (int) playerPosition.y) {
            status.setDirection(Direction.RIGHT);
            aiComponent.state = AIComponent.State.ATTACKING;//Mappers.status.get(enemy).setAction(Action.ENGAGING);
        }
    }

    //    Prevents Overlap
    public boolean isWalkable(int x, int y) {
        return (dungeonService.isWalkable(x, y) && agentService.isPositionFree(x, y));
    }

    private boolean inRangeOfTarget(Vector2 enemy, Vector2 player) {
        float distance = enemy.dst(player);
        return distance <= ENEMY_ATTACK_DISTANCE;
    }
}