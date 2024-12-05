package com.byron.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.components.StatusComponent;
import com.byron.engine.GameResources;
import com.byron.utils.Dimensions;
import com.byron.utils.Mappers;

import java.util.Comparator;
import java.util.List;

public class RenderSystem extends SortedIteratingSystem {
    private ComponentMapper<PositionComponent> pm = Mappers.position;
    private ComponentMapper<RenderComponent> rm = Mappers.render;
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private SpriteBatch spriteBatch;

    public RenderSystem() {
        super(Family.all(PositionComponent.class, RenderComponent.class).get(), new ZComparator());
        this.spriteBatch = GameResources.get().getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        super.forceSort();
        PositionComponent positionComponent = pm.get(entity);
        RenderComponent renderComponent = rm.get(entity);
        StatusComponent statusComponent = sm.get(entity);

        List<Sprite> sprites = renderComponent.getSprites();

        if (sprites.isEmpty()) {
            return;
        }

        for (Sprite sprite : sprites) {
            Vector2 pos = renderComponent.getRenderPositionStrategy().process(positionComponent.getX(), positionComponent.getY(), statusComponent.getDirection());

            float x = pos.x - Dimensions.toMeters(sprite.getOriginX());
            float y = pos.y - Dimensions.toMeters(sprite.getOriginY());

            float width = Dimensions.toMeters(sprite.getRegionWidth());
            float height = Dimensions.toMeters(sprite.getRegionHeight());

            spriteBatch.draw(sprite, x, y,
                    sprite.getOriginX(), sprite.getOriginY(),
                    width, height,
                    sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
        }
    }

    /**
     * This ZComparator can be really expensive if there are too many render entities
     **/
    private static class ZComparator implements Comparator<Entity> {
        private static final int DIFF_MULTIPLIER = 10;

        private ComponentMapper<PositionComponent> pm = Mappers.position;
        private ComponentMapper<RenderComponent> rm = Mappers.render;

        @Override
        public int compare(Entity e1, Entity e2) {
            int priorityDiff = getPriorityDifference(e1, e2);

            if (priorityDiff == 0)
                return getVerticalPositionDifferente(e1, e2);

            return priorityDiff;
        }

        private int getVerticalPositionDifferente(Entity e1, Entity e2) {
            PositionComponent p1 = pm.get(e1);
            PositionComponent p2 = pm.get(e2);

            return (int) ((p2.getY() - p1.getY()) * DIFF_MULTIPLIER);
        }

        private int getPriorityDifference(Entity e1, Entity e2) {
            RenderComponent r1 = rm.get(e1);
            RenderComponent r2 = rm.get(e2);

            return r2.getPriority().getValue() - r1.getPriority().getValue();
        }
    }
}
