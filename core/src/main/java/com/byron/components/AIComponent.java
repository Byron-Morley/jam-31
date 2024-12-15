package com.byron.components;

import com.badlogic.ashley.core.Component;

import static com.byron.utils.Config.ENEMY_TURN_RATIO;

public class AIComponent implements Component {

    public enum State {IDLE, ATTACKING, MOVING} // Byron went a different way, never needed this line and the one below

    public State state = State.IDLE;
    public int moveCounter;
    public final int turnRatio;

    public AIComponent(int moveCounter) {
        this.moveCounter = moveCounter;
        this.turnRatio = moveCounter;
    }

    public boolean canMove() {
        if (moveCounter <= 0) {
            moveCounter = turnRatio;
            return true;
        }
        return false;
    }
}
