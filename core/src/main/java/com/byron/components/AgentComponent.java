package com.byron.components;

import com.badlogic.ashley.core.Component;
import com.byron.models.Stats;

public class AgentComponent implements Component {

    Stats stats;
    public float damageTimer = 0;


    public AgentComponent(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}
