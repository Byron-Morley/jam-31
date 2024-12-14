package com.byron.models;

import com.byron.models.equip.EquipSlot;

import java.util.Map;

public class Agent {
    String id;
    String name;
    String factionId;
    float velocityX;
    float velocityY;
    boolean player = false;
    String animationModel = "AGENT";
    Map<EquipSlot, String> body;
    Stats stats;
    String skin;

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

    public boolean isPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }

    public Stats getStats() {
        return stats;
    }

    public String getSkin() {
        if (skin != null) return skin;
        return id;
    }
}
