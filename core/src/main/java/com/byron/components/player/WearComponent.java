package com.byron.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.byron.models.equip.EquipSlot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WearComponent implements Component {
    public Map<EquipSlot, Entity> wearables;
    public String skin; ;

    public WearComponent(Map<EquipSlot, Entity> wearables, String skin) {
        this.wearables = wearables;
        this.skin = skin;
    }

    public List<Entity> asList() {
        return wearables.keySet().stream().sorted(Comparator.comparingInt(EquipSlot::getRenderPriority)).map(e -> wearables.get(e)).collect(Collectors.toList());
    }


    public List<Entity> getEquipmentAsList() {
        List<Entity> equipment = new ArrayList<>();

        for (EquipSlot slot : EquipSlot.values()) {
            if ((slot.hasAttackComponent() || slot.hasDefenseComponent()) && wearables.containsKey(slot))
                equipment.add(wearables.get(slot));
        }

        return equipment;
    }

    public boolean hasWearable(EquipSlot equipSlot) {
        return wearables.containsKey(equipSlot);
    }

    public Entity getWearable(EquipSlot equipSlot) {
        return wearables.get(equipSlot);
    }

    public String getSkin() {
        return skin;
    }
}