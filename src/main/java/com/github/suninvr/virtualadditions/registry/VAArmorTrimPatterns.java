package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAArmorTrimPatterns {
    //
    //IMPORTANT NOTE: Armor trim patterns are registered in ArmorTrimPatternsMixin at the moment
    //
    public static final RegistryKey<ArmorTrimPattern> EXOSKELETON = of("exoskeleton");
    public static final RegistryKey<ArmorTrimPattern> ROBE = of("robe");

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, idOf(id));
    }

    public static void init() {
    }
}
