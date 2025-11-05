package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorTrimPatterns {
    //
    //IMPORTANT NOTE: Armor trim patterns are registered in ArmorTrimPatternsMixin at the moment
    //
    public static final ResourceKey<TrimPattern> EXOSKELETON = of("exoskeleton");
    public static final ResourceKey<TrimPattern> ROBE = of("robe");

    private static ResourceKey<TrimPattern> of(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, idOf(id));
    }

    public static void init() {
    }
}
