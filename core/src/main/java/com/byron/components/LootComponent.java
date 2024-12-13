package com.byron.components;

import com.badlogic.ashley.core.Component;

public class LootComponent implements Component {
    int value;
    boolean isArmor;

    public LootComponent(int value, boolean isArmor) {
        this.value = value;
        this.isArmor = isArmor;
    }

    public int getValue() {
        return value;
    }

    public boolean isArmor() {
        return isArmor;
    }
}
