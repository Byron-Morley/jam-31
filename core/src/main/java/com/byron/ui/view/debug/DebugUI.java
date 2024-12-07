package com.byron.ui.view.debug;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

import java.text.DecimalFormat;

class DebugLabel extends VisLabel {
    public DebugLabel(String text) {
        super(text);
        this.setFontScale(1f);
    }
}

public class DebugUI extends VisTable {

    private Label fpsLabel;
    private Label positionLabel;
    private Label labelThree;
    private Label labelFour;
    private Label labelFive;
    private Label labelSix;
    private Label labelSeven;
    private Label labelEight;
    DecimalFormat df;

    public DebugUI() {
        super();

        this.fpsLabel = new DebugLabel("FPS: unknown");
        this.positionLabel = new DebugLabel("Current Position: unknown");
        this.labelThree = new DebugLabel("");
        this.labelFour = new DebugLabel("");
        this.labelFive = new DebugLabel("");
        this.labelSix = new DebugLabel("");
        this.labelSeven = new DebugLabel("");
        this.labelEight = new DebugLabel("");

        df = new DecimalFormat("#.##");

        pad(5);
        align(Align.topLeft);
        add(fpsLabel).left();
        row();
        add(positionLabel).left();
        row();
        add(labelThree).left();
        row();
        add(labelFour).left();
        row();
        add(labelFive).left();
        row();
        add(labelSix).left();
        row();
        add(labelSeven).left();
        row();
        add(labelEight).left();
        row();

    }

    public void setFPS(int fps) {
        this.fpsLabel.setText("FPS: " + fps);
    }

    public void setPositionLabel(String text) {
        this.positionLabel.setText(text);
    }

    public void setLabelThree(String text) {
        this.labelThree.setText(text);
    }

    public void setLabelFour(String text) {
        this.labelFour.setText(text);
    }

    public void setLabelFive(String text) {
        this.labelFive.setText(text);
    }

    public void setLabelSix(String text) {
        this.labelSix.setText(text);
    }

    public void setLabelSeven(String text) {
        this.labelSeven.setText(text);
    }

    public void setLabelEight(String text) {
        this.labelEight.setText(text);
    }
}
