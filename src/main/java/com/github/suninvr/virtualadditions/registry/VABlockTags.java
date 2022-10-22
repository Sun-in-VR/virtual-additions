package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABlockTags {
    public static final TagKey<Block> CLIMBING_ROPES = register("climbing_ropes");
    public static final TagKey<Block> CRYSTALS = register("crystals");
    public static final TagKey<Block> SPOTLIGHT_PERMEABLE = register("spotlight_permeable");
    public static final TagKey<Block> INSIDE_VELOCITY_AFFECTING = register("inside_velocity_affecting");

    public static void init(){}

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, idOf(id));
    }

}
