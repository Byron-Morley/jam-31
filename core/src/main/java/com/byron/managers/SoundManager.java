package com.byron.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.byron.commands.PlaySoundCommand;
import com.byron.engine.GameResources;
import com.byron.factories.ModelFactory;
import com.byron.interfaces.ISoundCommand;
import com.byron.interfaces.ISoundManager;
import com.byron.models.SoundAsset;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager implements ISoundManager {

    private static final int MAX_CONCURRENT_SOUNDS = 4; // Adjust as needed
    private PriorityQueue<ISoundCommand> soundQueue;
    private Set<ISoundCommand> currentlyPlaying;
    private ExecutorService soundExecutor;
    private final AssetManager assetManager;
    private final Map<String, SoundAsset> sounds;

    public SoundManager() {
        assetManager = GameResources.get().getAssetManager();
        sounds = ModelFactory.getSoundsModel();
        soundQueue = new PriorityQueue<>((a, b) -> b.getPriority() - a.getPriority());
        currentlyPlaying = Collections.synchronizedSet(new HashSet<>());
        soundExecutor = Executors.newFixedThreadPool(MAX_CONCURRENT_SOUNDS);
        queueSounds();
    }

    private void queueSounds() {
        sounds.forEach((id, config) ->
            assetManager.load(config.getPath(), Sound.class)
        );
        assetManager.finishLoading();
    }

    public void queueSound(ISoundCommand command) {
        if (currentlyPlaying.size() < MAX_CONCURRENT_SOUNDS) {
            soundExecutor.submit(() -> {
                currentlyPlaying.add(command);
                command.execute();
                currentlyPlaying.remove(command);
            });
        } else {
            soundQueue.offer(command);
        }
    }

    public void playSound(String soundId) {
        SoundAsset config = sounds.get(soundId);
        Sound sound = assetManager.get(config.getPath(), Sound.class);
        queueSound(new PlaySoundCommand(sound, config.getPriority(), config.getVolume()));
    }
}
