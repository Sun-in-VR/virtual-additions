package com.github.suninvr.virtualadditions.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAEntityTypeTags {
    public static final TagKey<EntityType<?>> PASSES_THROUGH_WEBBED_SILK = register("passes_through_webbed_silk");

    public static void init(){}

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(Registries.ENTITY_TYPE.getKey(), idOf(id));
    }

}
