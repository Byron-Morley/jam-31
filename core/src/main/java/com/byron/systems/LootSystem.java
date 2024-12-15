package com.byron.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.*;
import com.byron.components.hud.ScoreEvent;
import com.byron.components.hud.TextComponent;
import com.byron.components.visuals.ColorInterpComponent;
import com.byron.components.visuals.PositionInterpComponent;
import com.byron.engine.GameResources;
import com.byron.interfaces.IAgentService;
import com.byron.utils.Mappers;

import static com.badlogic.gdx.graphics.Color.*;
import static com.byron.models.status.Direction.UP;
import static com.byron.utils.Config.FONT;

public class  LootSystem extends IteratingSystem {

    IAgentService agentService;
    Engine engine;
    BitmapFont bitmapFont;

    public LootSystem(IAgentService agentService) {
        super(Family.all(
            LootComponent.class
        ).get());
        this.agentService = agentService;
        this.engine = GameResources.get().getEngine();
        bitmapFont = new BitmapFont(Gdx.files.internal(FONT));
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        Entity player = agentService.getPlayer();
        LootComponent lootComponent = entity.getComponent(LootComponent.class);

        PositionComponent playerPosition = Mappers.position.get(player);
        PositionComponent lootPosition = Mappers.position.get(entity);

        if (playerPosition.position.equals(lootPosition.position)) {
            engine.addEntity(new Entity().add(new ScoreEvent(lootComponent.getValue())));

            if (lootComponent.isArmor()) {
                addToArmor(lootComponent.getValue());
                showNumbers(playerPosition.position, GREEN, "+" + lootComponent.getValue());
            } else {
                showNumbers(playerPosition.position, GOLD, "" + lootComponent.getValue());
            }

            engine.removeEntity(entity);
        }
    }

    private void addToArmor(int value) {
        ImmutableArray<Entity> progressBars = engine.getEntitiesFor(Family.all(HUDProgressBarComponent.class).get());
        Entity ArmorEntity = progressBars.get(1);
        HUDProgressBarComponent armorBar = ArmorEntity.getComponent(HUDProgressBarComponent.class);

        float newProgress = Math.min(1, armorBar.getProgress() + (float) value / 100);
        armorBar.setProgress(newProgress);
    }

    private void showNumbers(Vector2 position, Color color, String text) {
        Entity damage = new Entity()
            .add(new ColorInterpComponent(color, CLEAR))
            .add(new PositionInterpComponent(position, position.cpy().add(UP.vector)))
            .add(new TextComponent(bitmapFont, text, 0.05f));
        getEngine().addEntity(damage);
    }
}
