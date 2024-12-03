package com.byron.renderers.strategy;

public enum RenderPriority {
    UI(1),
    FOREGROUND(2),
    CARRYABLE(3),
    AGENT(4),
    ITEM(5),
    SCENERY(6),
    BACKGROUND(7);

    private int value;

    RenderPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
