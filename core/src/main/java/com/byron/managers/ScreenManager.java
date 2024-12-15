package com.byron.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.byron.engine.GameResources;
import com.byron.interfaces.IRenderable;
import com.byron.screens.GameScreen;
import com.byron.screens.StartMenu;

import java.util.Random;

public class ScreenManager implements IRenderable {

    private GameResources gameResources;
    private Screen currentScreen;
    private final Music track1, track2;

    public ScreenManager(GameResources gameResources) {
        this.gameResources = gameResources;
        gameResources.setScreenManager(this);

        track1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/track_01.ogg"));
        track1.setVolume(0.2f);
        track1.setLooping(true);

        track2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/track_02.ogg"));
        track2.setVolume(0.2f);
        track2.setLooping(true);
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        if (this.currentScreen != null)
            this.currentScreen.dispose();

        this.currentScreen = currentScreen;
    }

    public void loadGameScreen() {
        System.out.println("Loading Game Screen");
        Random random = new Random();
        int seed = random.nextInt(1000000000);
        GameScreen gameScreen = new GameScreen(seed);
        setCurrentScreen(gameScreen);
        gameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        track2.play();
        track1.stop();
    }

    public void loadStartMenu() {
        System.out.println("Loading Start Menu");
        setCurrentScreen(new StartMenu(this));
        track1.play();
        track2.stop();
    }

    @Override
    public void render(float delta) {
        this.currentScreen.render(delta);
    }

    @Override
    public void dispose() {

    }
}