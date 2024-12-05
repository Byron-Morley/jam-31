package com.byron.interfaces;

import com.byron.models.status.Direction;

public interface IPlayerInputManager {
    Direction getPlayersNewDirection();

    boolean isPressingJump();
}
