package com.byron.components.visuals;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.byron.components.hud.InterpComponent;

public class RotationInterpComponent extends InterpComponent<Float> implements Component {

    public RotationInterpComponent(Interpolation interpolation, Float start, Float end) {
        super(interpolation, start, start, end);
    }

    public RotationInterpComponent(Float start, Float end) {
        super(start, start, end);
    }
}