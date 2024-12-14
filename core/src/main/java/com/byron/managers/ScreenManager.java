package com.byron.managers;

import com.badlogic.gdx.Screen;
import com.byron.engine.GameResources;
import com.byron.interfaces.IRenderable;
import com.byron.screens.GameScreen;
import  java.util.Random;

public class ScreenManager implements IRenderable {

    private GameResources gameResources;
    private Screen currentScreen;

    public ScreenManager(GameResources gameResources) {
        this.gameResources = gameResources;
        gameResources.setScreenManager(this);
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
        System.out.println("Loading WorldScreen");
        Random random = new Random();
        int seed = random.nextInt(1000000000);
        setCurrentScreen(new GameScreen(seed));
    }

    @Override
    public void render(float delta) {
        this.currentScreen.render(delta);
    }

    @Override
    public void dispose() {

    }
}