package com.byron.models.sprite;


import com.byron.models.status.Status;

import java.util.Map;

public class RawAnimationModel {
    private float frameDuration;
    private int sheetWidth;
    private int sheetHeight;
    private Map<Status, SubAnimationModel> subAnimations;

    public RawAnimationModel() {}

    public int getSheetWidth() {
        return sheetWidth;
    }

    public void setSheetWidth(int sheetWidth) {
        this.sheetWidth = sheetWidth;
    }

    public int getSheetHeight() {
        return sheetHeight;
    }

    public void setSheetHeight(int sheetHeight) {
        this.sheetHeight = sheetHeight;
    }

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
                ", sheetWidth=" + sheetWidth +
                ", sheetHeight=" + sheetHeight +
                '}';
    }
}
