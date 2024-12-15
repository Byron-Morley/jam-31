package com.byron.models;

public class SoundAsset {
    String id;
    String path;
    int priority;
    float volume;
    boolean isMusic = false;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }
}
