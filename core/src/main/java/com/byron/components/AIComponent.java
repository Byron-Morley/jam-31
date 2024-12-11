package com.byron.components;

import com.badlogic.ashley.core.Component;

import static com.byron.utils.Config.ENEMY_TURN_RATIO;

public class AIComponent implements Component {

    public enum State {IDLE, ATTACKING, MOVING}

    public State state = State.IDLE;
    public int moveCounter;

    public AIComponent() {
        this.moveCounter = ENEMY_TURN_RATIO;
    }

    public boolean canMove() {
        if (moveCounter <= 0) {
            moveCounter = ENEMY_TURN_RATIO;
            return true;
        }
        return false;
    }
}
