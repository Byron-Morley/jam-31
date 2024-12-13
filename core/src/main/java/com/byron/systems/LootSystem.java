package com.byron.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.byron.components.*;
import com.byron.components.hud.ColorInterpComponent;
import com.byron.components.hud.PositionInterpComponent;
import com.byron.components.hud.ScoreEvent;
import com.byron.components.hud.TextComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentService;
import com.byron.utils.Mappers;

import static com.badlogic.gdx.graphics.Color.*;
import static com.byron.models.status.Direction.UP;
import static com.byron.utils.Config.FONT;

public class LootSystem extends IteratingSystem {

    IAgentService agentService;
    Engine engine;

    public LootSystem(IAgentService agentService) {
        super(Family.all(
            LootComponent.class
        ).get());
        this.agentService = agentService;
        this.engine = GameResources.get().getEngine();
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        Entity player = agentService.getPlayer();
        LootComponent lootComponent = entity.getComponent(LootComponent.class);

        PositionComponent playerPosition = Mappers.position.get(player);
        PositionComponent lootPosition = Mappers.position.get(entity);

        if (playerPosition.position.equals(lootPosition.position)) {
            engine.addEntity(new Entity().add(new ScoreEvent(lootComponent.getValue())));

            Entity damage = new Entity()
                .add(new ColorInterpComponent(GOLD, CLEAR))
                .add(new PositionInterpComponent(playerPosition.position, playerPosition.position.cpy().add(UP.vector)))
                .add(new TextComponent(new BitmapFont(Gdx.files.internal(FONT)), "" + lootComponent.getValue(), 0.05f));
            getEngine().addEntity(damage);

            engine.removeEntity(entity);
        }
    }
}
