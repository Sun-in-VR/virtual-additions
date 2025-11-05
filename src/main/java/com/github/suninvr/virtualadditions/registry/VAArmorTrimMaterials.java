package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorTrimMaterials {
    public static final ResourceKey<TrimMaterial> STEEL = register("steel");
    public static final ResourceKey<TrimMaterial> ROCK_SALT = register("rock_salt");
    public static final ResourceKey<TrimMaterial> IOLITE = register("iolite");

    private static ResourceKey<TrimMaterial> register(String id) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, idOf(id));
    }

    public static void init() {
    }
}
