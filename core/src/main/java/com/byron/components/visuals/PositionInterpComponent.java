package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.byron.components.hud.InterpComponent;

public class PositionInterpComponent extends InterpComponent<Vector2> implements Component {

    public PositionInterpComponent(Interpolation interpolation, Vector2 start, Vector2 end) {
        super(interpolation, start, start.cpy(), end);
    }

    public PositionInterpComponent(Vector2 start, Vector2 end) {
        super(start, start.cpy(), end);
    }
}