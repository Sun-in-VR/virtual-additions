package com.github.suninvr.virtualadditions.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAItemTags {
    public static final TagKey<Item> LUMWASP_LARVAE_FOOD = register("lumwasp_larvae_food");
    public static final TagKey<Item> GILDED_TOOLS = TagKey.of(RegistryKeys.ITEM, idOf("gilded_tools"));
    public static final TagKey<Item> AEROBLOOM_LOGS = TagKey.of(RegistryKeys.ITEM, idOf("aerobloom_logs"));

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registries.ITEM.getKey(), idOf(id));
    }

    public static void init(){}

}
