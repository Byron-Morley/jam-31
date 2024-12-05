package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.byron.models.status.Action;
import com.byron.models.status.Direction;

public class StatusComponent implements Component {
    Action action;
    Direction direction;
    boolean isMoving;
    boolean changeInActionOrDirection;

    public StatusComponent() {
        this.action = Action.STANDING;
        this.direction = Direction.RIGHT;
        this.changeInActionOrDirection = false;
    }

    public void setAction(Action action){
        changeInActionOrDirection = action != this.action;
        this.action = action;
    }

    public void setDirection(Direction direction) {
        changeInActionOrDirection = action != this.action;
        this.direction = direction;
    }

    public Action getAction() {
        return this.action;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isChangeInActionOrDirection() {
        return changeInActionOrDirection;
    }

    public void setChangeInActionOrDirection(boolean changeInActionOrDirection) {
        this.changeInActionOrDirection = changeInActionOrDirection;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
