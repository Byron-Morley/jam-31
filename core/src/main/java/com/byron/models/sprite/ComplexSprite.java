package com.byron.models.sprite;

import com.badlogic.gdx.graphics.Texture;

public class ComplexSprite {
    private Texture texture;
    private SpriteModel model;

    public ComplexSprite(Texture texture, SpriteModel model) {
        this.texture = texture;
        this.model = model;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getSize() {
        return model.getRegionSize();
    }

    public int getStartingIndexAtSpriteSheet() {
        return model.getStartingIndexAtSpriteSheet();
    }
}
