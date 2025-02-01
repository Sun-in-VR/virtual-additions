package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> STEEL = of("steel");
    public static final RegistryKey<ArmorTrimMaterial> ROCK_SALT = of("rock_salt");
    public static final RegistryKey<ArmorTrimMaterial> IOLITE = of("iolite");

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, idOf(id));
    }

    public static void init() {
    }
}
