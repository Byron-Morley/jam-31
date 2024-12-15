package com.byron.systems;

import static com.byron.utils.Messages.PLAY_SOUND;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.MathUtils;
import com.byron.managers.SoundManager;

public class BGSfxSystem extends EntitySystem {

    float timer;

    public BGSfxSystem() {
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime;
        if (timer >= 3) {
            int randomIndex = MathUtils.random(23);
            MessageManager.getInstance().dispatchMessage(PLAY_SOUND, SoundManager.randomBGSfx[randomIndex]);
            MessageManager.getInstance().dispatchMessage(PLAY_SOUND, SoundManager.randomBGSfx[randomIndex]);
            timer = MathUtils.random(2f);
        }
    }
}