package com.byron.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.byron.components.PositionComponent;
import com.byron.components.RenderComponent;
import com.byron.utils.Mappers;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    public RenderSystem() {
        super(Family.all(PositionComponent.class, RenderComponent.class).get(), new ZComparator());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        System.out.println("RenderSystem");
    }

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
