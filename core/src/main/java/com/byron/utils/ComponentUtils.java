package com.byron.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class ComponentUtils {

    public static boolean hasAll(Entity entity, ComponentMapper<?>... mappers) {
        for (int i = 0; i < mappers.length; i++) {
            if (!mappers[i].has(entity)) {
                return false;
            }
        }
        return true;
    }
}