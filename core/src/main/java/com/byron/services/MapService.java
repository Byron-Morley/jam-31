package com.byron.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.byron.components.BodyComponent;
import com.byron.components.PlatformComponent;
import com.byron.components.PositionComponent;
import com.byron.interfaces.IMapManager;
import com.byron.interfaces.IMapService;
import com.byron.models.physics.Body;
import com.byron.utils.Config;
import com.byron.utils.shape.RectangleShape;
import com.byron.utils.shape.Shape;

import java.util.Iterator;

public class MapService extends Service implements IMapService {
    public static final String COLLISION_LAYER = "collisions";
    IMapManager mapManager;

    public MapService(IMapManager mapManager) {
        this.mapManager = mapManager;
        populateObstacles();
//        createObstacle(1, 8f, 1, 1);
    }

  private void populateObstacles() {
        MapLayer layer = mapManager.getMap().getLayers().get(COLLISION_LAYER);
        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();

        while (objectIt.hasNext()) {
            MapObject object = objectIt.next();

            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle rectangle = rectangleObject.getRectangle();

            float width = rectangle.getWidth() / Config.PX_PER_METER;
            float height = rectangle.getHeight() / Config.PX_PER_METER;

            float x = (rectangle.getX() / Config.PX_PER_METER);
            float y = (rectangle.getY() / Config.PX_PER_METER);

            createObstacle(x, y, width, height);
        }
    }

    private void createObstacle(float x, float y, float width, float height) {
        mapManager.addObstacle(new Rectangle(x, y, width, height));

        Body body = new Body(x, y, width, height);

        Shape shape = new RectangleShape(body.width, body.height);
        shape.setColor(Color.BLUE);
        body.createFixture("STATIC", shape);

        Entity entity = new Entity();
        BodyComponent bodyComponent = new BodyComponent(body);
        entity.add(bodyComponent);

        PositionComponent positionComponent = new PositionComponent(x, y);
        PlatformComponent platformComponent = new PlatformComponent();

        entity.add(platformComponent);
        entity.add(positionComponent);
        entity.add(bodyComponent);

        getEngine().addEntity(entity);
    }

}
