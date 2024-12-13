package com.byron.models.item;

public class Item {
    private String label;
    private String sprite;
    float spriteScale = 1;
    boolean pickupable = false;
    int value = 0;

    public String getLabel() {
        return label;
    }

    public String getSprite() {
        return sprite;
    }

    public float getSpriteScale() {
        return spriteScale;
    }

    public boolean isPickupable() {
        return pickupable;
    }

    public int getValue() {
        return value;
    }
}
