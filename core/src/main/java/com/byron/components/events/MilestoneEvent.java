package com.byron.components.events;

import com.badlogic.ashley.core.Component;
import com.byron.constants.MilestoneType;

public class MilestoneEvent implements Component {

    public final MilestoneType type;

    public MilestoneEvent(MilestoneType type) {
        this.type = type;
    }
}