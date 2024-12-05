package com.byron.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.models.status.Direction;

import java.util.Stack;

public class PlayerInputManager extends ClickListener implements IPlayerInputManager {
     private Stack<Direction> movementKeysPressed;
    private Vector2 latestClickedPosition;
    private boolean isClicking;
    private boolean isPressingJump;

    public PlayerInputManager() {
        this.movementKeysPressed = new Stack<>();
    }

    public Direction getPlayersNewDirection() {
        int keysPressed = movementKeysPressed.size();

        if (keysPressed == 0)
            return null;
        else
            return movementKeysPressed.peek();
    }

    private Direction keycodeToDirection(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                return Direction.LEFT;
            case Input.Keys.D:
                return Direction.RIGHT;
        }
        return null;
    }

    public boolean isClicking() {
        return this.isClicking;
    }

    public boolean isPressingJump() {
        return isPressingJump;
    }

    private void handleActionKeys(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                isPressingJump = true;
                break;
        }
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        System.out.println("key down");
        handleActionKeys(keycode);
        handleMovementKeys(keycode);
        return super.keyDown(event, keycode);
    }

    private void handleMovementKeys(int keycode) {
        Direction direction = keycodeToDirection(keycode);

        if (direction != null && !movementKeysPressed.contains(direction)) {
            movementKeysPressed.push(direction);
        }
    }


    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        Direction direction = keycodeToDirection(keycode);

        switch (keycode) {
            case Input.Keys.SPACE:
                isPressingJump = false;
                break;
        }

        if (direction != null)
            movementKeysPressed.remove(direction);

        return super.keyUp(event, keycode);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.isClicking = true;
        this.latestClickedPosition = new Vector2(x, y);

        return super.touchDown(event, x, y, pointer, button);
    }


    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        this.isClicking = false;

        super.touchUp(event, x, y, pointer, button);
    }
}
