package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> STEEL = register("steel");
    public static final RegistryKey<ArmorTrimMaterial> ROCK_SALT = register("rock_salt");
    public static final RegistryKey<ArmorTrimMaterial> IOLITE = register("iolite");

    private static RegistryKey<ArmorTrimMaterial> register(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, idOf(id));
    }

    public static void init() {
    }
}
