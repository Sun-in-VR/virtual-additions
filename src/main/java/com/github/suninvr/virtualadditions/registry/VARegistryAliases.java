package com.github.suninvr.virtualadditions.registry;

import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARegistryAliases {
    public static void init() {

        blockItemAlias(idOf("floatrock"), idOf("porphyry"));
        blockItemAlias(idOf("grassy_floatrock"), idOf("porphyry"));
        blockItemAlias(idOf("floatrock_stairs"), idOf("porphyry_stairs"));
        blockItemAlias(idOf("floatrock_slab"), idOf("porphyry_slab"));
        blockItemAlias(idOf("floatrock_wall"), idOf("porphyry_wall"));

        blockItemAlias(idOf("floatrock_coal_ore"), Identifier.ofVanilla("coal_ore"));
        blockItemAlias(idOf("floatrock_iron_ore"), Identifier.ofVanilla("iron_ore"));
        blockItemAlias(idOf("floatrock_copper_ore"), Identifier.ofVanilla("copper_ore"));
        blockItemAlias(idOf("floatrock_gold_ore"), Identifier.ofVanilla("gold_ore"));
        blockItemAlias(idOf("floatrock_lapis_ore"), Identifier.ofVanilla("lapis_ore"));
        blockItemAlias(idOf("floatrock_redstone_ore"), Identifier.ofVanilla("redstone_ore"));
        blockItemAlias(idOf("floatrock_emerald_ore"), Identifier.ofVanilla("emerald_ore"));
        blockItemAlias(idOf("floatrock_diamond_ore"), Identifier.ofVanilla("diamond_ore"));

        blockItemAlias(idOf("floatrock_bricks"), idOf("porphyry_bricks"));
        blockItemAlias(idOf("floatrock_brick_stairs"), idOf("porphyry_brick_stairs"));
        blockItemAlias(idOf("floatrock_brick_slab"), idOf("porphyry_brick_slab"));
        blockItemAlias(idOf("floatrock_brick_wall"), idOf("porphyry_brick_wall"));

        blockItemAlias(idOf("polished_floatrock"), idOf("polished_porphyry"));
        blockItemAlias(idOf("polished_floatrock_stairs"), idOf("polished_porphyry_stairs"));
        blockItemAlias(idOf("polished_floatrock_slab"), idOf("polished_porphyry_slab"));
        blockItemAlias(idOf("polished_floatrock_wall"), idOf("polished_porphyry_wall"));

        blockItemAlias(idOf("aerobloom_log"), idOf("soulbloom_log"));
        blockItemAlias(idOf("aerobloom_wood"), idOf("soulbloom_wood"));
        blockItemAlias(idOf("stripped_aerobloom_log"), idOf("stripped_soulbloom_log"));
        blockItemAlias(idOf("stripped_aerobloom_wood"), idOf("stripped_soulbloom_wood"));
        blockItemAlias(idOf("aerobloom_planks"), idOf("soulbloom_planks"));
        blockItemAlias(idOf("aerobloom_stairs"), idOf("soulbloom_stairs"));
        blockItemAlias(idOf("aerobloom_slab"), idOf("soulbloom_slab"));
        blockItemAlias(idOf("aerobloom_fence"), idOf("soulbloom_fence"));
        blockItemAlias(idOf("aerobloom_fence_gate"), idOf("soulbloom_fence_gate"));
        blockItemAlias(idOf("aerobloom_door"), idOf("soulbloom_door"));
        blockItemAlias(idOf("aerobloom_trapdoor"), idOf("soulbloom_trapdoor"));
        blockItemAlias(idOf("aerobloom_pressure_plate"), idOf("soulbloom_pressure_plate"));
        blockItemAlias(idOf("aerobloom_button"), idOf("soulbloom_button"));
        blockItemAlias(idOf("aerobloom_hedge"), idOf("soulbloom_hedge"));
        blockItemAlias(idOf("aerobloom_leaves"), idOf("soulbloom_leaves"));
        blockItemAlias(idOf("aerobloom_sapling"), idOf("soulbloom_sapling"));
        blockItemAlias(idOf("aerobloom_sign"), idOf("soulbloom_sign"));
        blockAlias(idOf("aerobloom_wall_sign"), idOf("soulbloom_wall_sign"));
        blockItemAlias(idOf("aerobloom_hanging_sign"), idOf("soulbloom_hanging_sign"));
        blockAlias(idOf("aerobloom_wall_hanging_sign"), idOf("soulbloom_wall_hanging_sign"));

    }
    
    private static void blockItemAlias(Identifier oldId, Identifier newId) {
        Registries.BLOCK.addAlias(oldId, newId);
        Registries.ITEM.addAlias(oldId, newId);
    }

    private static void blockAlias(Identifier oldId, Identifier newId) {
        Registries.BLOCK.addAlias(oldId, newId);
    }
}
