package com.byron.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.ExitComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;


public class CollisionSystem extends IteratingSystem {

    private ImmutableArray<Entity> players;

    public CollisionSystem() {
        super(Family.all(ExitComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (players.size() == 0) return;

        Entity player = players.first();
        Vector2 playerPos = Mappers.position.get(player).position;
        Vector2 pentagramPos = Mappers.position.get(entity).position.cpy().add(0.5f, 0.5f);

        if (playerPos.dst(pentagramPos) > 1f) return;

        GameResources.get().sameSeed = false;
        GameResources.get().setRestart(true);
    }
}