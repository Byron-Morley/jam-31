package com.byron.interfaces;

public interface ISoundManager {
    void playSound(String sound);

    void playMusic(String musicId, boolean looping);

    void stopMusic();
}
