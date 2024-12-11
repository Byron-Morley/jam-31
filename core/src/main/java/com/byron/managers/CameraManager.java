package com.byron.managers;

import static com.byron.utils.Config.PX_PER_METER;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.byron.engine.GameResources;
import com.byron.interfaces.ICameraManager;
import com.byron.interfaces.ICameraService;
import com.byron.services.CameraService;
import com.byron.utils.Config;

public class CameraManager implements ICameraManager {
    static final float MAXIMUM_DISTANCE_FROM_SHAKE = 12;
    static final int Z = 0;

    private final ScreenShakeManager shakeManager;
    private final OrthographicCamera camera;
    private final Vector3 position;
    private final ICameraService cameraService;

    public CameraManager() {
        cameraService = new CameraService(this);

        this.position = new Vector3(0, 0, Z);
        this.shakeManager = new ScreenShakeManager();
        camera = GameResources.get().getCamera();
        camera.setToOrtho(false, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.zoom = 0.25f;
        camera.update();
        System.out.println("Screen dimensions (px): " + Config.SCREEN_WIDTH + " x " + Config.SCREEN_HEIGHT);
        System.out.println("Viewport dimensions (m): " + camera.viewportWidth + " x " + camera.viewportHeight);
        System.out.println("Camera center: " + camera.position.x + "," + camera.position.y);
    }

    public void shake(float power, float duration, Vector2 epicenter) {
        if (cameraCloseToEpicenter(epicenter))
            shakeManager.shake(power, duration);
    }

    private boolean cameraCloseToEpicenter(Vector2 epicenter) {
        return new Vector3(epicenter, Z).sub(position).len() < MAXIMUM_DISTANCE_FROM_SHAKE;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width / PX_PER_METER;
        camera.viewportHeight = height / PX_PER_METER;
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
        position.set(x, y, Z);
    }

    @Override
    public ICameraService getCameraService() {
        return cameraService;
    }
}