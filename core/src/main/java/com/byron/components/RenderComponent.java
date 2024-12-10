package com.byron.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.byron.renderers.strategy.DefaultRenderPositionStrategy;
import com.byron.renderers.strategy.RenderPositionStrategy;
import com.byron.renderers.strategy.RenderPriority;

import java.util.ArrayList;
import java.util.List;

public class RenderComponent implements Component {
    private List<Sprite> sprites;
    int width = 1;
    int height = 1;
    private RenderPriority priority;
    private RenderPositionStrategy renderPositionStrategy;

    {
        this.renderPositionStrategy = new DefaultRenderPositionStrategy();
    }

    public RenderComponent(Sprite sprite, RenderPriority priority) {

        this.width = (int) sprite.getWidth();
        this.height = (int) sprite.getHeight();
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.priority = priority;
    }

    public RenderComponent(RenderPositionStrategy renderPositionStrategy, RenderPriority priority) {
        this.renderPositionStrategy = renderPositionStrategy;
        this.sprites = new ArrayList<>();
        this.priority = priority;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public RenderPriority getPriority() {
        return priority;
    }

    public void setPriority(RenderPriority priority) {
        this.priority = priority;
    }

    public RenderPositionStrategy getRenderPositionStrategy() {
        return renderPositionStrategy;
    }

    public void clear() {
        this.sprites.clear();
    }

    public void add(Sprite sprite) {
        this.sprites.add(sprite);
    }

    public Sprite getSprite() {
        return sprites.get(0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
