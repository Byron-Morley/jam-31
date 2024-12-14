package com.byron.components.weapons;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {

    public int damage;

    public DamageComponent(int damage) {
        this.damage = damage;
    }
}