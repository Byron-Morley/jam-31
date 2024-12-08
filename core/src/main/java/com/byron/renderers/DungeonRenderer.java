package com.byron.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.byron.engine.GameResources;
import com.byron.factories.SpriteFactory;
import com.byron.interfaces.IRenderable;

import java.util.Random;

import static com.byron.utils.Config.PX_PER_METER;

public class DungeonRenderer implements IRenderable {

    int[][] dungeon;
    SpriteBatch batch;
    TextureRegion floor;
    TextureRegion brokenFloor;
    Random random;
    final int PERCENT_CHANCE_OF_BROKEN_FLOOR = 5;


    public DungeonRenderer(int[][] dungeon) {
        this.dungeon = dungeon;
        batch = GameResources.get().getBatch();
        floor = SpriteFactory.getTextureRegion("building/stone-tile-1", -1);
        brokenFloor = SpriteFactory.getTextureRegion("building/stone-tile-1-broken", -1);
        random = new Random();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(GameResources.get().getCamera().combined);
        batch.begin();

        for (int i = 0; i < dungeon.length; i++) {
            for (int j = 0; j < dungeon[i].length; j++) {
                if (dungeon[i][j] == 1) {
                    // Use coordinates to generate consistent hash
                    int hash = (i * 31 + j) * 31;
                    // Use modulo to get 2% chance (0-49 = broken, 50-99 = normal)
                    if (Math.abs(hash % 100) < PERCENT_CHANCE_OF_BROKEN_FLOOR) {
                        batch.draw(brokenFloor, i * PX_PER_METER, j * PX_PER_METER);
                    } else {
                        batch.draw(floor, i * PX_PER_METER, j * PX_PER_METER);
                    }
                }
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
