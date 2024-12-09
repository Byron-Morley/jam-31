package com.byron.renderers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.byron.engine.GameResources;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IRenderable;

import static com.byron.utils.Config.MAP_HEIGHT;
import static com.byron.utils.Config.MAP_WIDTH;

public class GridRenderer implements IRenderable {

    GameResources resources;
    TextureRegion grid;

    public GridRenderer() {
        this.resources = GameResources.get();
        grid = SpriteFactory.getTextureRegion("grid", -1);
    }

    @Override
    public void render(float delta) {
        resources.getBatch().setProjectionMatrix(resources.getCamera().combined);
        resources.getBatch().begin();
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                resources.getBatch().draw(grid, x, y, 1, 1);
            }
        }
        resources.getBatch().end();
    }

    @Override
    public void dispose() {

    }
}
