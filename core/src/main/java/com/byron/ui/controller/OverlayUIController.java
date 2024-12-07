package com.byron.ui.controller;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.byron.interfaces.IUIService;
import com.byron.ui.view.debug.TopRightDebugUI;
import com.byron.utils.Messages;

import static com.byron.utils.ui.Panels.TOP_RIGHT_DEBUG;


public class OverlayUIController implements Telegraph {
    IUIService uiService;
    TopRightDebugUI topRightDebugUI;

    public OverlayUIController(IUIService uiService) {
        topRightDebugUI = (TopRightDebugUI) uiService.getUI(TOP_RIGHT_DEBUG);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case Messages.PLAYER_POSITION:
                topRightDebugUI.setPositionLabel((String) msg.extraInfo);
                break;
            case Messages.PLAYER_STATUS:
                topRightDebugUI.setLabelThree((String) msg.extraInfo);
                break;
            case Messages.VELOCITY:
                topRightDebugUI.setLabelFour((String) msg.extraInfo);
                break;
            case Messages.ACCELERATION:
                topRightDebugUI.setLabelFive((String) msg.extraInfo);
                break;
            case Messages.BODY_POSITION:
                topRightDebugUI.setLabelSix((String) msg.extraInfo);
                break;
            case Messages.PREVIOUS_POSITION:
                topRightDebugUI.setLabelSeven((String) msg.extraInfo);
                break;
            case Messages.SPEED:
                topRightDebugUI.setLabelEight((String) msg.extraInfo);
                break;

        }
        return false;
    }
}
