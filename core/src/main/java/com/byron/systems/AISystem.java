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
    Entity player;
    ImmutableArray<Entity> entityImmutableArray;

    public AISystem(IDungeonService dungeonService) {
        super(Family.all(AIComponent.class).get());
        //this.cameraService = cameraService;
        this.dungeonService = dungeonService;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        //player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        entityImmutableArray = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }
    @Override
    protected void processEntity(Entity enemy, float deltaTime) {
        //Vector2 position = pm.get(entity).position;
        //cameraService.setPosition(position.x, position.y);
        StatusComponent status = sm.get(enemy);
        BodyComponent bodyComponent = bm.get(enemy);
        Vector2 position = Mappers.position.get(enemy).position;
        player = entityImmutableArray.first();
        Vector2 playerPosition = Mappers.position.get(player).position;


        if (status.getAction() == Action.WALKING) return;
        //status.setAction(Action.STANDING);

        //status.setDirection(Direction.UP);
        float xDelta = playerPosition.x - position.x;
        float yDelta = playerPosition.y - position.y;
        if(Math.abs(xDelta) >= Math.abs(yDelta)) {
            if(xDelta < 0 && dungeonService.isWalkable((int) position.x - 1, (int) (position.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.LEFT);
                enemy.add(new DestinationComponent(position.x - 1, position.y));
            }
            if(xDelta > 0 && dungeonService.isWalkable((int) position.x + 1, (int) (position.y))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.RIGHT);
                enemy.add(new DestinationComponent(position.x + 1, position.y));
            }
        }
        if(Math.abs(xDelta) < Math.abs(yDelta)) {
            if(yDelta < 0 && dungeonService.isWalkable((int) position.x, (int) (position.y - 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.DOWN);
                enemy.add(new DestinationComponent(position.x, position.y - 1));
            }
            if(yDelta > 0 && dungeonService.isWalkable((int) position.x, (int) (position.y + 1))) {
                status.setAction(Action.WALKING);
                status.setDirection(Direction.UP);
                enemy.add(new DestinationComponent(position.x, position.y + 1));
            }
        }
    }
}