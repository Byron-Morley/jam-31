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
        PositionComponent position = Mappers.position.get(player);

        Stack<Direction> movementKeysPressed = playerInputManager.getMovementKeysPressed();
        Stack<PlayerAction> actionKeysPressed = playerInputManager.getActionKeyPressed();


    }
}
