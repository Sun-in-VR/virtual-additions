package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABlockTags {
    public static final TagKey<Block> CLIMBING_ROPES = register("climbing_ropes");
    public static final TagKey<Block> CRYSTALS = register("crystals");
    public static final TagKey<Block> SPOTLIGHT_PERMEABLE = register("spotlight_permeable");
    public static final TagKey<Block> HEDGES = register("hedges");
    public static final TagKey<Block> ACID_UNBREAKABLE = register("acid_unbreakable");
    public static final TagKey<Block> LUMWASP_NEST_REPLACEABLE = register("lumwasp_nest_replaceable");
    public static final TagKey<Block> SILKBULBS = TagKey.create(Registries.BLOCK, idOf("silkbulbs"));
    public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = register("incorrect_for_steel_tool");
    public static final TagKey<Block> USES_STEEL_SCRAPE_PARTICLES = register("uses_steel_scrape_particles");
    public static final TagKey<Block> SPECTRE_SPAWNABLE_ON = register("spectre_spawnable_on");
    public static final TagKey<Block> ROCK_SALT_ORES = register("rock_salt_ores");
    public static final TagKey<Block> HALBERD_SWING_BREAKABLES = register("halberd_swing_breakables");

    private static TagKey<Block> register(String id) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(), idOf(id));
    }

    public static void init(){}

}
