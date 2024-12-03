package com.byron.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.byron.engine.GameResources;
import com.byron.interfaces.IMapManager;
import com.byron.interfaces.IRenderable;
import com.byron.utils.Config;

public class MapManager implements IMapManager, IRenderable {
    public static final String PATH_TO_TILE_MAP = "map/test/test_map_32.tmx";
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    SpriteBatch batch;

    public MapManager() {
        this.camera = GameResources.get().getCamera();
        this.batch = GameResources.get().getBatch();
        this.map = new TmxMapLoader().load(PATH_TO_TILE_MAP);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map, Config.METERS_PER_PX, this.batch);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }
}
