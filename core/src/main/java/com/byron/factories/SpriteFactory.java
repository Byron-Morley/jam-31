package com.byron.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.byron.models.sprite.ComplexSprite;
import com.byron.models.sprite.SpriteModel;
import com.byron.utils.Dimensions;

import java.io.File;
import java.util.Set;

public class SpriteFactory {
    private static TextureAtlas atlas;
    private static BitmapFont font;

    static {
        loadAtlasSprites();
    }

    public static void loadAtlasSprites() {
        Set<String> files = ModelFactory.getAtlasSpriteModels();

        atlas = new TextureAtlas();
        for (String path : files) {

            File file = new File(path);
            String folder = file.getParent();

            TextureAtlas.TextureAtlasData data = new TextureAtlas.TextureAtlasData(Gdx.files.internal(path), Gdx.files.internal(folder), false);
            atlas.load(data);
        }
    }

    public static ComplexSprite get(String name) {
//        System.out.println("name:"+ name);
        try {
            TextureRegion textureRegion = atlas.findRegion(name);
            SpriteModel spriteModel = new SpriteModel(textureRegion.getRegionWidth());
            return new ComplexSprite(atlas, textureRegion.getTexture(), spriteModel, name);
        } catch (Exception e) {
            System.out.println("failed to find:" + name);
        }
        return null;
    }

    public static ComplexSprite getItemComplexSprite(String name) {
       try {
            TextureRegion textureRegion = atlas.findRegion(name);
            SpriteModel spriteModel = new SpriteModel(textureRegion.getRegionWidth());
            return new ComplexSprite(atlas, textureRegion.getTexture(), spriteModel, name, true);
        } catch (Exception e) {
            System.out.println("failed to find:" + name);
        }
        return null;
    }

    public static Sprite getSprite(String name, int index) {

        TextureRegion textureRegion = atlas.findRegion(name);
        if (index >= 0) textureRegion = atlas.findRegion(name, index);

        float width = Dimensions.toMeters(textureRegion.getRegionWidth());
        float height = Dimensions.toMeters(textureRegion.getRegionHeight());

        Sprite sprite = new Sprite(textureRegion);

        sprite.setSize(width, height);
        sprite.setOrigin(0, 0);

        return sprite;
    }

    public static Sprite getSprite(String name) {
        return SpriteFactory.getSprite(name, -1);
    }

    public static TextureRegion getTextureRegion(String name, int index) {
        return atlas.findRegion(name, index);
    }

    public static TextureRegion getTextureRegion(String name) {
        return atlas.findRegion(name);
    }

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public static BitmapFont getFont() {
        return font;
    }
}
