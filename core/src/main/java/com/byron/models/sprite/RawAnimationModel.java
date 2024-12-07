package com.byron.models.sprite;


import com.byron.models.status.Status;

import java.util.Map;

public class RawAnimationModel {
    private float frameDuration;
    private Map<Status, SubAnimationModel> subAnimations;

    public RawAnimationModel() {}

    public void setSubAnimations(Map<Status, SubAnimationModel> subAnimations) {
        this.subAnimations = subAnimations;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public Map<Status, SubAnimationModel> getSubAnimations() {
        return subAnimations;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    @Override
    public String toString() {
        return "RawAnimationModel{" +
                "frameDuration=" + frameDuration +
                ", subAnimations=" + subAnimations +
                '}';
    }
}
