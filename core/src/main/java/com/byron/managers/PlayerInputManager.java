package com.byron.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.byron.interfaces.IPlayerInputManager;
import com.byron.interfaces.IUIService;
import com.byron.models.player.PlayerAction;
import com.byron.models.status.Direction;

import java.util.LinkedList;
import java.util.Stack;

public class PlayerInputManager extends ClickListener implements IPlayerInputManager {

    private final Stack<Direction> movementKeysPressed;
    private final Stack<PlayerAction> actionKeyPressed;
    private boolean previousLeftClicking;
    private boolean isLeftClicking;
    private boolean isRightClicking;
    private final LinkedList<Float> scrollQueue;
    private final Vector2 movementVector = new Vector2();
    IUIService uiService;


    public PlayerInputManager(IUIService uiService) {
        isLeftClicking = false;
        isRightClicking = false;
        previousLeftClicking = false;
        this.movementKeysPressed = new Stack<>();
        this.actionKeyPressed = new Stack<>();
        this.scrollQueue = new LinkedList<>();
        this.setButton(-1);
        this.uiService = uiService;
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
            case Input.Keys.W:
                return Direction.UP;
            case Input.Keys.S:
                return Direction.DOWN;
            case Input.Keys.A:
                return Direction.LEFT;
            case Input.Keys.D:
                return Direction.RIGHT;

        }
        return null;
    }

    public boolean isLeftClicking() {
        return this.isLeftClicking;
    }

    public boolean isRightClicking() {
        return this.isRightClicking;
    }

    private void handleMovementKeys(int keycode) {
        Direction direction = keycodeToDirection(keycode);

        if (direction != null && !movementKeysPressed.contains(direction)) {
            movementKeysPressed.push(direction);
        }
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        handleMovementKeys(keycode);
        handleActionKeys(keycode);
        return super.keyDown(event, keycode);
    }

    private void handleActionKeys(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            actionKeyPressed.push(PlayerAction.ACTION);
        }
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        Direction direction = keycodeToDirection(keycode);

        if (direction != null)
            movementKeysPressed.remove(direction);

        if (keycode == Input.Keys.SPACE) {
            actionKeyPressed.remove(PlayerAction.ACTION);
        }

        return super.keyUp(event, keycode);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            this.isLeftClicking = true;
        }
        if (button == Input.Buttons.RIGHT) {
            this.isRightClicking = true;
        }
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            this.isLeftClicking = false;
            this.previousLeftClicking = false;
        }

        if (button == Input.Buttons.RIGHT) {
            this.isRightClicking = false;
        }
        super.touchUp(event, x, y, pointer, button);
    }

    public Stack<Direction> getMovementKeysPressed() {
        return movementKeysPressed;
    }

    @Override
    public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
        if (uiService.isMouseOverUI()) {
            return super.scrolled(event, x, y, amountX, amountY);
        } else {
            scrollQueue.add(amountY);
            return true;
        }
    }

    public Vector2 getMovementVector() {

        movementVector.setZero();

        for (Direction dir : movementKeysPressed) {
            switch (dir) {
                case UP:
                    movementVector.y += 1;
                    break;
                case DOWN:
                    movementVector.y -= 1;
                    break;
                case LEFT:
                    movementVector.x -= 1;
                    break;
                case RIGHT:
                    movementVector.x += 1;
                    break;
            }
        }

        // Normalize the vector if moving diagonally to maintain consistent speed
        if (movementVector.len() > 1) {
            movementVector.nor();
        }

        return movementVector;
    }

    @Override
    public boolean playerHasTakenTheirTurn() {
        return (movementKeysPressed.size() > 0 || actionKeyPressed.size() > 0);
    }

    public LinkedList<Float> getScrollQueue() {
        return scrollQueue;
    }

    public Stack<PlayerAction> getActionKeyPressed() {
        return actionKeyPressed;
    }

    public boolean isPreviousLeftClicking() {
        return previousLeftClicking;
    }

    public void setPreviousLeftClicking(boolean previousLeftClicking) {
        this.previousLeftClicking = previousLeftClicking;
    }
}