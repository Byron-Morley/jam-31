package com.byron.models;

import com.byron.models.equip.EquipSlot;

import java.util.Map;

public class Agent {
    String name;
    String factionId;
    float velocityX;
    float velocityY;
    String animationModel;
    Map<EquipSlot, String> body;

    public Agent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactionId() {
        return factionId;
    }

    public void setFactionId(String factionId) {
        this.factionId = factionId;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public String getAnimationModel() {
        return animationModel;
    }

    public void setAnimationModel(String animationModel) {
        this.animationModel = animationModel;
    }

    public Map<EquipSlot, String> getBody() {
        return body;
    }

    public void setBody(Map<EquipSlot, String> body) {
        this.body = body;
    }
}
