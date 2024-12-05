package com.byron.ui.controller;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.byron.interfaces.IUIService;
import com.byron.ui.view.debug.TopRightDebugUI;

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
//            case Messages.CURSOR_TILE_POSITION:
//                topRightDebugUI.setPositionLabel((GridPoint2) msg.extraInfo);
//                break;
//            case Messages.IS_WALKABLE:
//                topRightDebugUI.setLabelThree((String) msg.extraInfo);
//                break;
//            case Messages.CONNECTION_COUNT:
//                topRightDebugUI.setLabelFour((String) msg.extraInfo);
//                break;
        }
        return false;
    }
}
