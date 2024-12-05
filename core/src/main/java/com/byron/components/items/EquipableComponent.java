package com.byron.components.items;

import com.badlogic.ashley.core.Component;
import com.byron.models.equip.EquipSlot;
import com.byron.models.equip.EquipType;
import com.byron.models.item.EquipmentInformation;

public class EquipableComponent implements Component {
    private EquipType equipType;
    private EquipSlot equipSlot;
    private int attack;
    private int defense;

    public EquipableComponent(EquipmentInformation eq) {
        this.equipSlot = eq.getEquipSlot();
        this.equipType = eq.getEquipType();
        this.attack = eq.getAttack();
        this.defense = eq.getDefense();
    }

    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public void setEquipSlot(EquipSlot equipSlot) {
        this.equipSlot = equipSlot;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
