package com.byron.screens;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.byron.managers.ScreenManager;

public class StartMenu extends ScreenAdapter {

    private final String START_MESSAGE = "Press any key to start";
    private final String TUTORIAL_MESSAGE = "WASD to move\nSPACE to attack\nR to restart\n" +
        "Get to the other pentagram\nGet/Upgrade your weapon by picking loot\nKeep an eye out for your HP and Armor at the top\n" +
        "Made with love by:\nByron\njohnny\nTorey\nGroxar";

    private final ScreenManager screenManager;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final Sprite background, infoButton, overlay;
    private final BitmapFont startText, tutorialText;
    private final GlyphLayout pixelLayout, normalLayout;
    private float time;

    public StartMenu(ScreenManager screenManager) {
        this.screenManager = screenManager;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        spriteBatch = new SpriteBatch();

        background = new Sprite(new Texture("raw/sprites/rogueStartScreen.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        infoButton = new Sprite(new Texture("raw/sprites/interrogationSign.png"));
        infoButton.setSize(60f, 60f);
        infoButton.setOriginCenter();

        startText = new BitmapFont(Gdx.files.internal("raw/fonts/pixelFont.fnt"));
        startText.getData().setScale(3f);

        overlay = new Sprite(new Texture("raw/sprites/pixel.png"));
        overlay.setColor(CLEAR);

        tutorialText = new BitmapFont(Gdx.files.internal("raw/fonts/normal.fnt"));
        tutorialText.getData().setScale(0.5f);
        tutorialText.setColor(CLEAR);

        pixelLayout = new GlyphLayout(startText, START_MESSAGE);
        normalLayout = new GlyphLayout(tutorialText, TUTORIAL_MESSAGE);

        Gdx.input.setInputProcessor(new InputHandler());
    }

    @Override
    public void render(float delta) {
        time += delta;
        startText.setColor(MathUtils.floor(time) % 2 == 0 ? CLEAR : WHITE);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        background.draw(spriteBatch);
        infoButton.draw(spriteBatch);
        startText.draw(
            spriteBatch,
            START_MESSAGE,
            Gdx.graphics.getWidth() / 2f - pixelLayout.width / 2f,
            Gdx.graphics.getHeight() * 0.33f + pixelLayout.height / 2f);
        overlay.draw(spriteBatch);
        tutorialText.draw(
            spriteBatch,
            TUTORIAL_MESSAGE,
            0f,
            Gdx.graphics.getHeight() / 2f + normalLayout.height / 2f,
            Gdx.graphics.getWidth(),
            Align.center,
            true
        );
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        background.setSize(width, height);
        infoButton.setOriginBasedPosition(width / 2f, height * 0.92f);
        overlay.setSize(width, height);
    }

    @Override
    public void hide() {
        spriteBatch.dispose();
        background.getTexture().dispose();
        infoButton.getTexture().dispose();
        startText.dispose();
    }

    private class InputHandler extends InputAdapter {

        private boolean showingTutorial;

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            screenY = Gdx.graphics.getHeight() - screenY;
            if (infoButton.getBoundingRectangle().contains(screenX, screenY)) {
                infoButton.setColor(RED);
            } else {
                infoButton.setColor(WHITE);
            }
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (showingTutorial) {
                tutorialText.setColor(CLEAR);
                overlay.setColor(CLEAR);
                showingTutorial = false;
                return true;
            }
            screenY = Gdx.graphics.getHeight() - screenY;
            if (infoButton.getBoundingRectangle().contains(screenX, screenY)) {
                tutorialText.setColor(WHITE);
                overlay.setColor(BLACK);
                showingTutorial = true;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            Gdx.input.setInputProcessor(null);
            screenManager.loadGameScreen();
            return true;
        }
    }
}