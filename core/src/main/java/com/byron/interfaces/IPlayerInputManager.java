package com.byron.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Direction;

import java.util.LinkedList;
import java.util.Stack;

public interface IPlayerInputManager {
    Direction getPlayersNewDirection();

    Stack<Direction> getMovementKeysPressed();

    Stack<PlayerAction> getActionKeyPressed();

    LinkedList<Float> getScrollQueue();

    Vector2 getMovementVector();
}
