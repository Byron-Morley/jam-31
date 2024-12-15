package com.byron.models;

import static com.byron.utils.Config.ENEMY_TURN_RATIO;

public class Stats {

    int maxHealth;
    int health;
    int attack;
    int speed = ENEMY_TURN_RATIO;
    int armor;
    int maxArmor;

    public Stats() {
        health = 100;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpeed() {
        return speed;
    }

    public int getArmor() {
        return armor;
    }

    public int getMaxArmor() {
        return maxArmor;
    }
}