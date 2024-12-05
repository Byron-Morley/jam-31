package com.byron.models.item;


import com.byron.models.equip.EquipSlot;
import com.byron.models.equip.EquipType;

public class EquipmentInformation {
    private EquipSlot equipSlot;
    private EquipType equipType;
    private int attack;
    private int defense;

    public EquipmentInformation() { }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public void setEquipSlot(EquipSlot equipSlot) {
        this.equipSlot = equipSlot;
    }

    public EquipType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipType equipType) {
        this.equipType = equipType;
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

    @Override
    public String toString() {
        return "EquipmentInformation{" +
                "equipSlot=" + equipSlot +
                ", equipType=" + equipType +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}
