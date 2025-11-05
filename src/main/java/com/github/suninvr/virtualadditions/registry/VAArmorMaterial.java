package com.github.suninvr.virtualadditions.registry;

import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorMaterial {
    public static final ResourceKey<EquipmentAsset> STEEL_ASSET_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, idOf("steel"));
    public static final ArmorMaterial STEEL = new ArmorMaterial(22, Util.make(new EnumMap<>(ArmorType.class), map ->{
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 3);
        map.put(ArmorType.BODY, 9);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, VAItemTags.REPAIRS_STEEL_ARMOR, STEEL_ASSET_KEY);
}
