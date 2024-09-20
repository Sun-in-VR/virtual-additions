package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorMaterial {
    public static final ArmorMaterial STEEL = new ArmorMaterial(22, Util.make(new EnumMap<>(EquipmentType.class), map ->{
        map.put(EquipmentType.BOOTS, 2);
        map.put(EquipmentType.LEGGINGS, 5);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 2);
    }), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0.0F, VAItemTags.REPAIRS_STEEL_ARMOR, idOf("steel"));
}
