package com.byron.systems.hud;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.hud.ColorInterpComponent;
import com.byron.components.hud.PositionInterpComponent;
import com.byron.components.hud.TextComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;

public class FadingTextSystem extends IteratingSystem {

    private final SpriteBatch spriteBatch;

    public FadingTextSystem() {
        super(Family.all(
            ColorInterpComponent.class,
            PositionInterpComponent.class,
            TextComponent.class
        ).get());

        spriteBatch = GameResources.get().getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ColorInterpComponent colorInterp = Mappers.colorInterp.get(entity);
        PositionInterpComponent positionInterp = Mappers.positionInterp.get(entity);
        TextComponent textComponent = Mappers.text.get(entity);

        float colorProgress = colorInterp.progress;
        float positionProgress = positionInterp.progress;

        colorProgress = colorInterp.progress = Math.min(colorProgress + deltaTime, 1f);
        positionProgress = positionInterp.progress = Math.min(positionProgress + deltaTime, 1f);

        Color startColor = colorInterp.startColor;
        Color currentColor = colorInterp.currentColor;
        Color endColor = colorInterp.endColor;
        currentColor.set(startColor).lerp(endColor, colorInterp.interpolation.apply(colorProgress));

        Vector2 startPosition = positionInterp.startPosition;
        Vector2 currentPosition = positionInterp.currentPosition;
        Vector2 endPosition = positionInterp.endPosition;
        currentPosition.set(startPosition).lerp(endPosition, positionInterp.interpolation.apply(positionProgress));

        textComponent.font.setColor(currentColor);
        textComponent.font.draw(spriteBatch, textComponent.text, currentPosition.x, currentPosition.y);

        if (colorProgress == 1f && positionProgress == 1f) {
            getEngine().removeEntity(entity);
        }
    }
}