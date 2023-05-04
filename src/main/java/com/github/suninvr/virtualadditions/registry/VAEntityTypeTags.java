package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VAEntityTypeTags {
    public static final TagKey<EntityType<?>> COLLIDES_WITH_WEBBED_SILK = register("collides_with_webbed_silk");

    public static void init(){}

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(Registries.ENTITY_TYPE.getKey(), idOf(id));
    }

}
