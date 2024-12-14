package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.byron.components.hud.InterpComponent;

public class ColorInterpComponent extends InterpComponent<Color> implements Component {

    public ColorInterpComponent(Interpolation interpolation, Color start, Color end) {
        super(interpolation, start, start.cpy(), end);
    }

    public ColorInterpComponent(Color start, Color end) {
        super(start, start.cpy(), end);
    }
}