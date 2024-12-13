package com.byron.constants;

import java.util.HashMap;
import java.util.Map;

public enum Weapon {

    REGULAR_SWORD(1, "regularSword"), BLUE_SWORD(2, "blueSword"), RED_SWORD(3, "redSword"), BLACK_SWORD(4, "blackSword");

    public final int damage;
    public final String name;
    public static final Weapon[] TIER_1_WEAPONS = new Weapon[]{REGULAR_SWORD, BLUE_SWORD};
    public static final Weapon[] TIER_2_WEAPONS = new Weapon[]{RED_SWORD, BLACK_SWORD};
    public static final Map<Integer, Weapon[]> TIER_MAP;

    static {
        TIER_MAP = new HashMap<>();
        TIER_MAP.put(1, TIER_1_WEAPONS);
        TIER_MAP.put(2, TIER_2_WEAPONS);
    }

    Weapon(int damage, String name) {
        this.damage = damage;
        this.name = name;
    }
}