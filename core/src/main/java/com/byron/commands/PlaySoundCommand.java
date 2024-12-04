package com.byron.commands;

import com.badlogic.gdx.audio.Sound;
import com.byron.interfaces.ISoundCommand;

public class PlaySoundCommand implements ISoundCommand {
    Sound sound;
    int priority;
    float volume;

    public PlaySoundCommand(Sound sound, int priority, float volume) {
        this.sound = sound;
        this.priority = priority;
        this.volume = volume;
    }

    @Override
    public void execute() {
        sound.play(volume);
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
