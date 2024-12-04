package com.byron.engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameResources {
    static GameResources instance;
    Engine engine;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    Stage stage;
    AssetManager assetManager;

    public GameResources(Engine engine, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.engine = engine;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.camera = new OrthographicCamera();
        this.assetManager = new AssetManager();

        if (instance == null) {
            instance = this;
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public static GameResources get() {
        return instance;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
