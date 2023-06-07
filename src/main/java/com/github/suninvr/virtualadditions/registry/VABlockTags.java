package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABlockTags {
    public static final TagKey<Block> CLIMBING_ROPES = register("climbing_ropes");
    public static final TagKey<Block> CRYSTALS = register("crystals");
    public static final TagKey<Block> SPOTLIGHT_PERMEABLE = register("spotlight_permeable");
    public static final TagKey<Block> HEDGES = register("hedges");
    public static final TagKey<Block> ACID_UNBREAKABLE = register("acid_unbreakable");
    public static final TagKey<Block> LUMWASP_NEST_REPLACEABLE = register("lumwasp_nest_replaceable");
    public static final TagKey<Block> SCULK_GILD_EFFECTIVE = register("sculk_gild_effective");
    public static final TagKey<Block> SCULK_GILD_STRONGLY_EFFECTIVE = register("sculk_gild_strongly_effective");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registries.BLOCK.getKey(), idOf(id));
    }

    public static void init(){}

}
