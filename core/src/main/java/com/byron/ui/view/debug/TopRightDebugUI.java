package com.byron.ui.view.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.byron.interfaces.IUpdatable;
import com.byron.interfaces.UI;
import com.kotcrab.vis.ui.widget.VisTable;

public class TopRightDebugUI implements UI<Group>, IUpdatable {
    private final VisTable table;
    private final DebugUI debugUI;

    public TopRightDebugUI() {
        this.table = new VisTable();
        this.table.setFillParent(true);
        this.table.align(Align.topLeft);
        this.debugUI = new DebugUI();
        this.table.add(debugUI).top().left();
        this.table.row();
    }

    public void setFPS(int fps) {
        this.debugUI.setFPS(fps);
    }

    public void setPositionLabel(String text) {
        this.debugUI.setPositionLabel(text);
    }

    public void setLabelThree(String text) {
        this.debugUI.setLabelThree(text);
    }

    public void setLabelFour(String text) {
        this.debugUI.setLabelFour(text);
    }

    public void setLabelFive(String text) {
        this.debugUI.setLabelFive(text);
    }

    public void setLabelSix(String text) {
        this.debugUI.setLabelSix(text);
    }

    public void setLabelSeven(String text) {
        this.debugUI.setLabelSeven(text);
    }

    public void setLabelEight(String text) {
        this.debugUI.setLabelEight(text);
    }

    @Override
    public Table get() {
        return this.table;
    }

    @Override
    public void update() {
        setFPS(Gdx.graphics.getFramesPerSecond());
    }
}
