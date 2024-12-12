package com.byron.components.hud;

import com.badlogic.ashley.core.Component;

public class ScoreEvent implements Component {

    public final int scoreChange;

    public ScoreEvent(int scoreChange) {
        this.scoreChange = scoreChange;
    }
}