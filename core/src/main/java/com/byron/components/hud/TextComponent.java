package com.byron.components.hud;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextComponent implements Component {

    public BitmapFont font;
    public String text;

    public TextComponent(BitmapFont textFont, String text, float scale) {
        font = textFont;
        font.setUseIntegerPositions(false);
        font.getData().setScale(scale);
        this.text = text;
    }

    public TextComponent(BitmapFont font) {
        this(font, "", 1f);
    }
}