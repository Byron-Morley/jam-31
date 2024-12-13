package com.byron.components;

import com.badlogic.ashley.core.Component;

public class LootComponent implements Component {
    int value;

    public LootComponent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
