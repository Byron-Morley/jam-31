package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.byron.renderers.strategy.DefaultRenderPositionStrategy;
import com.byron.renderers.strategy.RenderPositionStrategy;
import com.byron.utils.shape.Shape;

public class ShapeComponent implements Component {

    private Shape shape;

    private RenderPositionStrategy renderPositionStrategy;

    {
        this.renderPositionStrategy = new DefaultRenderPositionStrategy();
    }

    public ShapeComponent(Shape shape) {
        this.shape = shape;
    }

    public ShapeComponent(Shape shape, RenderPositionStrategy renderPositionStrategy) {
        this.renderPositionStrategy = renderPositionStrategy;
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public RenderPositionStrategy getRenderPositionStrategy() {
        return renderPositionStrategy;
    }

    public void setRenderPositionStrategy(RenderPositionStrategy renderPositionStrategy) {
        this.renderPositionStrategy = renderPositionStrategy;
    }
}
