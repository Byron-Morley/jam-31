package com.byron.systems.hud;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.byron.components.hud.ScoreWidgetComponent;
import com.byron.components.hud.TextComponent;
import com.byron.engine.GameResources;

public class ScoreWidgetSpawner extends EntitySystem {

    private static final float FONT_SCALE = 0.05f;

    @Override
    public void addedToEngine(Engine engine) {
        int gold = GameResources.get().savedScore;
        Entity scoreWidget = new Entity();
        scoreWidget
            .add(new ScoreWidgetComponent())
            .add(new TextComponent(new BitmapFont(Gdx.files.internal("raw/fonts/pixelFont.fnt")), String.valueOf(gold), FONT_SCALE));
        engine.addEntity(scoreWidget);
        engine.removeSystem(this);
    }
}