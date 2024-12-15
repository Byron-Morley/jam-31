package com.byron.systems;

import static com.badlogic.gdx.Input.Keys.E;
import static com.badlogic.gdx.Input.Keys.NUM_1;
import static com.badlogic.gdx.Input.Keys.NUM_2;
import static com.badlogic.gdx.Input.Keys.O;
import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.byron.constants.Weapon.REGULAR_SWORD;
import static com.byron.models.status.Direction.UP;
import static com.byron.utils.Messages.PLAY_SOUND;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.DestinationComponent;
import com.byron.components.StatusComponent;
import com.byron.components.WeaponComponent;
import com.byron.components.hud.ScoreEvent;
import com.byron.components.hud.TextComponent;
import com.byron.components.player.KeyboardComponent;
import com.byron.components.sprite.SpriteComponent;
import com.byron.components.visuals.ColorInterpComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.components.weapons.DamageComponent;
import com.byron.components.weapons.WeaponTag;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IAgentService;
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
    IAgentService agentService;

    public PlayerInputSystem(IPlayerInputManager playerInputManager, IDungeonService dungeonService, IAgentService agentService) {
        super(Family.all(KeyboardComponent.class).get());
        this.playerInputManager = playerInputManager;
        this.dungeonService = dungeonService;
        this.agentService = agentService;
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
            switch (direction) {
                case UP:
                case LEFT:
                case DOWN:
                case RIGHT:
                    break;
                default:
                    return;
            }

            status.setDirection(direction);
            Vector2 target = position.cpy().add(direction.vector);

            if (dungeonService.isWalkable(target) && agentService.isPositionFree((int) target.x, (int) target.y)) {
                status.setAction(Action.WALKING);
                player.add(new DestinationComponent(target));
            }
            System.out.println("walking message");
            //MessageManager.getInstance().dispatchMessage(PLAY_SOUND, "swoosh");
        }

        testScore();
        testDamage(position);
        testWeapon(player);
    }

    // TODO: Remove, this is only for testing the score widget
    private void testScore() {
        if (Gdx.input.isKeyJustPressed(NUM_1)) {
            getEngine().addEntity(new Entity().add(new ScoreEvent(500)));
        }
        if (Gdx.input.isKeyJustPressed(NUM_2)) {
            getEngine().addEntity(new Entity().add(new ScoreEvent(1000)));
        }
    }

    // TODO: Remove, this is only for testing the fading damage numbers
    private void testDamage(Vector2 position) {
        if (!Gdx.input.isKeyJustPressed(O)) return;

        Entity damage = new Entity()
            .add(new ColorInterpComponent(RED, CLEAR))
            .add(new PositionInterpComponent(position.cpy(), position.cpy().add(UP.vector)))
            .add(new TextComponent(new BitmapFont(Gdx.files.internal("raw/fonts/pixelFont.fnt")), "123", 0.05f));
        getEngine().addEntity(damage);
    }

    // TODO: Remove, this is only for testing giving the player a weapon
    private void testWeapon(Entity player) {
        if (!Gdx.input.isKeyJustPressed(E)) return;

        Sprite weaponSprite = SpriteFactory.getSprite(REGULAR_SWORD.name);
        weaponSprite.setOriginCenter();
        Entity weapon = new Entity()
            .add(new WeaponTag())
            .add(new SpriteComponent(weaponSprite))
            .add(new DamageComponent(REGULAR_SWORD.damage));
        getEngine().addEntity(weapon);

        player.add(new WeaponComponent(weapon));
    }
}