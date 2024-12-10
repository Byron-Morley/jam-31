package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.AIComponent;
import com.byron.components.BodyComponent;
//import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
//import com.byron.components.player.KeyboardComponent;
//import com.byron.interfaces.ICameraService;
import com.byron.interfaces.IDungeonService;
//import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;
import com.byron.utils.Mappers;

public class AISystem extends IteratingSystem {

    private final ComponentMapper<StatusComponent> sm = Mappers.status;
    private final ComponentMapper<BodyComponent> bm = Mappers.body;
    private final ComponentMapper<VelocityComponent> vm = Mappers.velocity;
    //private final IPlayerInputManager playerInputManager;
    IDungeonService dungeonService;

    public AISystem(IDungeonService dungeonService) {
        super(Family.all(AIComponent.class).get());
        //this.cameraService = cameraService;
        this.dungeonService = dungeonService;
    }

    @Override
    protected void processEntity(Entity enemy, float deltaTime) {
        //Vector2 position = pm.get(entity).position;
        //cameraService.setPosition(position.x, position.y);
        StatusComponent status = sm.get(enemy);
        BodyComponent bodyComponent = bm.get(enemy);
        Vector2 position = Mappers.position.get(enemy).position;

        status.setAction(Action.WALKING);

        status.setDirection(Direction.UP);
        if (dungeonService.isWalkable((int) position.x, (int) (position.y + 1))) {
            position.y++;
        }
    }
}