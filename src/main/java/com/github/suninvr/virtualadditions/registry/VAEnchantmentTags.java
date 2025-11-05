package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEnchantmentTags {

    private static TagKey<Enchantment> of(String id) {
        return TagKey.create(Registries.ENCHANTMENT, idOf(id));
    }
}
