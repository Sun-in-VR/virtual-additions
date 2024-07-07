package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VABlockTags {
    public static final TagKey<Block> CLIMBING_ROPES = register("climbing_ropes");
    public static final TagKey<Block> CRYSTALS = register("crystals");
    public static final TagKey<Block> SPOTLIGHT_PERMEABLE = register("spotlight_permeable");
    public static final TagKey<Block> HEDGES = register("hedges");
    public static final TagKey<Block> ACID_UNBREAKABLE = register("acid_unbreakable");
    public static final TagKey<Block> LUMWASP_NEST_REPLACEABLE = register("lumwasp_nest_replaceable");
    public static final TagKey<Block> SILKBULBS = TagKey.of(RegistryKeys.BLOCK, idOf("silkbulbs"));
    public static final TagKey<Block> FLOATROCK_ORE_REPLACEABLES = register("floatrock_ore_replaceables");
    public static final TagKey<Block> SKYLANDS_CARVER_REPLACEABLES = register("skylands_carver_replaceables");
    public static final TagKey<Block> NO_FOLIAGE_WORLDGEN = register("no_foliage_worldgen");
    public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = register("incorrect_for_steel_tool");
    public static final TagKey<Block> USES_STEEL_SCRAPE_PARTICLES = register("uses_steel_scrape_particles");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registries.BLOCK.getKey(), idOf(id));
    }

    public static void init(){}

}
