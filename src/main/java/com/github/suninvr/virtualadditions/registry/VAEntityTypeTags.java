package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public class VAEntityTypeTags {
    public static final TagKey<EntityType<?>> PASSES_THROUGH_WEBBED_SILK = register("passes_through_webbed_silk");
    public static final TagKey<EntityType<?>> SPECTRE_BUFF_TARGETS = register("spectre_buff_targets");
    public static final TagKey<EntityType<?>> IGNORES_SPRING_LOTUS = register("ignores_spring_lotus");

    public static void init(){}

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), idOf(id));
    }

}
