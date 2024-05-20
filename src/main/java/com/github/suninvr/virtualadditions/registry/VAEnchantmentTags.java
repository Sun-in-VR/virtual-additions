package com.github.suninvr.virtualadditions.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEnchantmentTags {
    public static final TagKey<Enchantment> REDUCES_SCULK_GILD_MINING_SPEED = of("reduces_sculk_gild_mining_speed");

    private static TagKey<Enchantment> of(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, idOf(id));
    }
}
