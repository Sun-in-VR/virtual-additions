package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorMaterial {
    public static final RegistryKey<EquipmentAsset> STEEL_ASSET_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, idOf("steel"));
    public static final ArmorMaterial STEEL = new ArmorMaterial(22, Util.make(new EnumMap<>(EquipmentType.class), map ->{
        map.put(EquipmentType.BOOTS, 3);
        map.put(EquipmentType.LEGGINGS, 5);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 3);
        map.put(EquipmentType.BODY, 9);
    }), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0.0F, VAItemTags.REPAIRS_STEEL_ARMOR, STEEL_ASSET_KEY);
}
