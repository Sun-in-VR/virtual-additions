package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABlockTags {
    public static final TagKey<Block> CLIMBING_ROPES = register("climbing_ropes");
    public static final TagKey<Block> CRYSTALS = register("crystals");
    public static final TagKey<Block> SPOTLIGHT_PERMEABLE = register("spotlight_permeable");
    public static final TagKey<Block> INSIDE_VELOCITY_AFFECTING = register("inside_velocity_affecting");
    public static final TagKey<Block> HEDGES = register("hedges");

    public static void init(){}

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registries.BLOCK.getKey(), idOf(id));
    }

}
