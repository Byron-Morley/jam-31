package com.byron.systems.hud;

import static com.badlogic.gdx.Input.Keys.R;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.byron.components.hud.ScoreWidgetComponent;
import com.byron.components.hud.TextComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Mappers;

public class ScoreRenderer extends IteratingSystem {

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final GlyphLayout glyphLayout;

    public ScoreRenderer() {
        super(Family.all(ScoreWidgetComponent.class, TextComponent.class).get());
        camera = GameResources.get().getCamera();
        spriteBatch = GameResources.get().getBatch();
        glyphLayout = new GlyphLayout();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextComponent textComponent = Mappers.text.get(entity);
        BitmapFont font = textComponent.font;
        String text = textComponent.text;

        float camX = camera.position.x;
        float camY = camera.position.y;
        float camH = camera.viewportHeight * camera.zoom;

        glyphLayout.setText(font, text);
        float fontW = glyphLayout.width;
        float fontH = glyphLayout.height;

        font.draw(spriteBatch, text, camX - fontW / 2f, camY - camH / 2f + fontH + camH / 25f);

        testRestart();
    }

    // TODO: Remove, this is only for testing the restart
    private void testRestart() {
        if (Gdx.input.isKeyJustPressed(R)) {
            GameResources.get().savedScore = 0;
            GameResources.get().sameSeed = MathUtils.randomBoolean();
            GameResources.get().setRestart(true);
        }
    }
}