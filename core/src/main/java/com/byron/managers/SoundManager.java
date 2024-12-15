package com.byron.managers;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.byron.commands.PlaySoundCommand;
import com.byron.engine.GameResources;
import com.byron.factories.ModelFactory;
import com.byron.interfaces.ISoundCommand;
import com.byron.interfaces.ISoundManager;
import com.byron.models.SoundAsset;
import com.byron.utils.Messages;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.byron.utils.Messages.PLAY_MUSIC;
import static com.byron.utils.Messages.PLAY_SOUND;

public class SoundManager implements ISoundManager, Telegraph {

    private static final int MAX_CONCURRENT_SOUNDS = 4; // Adjust as needed
    private PriorityQueue<ISoundCommand> soundQueue;
    private Set<ISoundCommand> currentlyPlaying;
    private ExecutorService soundExecutor;
    private final AssetManager assetManager;
    private final Map<String, SoundAsset> sounds;
    private final Map<String, Music> musicTracks = new HashMap<>();
    private Music currentMusic;
    public static final String[] randomBGSfx = {"BG_1A_TS",
        "BG_1B_TS",
        "BG_1C_TS",
        "BG_2A_TS",
        "BG_2B_TS",
        "BG_2C_LONG_TS",
        "BG_3A_TS",
        "BG_3B_TS",
        "BG_3C_TS",
        "BG_3D_TS",
        "BG_3E_TS",
        "BG_3F_TS",
        "BG_3G_TS",
        "BG_4A_LONG_TS",
        "BG_4B_TS",
        "BG_4C_TS",
        "BG_4D_TS",
        "BG_4E_TS",
        "BG_4F_TS",
        "BG_4G_TS",
        "BG_4H_TS",
        "BG_4I_TS",
        "BG_4J_TS",
        "BG_4K_TS"};

    public SoundManager() {
        assetManager = GameResources.get().getAssetManager();
        sounds = ModelFactory.getSoundsModel();
        soundQueue = new PriorityQueue<>((a, b) -> b.getPriority() - a.getPriority());
        currentlyPlaying = Collections.synchronizedSet(new HashSet<>());
        soundExecutor = Executors.newFixedThreadPool(MAX_CONCURRENT_SOUNDS);
        queueSounds();
        subscribeListeners();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case PLAY_SOUND:
                playSound((String) msg.extraInfo);
                break;
            case Messages.PLAY_MUSIC:
                playMusic((String) msg.extraInfo, true);
                break;
        }
        return false;
    }

    private void queueSounds() {
        sounds.forEach((id, config) -> {
            String path = config.getPath();
            System.out.println("Attempting to load: " + path + " isMusic: " + config.isMusic());
            if (config.isMusic()) {
                assetManager.load(path, Music.class);
            } else {
                assetManager.load(path, Sound.class);
            }
        });
        assetManager.finishLoading();

        // Verify loaded assets
        Array<String> loadedAssets = assetManager.getAssetNames();
        System.out.println("Loaded assets:");
        for (String asset : loadedAssets) {
            System.out.println(asset);
        }
    }

    public void playMusic(String musicId, boolean looping) {
        if (currentMusic != null) {
            currentMusic.stop();
        }

        SoundAsset config = sounds.get(musicId);
        String path = config.getPath();

        if (!assetManager.isLoaded(path)) {
            assetManager.load(path, Music.class);  // Changed to Music.class instead of Sound.class
            assetManager.finishLoading();
        }

        Music music = assetManager.get(path, Music.class);
        music.setLooping(looping);
        music.setVolume(config.getVolume());
        music.play();
        currentMusic = music;
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
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

    private void subscribeListeners() {
        MessageDispatcher messageDispatcher = MessageManager.getInstance();
        messageDispatcher.addListeners(
            this,
            PLAY_SOUND,
            PLAY_MUSIC
        );
    }

}
