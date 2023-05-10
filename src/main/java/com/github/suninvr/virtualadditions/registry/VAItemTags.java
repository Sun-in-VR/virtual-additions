package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAItemTags {
    public static final TagKey<Item> LUMWASP_LARVAE_FOOD = register("lumwasp_larvae_food");

    public static void init(){}

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registries.ITEM.getKey(), idOf(id));
    }

}
