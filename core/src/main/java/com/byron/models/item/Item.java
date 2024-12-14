package com.byron.models.item;

public class Item {
    private String label;
    private String sprite;
    float spriteScale = 1;
    boolean pickupable = false;
    int value = 0;
    boolean isArmor = false;
    String animationModel = "DEFAULT";

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

    public boolean isArmor() {
        return isArmor;
    }

    public void setArmor(boolean armor) {
        isArmor = armor;
    }

    public String getAnimationModel() {
        return animationModel;
    }
}
