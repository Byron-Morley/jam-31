package com.byron.utils;

import com.badlogic.gdx.math.MathUtils;

public class Random {

    public static <T> T fromArray(T[] array) {
        return array[MathUtils.random(array.length - 1)];
    }
}