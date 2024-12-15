package com.byron.systems.weapons;

import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.byron.models.status.Direction.UP;
import static com.byron.utils.Config.FONT;
import static com.byron.utils.Messages.PLAY_SOUND;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AgentComponent;
import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.TakeDamageComponent;
import com.byron.components.WeaponComponent;
import com.byron.components.hud.TextComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.components.visuals.ColorInterpComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.components.visuals.RotationInterpComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentService;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.Stats;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

import java.util.Stack;

public class WeaponSystem extends IteratingSystem {

    ImmutableArray<Entity> enemies;
    private final IPlayerInputManager playerInputManager;
    private final SpriteBatch spriteBatch;
    IAgentService agentService;
    BitmapFont bitmapFont;
    Engine engine;

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
        bitmapFont = new BitmapFont(Gdx.files.internal(FONT));
        this.engine = GameResources.get().getEngine();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        enemies = engine.getEntitiesFor(Family.all(AgentComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity weapon = Mappers.weapon.get(entity).weapon;
        Sprite weaponSprite = Mappers.sprite.get(weapon).sprite;
        Vector2 direction = Mappers.status.get(entity).getDirection().vector;

        handleAttackAction(entity, weapon, direction);
        weaponSprite.draw(spriteBatch);
    }

    private void handleAttackAction(Entity wielder, Entity weapon, Vector2 direction) {
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();
        if (actionKeysPressed.size() < 1) return;

        PlayerAction action = actionKeysPressed.pop();
        if (action != PlayerAction.ACTION) return;

        Vector2 position = Mappers.position.get(wielder).position;

        setupAnimation(weapon, position, direction);
        applyDamage(wielder, position.x, position.y, direction);
        MessageManager.getInstance().dispatchMessage(PLAY_SOUND, "swoosh");
    }

    private void setupAnimation(Entity weapon, Vector2 playerPos, Vector2 direction) {
        Vector2 pos = playerPos.cpy().add(0.25f, 0.2f);
        Vector2 left = direction.cpy().rotateDeg(90f);
        Vector2 right = direction.cpy().rotateDeg(-90f);
        Vector2 startPos = pos.cpy().add(direction).add(left);
        Vector2 endPos = pos.cpy().add(direction).add(right);
        weapon.add(new PositionInterpComponent(startPos, endPos));
        float angle = direction.angleDeg() + 270f;
        weapon.add(new RotationInterpComponent(angle, angle - 90f));
    }

    private void applyDamage(Entity player, float playerX, float playerY, Vector2 direction) {
        if (direction.hasSameDirection(Direction.UP.vector)) {
            for (Entity targetEnemy : enemies) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if ((int) enemyPosition.x == (int) playerX
                    || (int) enemyPosition.x == (int) playerX - 1
                    || (int) enemyPosition.x == (int) playerX + 1) {
                    if ((int) enemyPosition.y == (int) playerY + 1) {
                        doAttack(player, targetEnemy, enemyPosition);
                    }
                }
            }
        }
        if (direction.hasSameDirection(Direction.DOWN.vector)) {
            for (Entity targetEnemy : enemies) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if ((int) enemyPosition.x == (int) playerX
                    || (int) enemyPosition.x == (int) playerX - 1
                    || (int) enemyPosition.x == (int) playerX + 1) {
                    if ((int) enemyPosition.y == (int) playerY - 1) {
                        doAttack(player, targetEnemy, enemyPosition);
                    }
                }
            }
        }
        if (direction.hasSameDirection(Direction.LEFT.vector)) {
            for (Entity targetEnemy : enemies) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if ((int) enemyPosition.y == (int) playerY
                    || (int) enemyPosition.y == (int) playerY - 1
                    || (int) enemyPosition.y == (int) playerY + 1) {
                    if ((int) enemyPosition.x == (int) playerX - 1) {
                        doAttack(player, targetEnemy, enemyPosition);
                    }
                }
            }
        }
        if (direction.hasSameDirection(Direction.RIGHT.vector)) {
            for (Entity targetEnemy : enemies) {
                Vector2 enemyPosition = Mappers.position.get(targetEnemy).position;
                if ((int) enemyPosition.y == (int) playerY
                    || (int) enemyPosition.y == (int) playerY - 1
                    || (int) enemyPosition.y == (int) playerY + 1) {
                    if ((int) enemyPosition.x == (int) playerX + 1) {
                        doAttack(player, targetEnemy, enemyPosition);
                    }
                }
            }
        }
    }

    private void doAttack(Entity player, Entity targetEnemy, Vector2 enemyPosition) {
        Stats playerStats = Mappers.agent.get(player).getStats();

        targetEnemy.add(new TakeDamageComponent());

        int multiplier = Mappers.damage.get(Mappers.weapon.get(player).weapon).damage;
        int damage = playerStats.getAttack() * multiplier;

        showNumbers(enemyPosition, Color.RED, "-" + damage);

        int enemyHealth = Mappers.health.get(targetEnemy).health;
        Mappers.health.get(targetEnemy).health = enemyHealth - damage;

        if (enemyHealth <= 0) getEngine().removeEntity(targetEnemy);
    }

    private void showNumbers(Vector2 position, Color color, String text) {
        Entity damage = new Entity()
            .add(new ColorInterpComponent(color, CLEAR))
            .add(new PositionInterpComponent(position, position.cpy().add(UP.vector)))
            .add(new TextComponent(bitmapFont, text, 0.05f));
        getEngine().addEntity(damage);
    }
}