package com.byron.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.byron.engine.GameResources;
import com.byron.interfaces.ICameraManager;
import com.byron.interfaces.ICameraService;
import com.byron.services.CameraService;

import static com.byron.utils.Config.VIEWPORT_WIDTH_IN_METERS;

public class CameraManager implements ICameraManager {
    static final float MAXIMUM_DISTANCE_FROM_SHAKE = 12;
    static final int Z = 0;

    ScreenShakeManager shakeManager;
    OrthographicCamera camera;
    Vector3 position;
    ICameraService cameraService;

    public CameraManager() {
        cameraService = new CameraService(this);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        this.position = new Vector3(0, 0, Z);
        this.shakeManager = new ScreenShakeManager();
        this.camera = GameResources.get().getCamera();
        this.camera.setToOrtho(false, VIEWPORT_WIDTH_IN_METERS,
            VIEWPORT_WIDTH_IN_METERS * (height / width));
    }

    public void shake(float power, float duration, Vector2 epicenter) {
        if (cameraCloseToEpicenter(epicenter))
            shakeManager.shake(power, duration);
    }

    private boolean cameraCloseToEpicenter(Vector2 epicenter) {
        return new Vector3(epicenter, Z).sub(position).len() < MAXIMUM_DISTANCE_FROM_SHAKE;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT_WIDTH_IN_METERS;
        camera.viewportHeight = VIEWPORT_WIDTH_IN_METERS * height / width;
        camera.update();
    }

    public void render(float delta) {
        camera.position.set(position);
        // camera.position.lerp(position, .2f); use this to get a smooth return

        if (shakeManager.getShakingTimeLeft() > 0)
            camera.translate(shakeManager.tick(delta));

        camera.update();
    }

    public void setPosition(float x, float y) {
        this.position = new Vector3(x, y, Z);
    }

    @Override
    public ICameraService getCameraService() {
        return cameraService;
    }
}

