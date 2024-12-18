package com.byron.models;

public class SoundAsset {
    String id;
    String path;
    int priority;
    float volume;

    public SoundAsset() {
    }

    public String getPath() {
        return path;
    }

    public int getPriority() {
        return priority;
    }

    public float getVolume() {
        return volume;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
