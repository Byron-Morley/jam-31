package com.byron.models.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class ComplexSprite {
    private Texture texture;
    private TextureAtlas atlas;
    private String name;
    private SpriteModel model;
    private boolean resetOrigin = false;


    public ComplexSprite(TextureAtlas atlas, Texture texture, SpriteModel model, String name) {
        this.texture = texture;
        this.model = model;
        this.atlas = atlas;
        this.name = name;
    }

    public ComplexSprite(TextureAtlas atlas, Texture texture, SpriteModel model, String name, boolean resetOrigin) {
        this.texture = texture;
        this.atlas = atlas;
        this.name = name;
        this.model = model;
        this.resetOrigin = resetOrigin;
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

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public String getName() {
        return name;
    }

    public boolean isResetOrigin() {
        return resetOrigin;
    }
}
