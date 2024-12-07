package com.byron.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public interface IMapManager {
    TiledMap getMap();
    void addObstacle(Rectangle rectangle);
}
