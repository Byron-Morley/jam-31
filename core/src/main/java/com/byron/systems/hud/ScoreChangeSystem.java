package com.byron.systems.hud;

import static com.byron.constants.MilestoneType.MAJOR;
import static com.byron.constants.MilestoneType.MINOR;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.byron.components.events.MilestoneEvent;
import com.byron.components.hud.ScoreEvent;
import com.byron.components.hud.ScoreWidgetComponent;
import com.byron.components.hud.TextComponent;
import com.byron.utils.Mappers;

public class ScoreChangeSystem extends IteratingSystem {

    private ImmutableArray<Entity> scoreWidgets;

    public ScoreChangeSystem() {
        super(Family.all(ScoreEvent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        scoreWidgets = engine.getEntitiesFor(Family.all(ScoreWidgetComponent.class, TextComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        int score = handleScoreEvent(entity);
        checkMilestones(score);
    }

    private int handleScoreEvent(Entity entity) {
        int scoreChange = Mappers.scoreEvent.get(entity).scoreChange;
        TextComponent scoreText = Mappers.text.get(scoreWidgets.first());
        int score = Math.max(Integer.parseInt(scoreText.text) + scoreChange, 0);
        scoreText.text = String.valueOf(score);
        getEngine().removeEntity(entity);
        return score;
    }

    private void checkMilestones(int score) {
        if (score % 10000 == 0) {
            getEngine().addEntity(new Entity().add(new MilestoneEvent(MAJOR)));
        } else if (score % 1000 == 0) {
            getEngine().addEntity(new Entity().add(new MilestoneEvent(MINOR)));
        }
    }
}