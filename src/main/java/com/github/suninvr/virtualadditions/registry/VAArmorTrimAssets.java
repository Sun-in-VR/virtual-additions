package com.github.suninvr.virtualadditions.registry;

import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;

import java.util.Map;

public class VAArmorTrimAssets {
    public static final MaterialAssetGroup STEEL = MaterialAssetGroup.create("virtual_additions_steel", Map.of(VAArmorMaterial.STEEL.assetId(), "virtual_additions_steel_darker"));
    public static final MaterialAssetGroup ROCK_SALT = MaterialAssetGroup.create("virtual_additions_rock_salt");
    public static final MaterialAssetGroup IOLITE = MaterialAssetGroup.create("virtual_additions_iolite");
}
