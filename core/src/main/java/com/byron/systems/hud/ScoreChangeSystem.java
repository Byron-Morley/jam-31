package com.byron.systems.hud;

import static com.byron.constants.MilestoneType.MAJOR;
import static com.byron.constants.MilestoneType.MINOR;
import static com.byron.constants.Weapon.REGULAR_SWORD;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.components.WeaponComponent;
import com.byron.components.events.MilestoneEvent;
import com.byron.components.hud.ScoreEvent;
import com.byron.components.hud.ScoreWidgetComponent;
import com.byron.components.hud.TextComponent;
import com.byron.components.sprite.SpriteComponent;
import com.byron.components.weapons.DamageComponent;
import com.byron.components.weapons.WeaponTag;
import com.byron.engine.GameResources;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IAgentService;
import com.byron.utils.Mappers;

public class ScoreChangeSystem extends IteratingSystem {

    private ImmutableArray<Entity> scoreWidgets;
    IAgentService agentService;
    private int targetMilestone;

    public ScoreChangeSystem(IAgentService agentService) {
        super(Family.all(ScoreEvent.class).get());
        this.agentService = agentService;
        int score = GameResources.get().savedScore;
        targetMilestone = (score / 1000) * 1000 + 1000;
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
        GameResources.get().savedScore = score;
        scoreText.text = String.valueOf(score);
        getEngine().removeEntity(entity);
        return score;
    }

    private void checkMilestones(int score) {
        if (score >= 10000) {
            getEngine().addEntity(new Entity().add(new MilestoneEvent(MAJOR)));
        } else if (score >= targetMilestone) {
            if (targetMilestone == 1000) {
                Sprite weaponSprite = SpriteFactory.getSprite(REGULAR_SWORD.name);
                weaponSprite.setOriginCenter();
                Entity weapon = new Entity()
                    .add(new WeaponTag())
                    .add(new SpriteComponent(weaponSprite))
                    .add(new DamageComponent(REGULAR_SWORD.damage));
                getEngine().addEntity(weapon);

                Entity player = agentService.getPlayer();
                player.add(new WeaponComponent(weapon));
            }
            getEngine().addEntity(new Entity().add(new MilestoneEvent(MINOR)));
        }
    }
}