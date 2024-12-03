package com.byron.services;

import com.byron.interfaces.ICameraManager;
import com.byron.interfaces.ICameraService;

public class CameraService extends Service implements ICameraService {
    ICameraManager cameraManager;

    public CameraService(ICameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }
}
