package com.byron.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.byron.engine.GameResources;
import com.byron.interfaces.IMapManager;
import com.byron.interfaces.IMapService;
import com.byron.interfaces.IRenderable;
import com.byron.services.MapService;
import com.byron.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class MapManager implements IMapManager, IRenderable {

    private List<Rectangle> obstacles;
    public static final String PATH_TO_TILE_MAP = "map/level_01/level_01.tmx";
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    SpriteBatch batch;
    IMapService mapService;

    public MapManager() {
        this.camera = GameResources.get().getCamera();
        this.batch = GameResources.get().getBatch();
        this.map = new TmxMapLoader().load(PATH_TO_TILE_MAP);
        this.obstacles = new ArrayList<>();
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map, Config.METERS_PER_PX, this.batch);
        this.mapService = new MapService(this);
    }

    @Override
    public void render(float delta) {
//        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public void addObstacle(Rectangle rectangle) {
        obstacles.add(rectangle);
    }
}
