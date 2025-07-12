package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.trim.ArmorTrimAssets;

import java.util.Map;

public class VAArmorTrimAssets {
    public static final ArmorTrimAssets STEEL = ArmorTrimAssets.of("virtual_additions_steel", Map.of(VAArmorMaterial.STEEL.assetId(), "virtual_additions_steel_darker"));
    public static final ArmorTrimAssets ROCK_SALT = ArmorTrimAssets.of("virtual_additions_rock_salt");
    public static final ArmorTrimAssets IOLITE = ArmorTrimAssets.of("virtual_additions_iolite");
}
