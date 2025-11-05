package com.github.suninvr.virtualadditions.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

public class VARegistryAliases {
    public static void init() {

        blockItemAlias(idOf("floatrock"), idOf("porphyry"));
        blockItemAlias(idOf("grassy_floatrock"), idOf("porphyry"));
        blockItemAlias(idOf("floatrock_stairs"), idOf("porphyry_stairs"));
        blockItemAlias(idOf("floatrock_slab"), idOf("porphyry_slab"));
        blockItemAlias(idOf("floatrock_wall"), idOf("porphyry_wall"));

        blockItemAlias(idOf("floatrock_coal_ore"), ResourceLocation.withDefaultNamespace("coal_ore"));
        blockItemAlias(idOf("floatrock_iron_ore"), ResourceLocation.withDefaultNamespace("iron_ore"));
        blockItemAlias(idOf("floatrock_copper_ore"), ResourceLocation.withDefaultNamespace("copper_ore"));
        blockItemAlias(idOf("floatrock_gold_ore"), ResourceLocation.withDefaultNamespace("gold_ore"));
        blockItemAlias(idOf("floatrock_lapis_ore"), ResourceLocation.withDefaultNamespace("lapis_ore"));
        blockItemAlias(idOf("floatrock_redstone_ore"), ResourceLocation.withDefaultNamespace("redstone_ore"));
        blockItemAlias(idOf("floatrock_emerald_ore"), ResourceLocation.withDefaultNamespace("emerald_ore"));
        blockItemAlias(idOf("floatrock_diamond_ore"), ResourceLocation.withDefaultNamespace("diamond_ore"));

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
        
        
        itemAlias(idOf("amethyst_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("amethyst_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("amethyst_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("amethyst_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("amethyst_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("amethyst_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("amethyst_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("amethyst_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("amethyst_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("amethyst_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("amethyst_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("amethyst_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("amethyst_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("amethyst_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("amethyst_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("amethyst_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("amethyst_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("amethyst_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("amethyst_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("amethyst_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("amethyst_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("amethyst_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("amethyst_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("amethyst_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("amethyst_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("amethyst_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("amethyst_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("amethyst_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("amethyst_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("amethyst_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("amethyst_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("amethyst_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("amethyst_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("amethyst_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("amethyst_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("amethyst_netherite_halberd"), idOf("netherite_halberd"));

        itemAlias(idOf("copper_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("copper_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("copper_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("copper_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("copper_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("copper_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("copper_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("copper_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("copper_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("copper_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("copper_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("copper_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("copper_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("copper_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("copper_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("copper_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("copper_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("copper_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("copper_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("copper_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("copper_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("copper_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("copper_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("copper_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("copper_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("copper_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("copper_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("copper_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("copper_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("copper_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("copper_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("copper_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("copper_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("copper_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("copper_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("copper_netherite_halberd"), idOf("netherite_halberd"));

        itemAlias(idOf("emerald_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("emerald_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("emerald_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("emerald_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("emerald_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("emerald_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("emerald_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("emerald_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("emerald_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("emerald_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("emerald_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("emerald_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("emerald_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("emerald_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("emerald_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("emerald_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("emerald_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("emerald_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("emerald_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("emerald_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("emerald_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("emerald_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("emerald_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("emerald_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("emerald_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("emerald_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("emerald_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("emerald_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("emerald_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("emerald_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("emerald_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("emerald_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("emerald_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("emerald_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("emerald_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("emerald_netherite_halberd"), idOf("netherite_halberd"));

        itemAlias(idOf("quartz_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("quartz_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("quartz_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("quartz_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("quartz_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("quartz_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("quartz_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("quartz_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("quartz_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("quartz_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("quartz_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("quartz_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("quartz_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("quartz_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("quartz_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("quartz_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("quartz_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("quartz_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("quartz_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("quartz_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("quartz_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("quartz_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("quartz_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("quartz_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("quartz_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("quartz_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("quartz_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("quartz_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("quartz_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("quartz_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("quartz_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("quartz_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("quartz_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("quartz_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("quartz_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("quartz_netherite_halberd"), idOf("netherite_halberd"));

        itemAlias(idOf("sculk_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("sculk_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("sculk_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("sculk_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("sculk_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("sculk_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("sculk_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("sculk_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("sculk_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("sculk_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("sculk_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("sculk_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("sculk_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("sculk_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("sculk_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("sculk_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("sculk_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("sculk_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("sculk_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("sculk_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("sculk_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("sculk_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("sculk_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("sculk_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("sculk_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("sculk_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("sculk_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("sculk_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("sculk_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("sculk_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("sculk_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("sculk_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("sculk_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("sculk_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("sculk_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("sculk_netherite_halberd"), idOf("netherite_halberd"));

        itemAlias(idOf("iolite_copper_sword"), ResourceLocation.withDefaultNamespace("copper_sword"));
        itemAlias(idOf("iolite_copper_shovel"), ResourceLocation.withDefaultNamespace("copper_shovel"));
        itemAlias(idOf("iolite_copper_pickaxe"), ResourceLocation.withDefaultNamespace("copper_pickaxe"));
        itemAlias(idOf("iolite_copper_axe"), ResourceLocation.withDefaultNamespace("copper_axe"));
        itemAlias(idOf("iolite_copper_hoe"), ResourceLocation.withDefaultNamespace("copper_hoe"));
        itemAlias(idOf("iolite_copper_halberd"), idOf("copper_halberd"));
        itemAlias(idOf("iolite_iron_sword"), ResourceLocation.withDefaultNamespace("iron_sword"));
        itemAlias(idOf("iolite_iron_shovel"), ResourceLocation.withDefaultNamespace("iron_shovel"));
        itemAlias(idOf("iolite_iron_pickaxe"), ResourceLocation.withDefaultNamespace("iron_pickaxe"));
        itemAlias(idOf("iolite_iron_axe"), ResourceLocation.withDefaultNamespace("iron_axe"));
        itemAlias(idOf("iolite_iron_hoe"), ResourceLocation.withDefaultNamespace("iron_hoe"));
        itemAlias(idOf("iolite_iron_halberd"), idOf("iron_halberd"));
        itemAlias(idOf("iolite_golden_sword"), ResourceLocation.withDefaultNamespace("golden_sword"));
        itemAlias(idOf("iolite_golden_shovel"), ResourceLocation.withDefaultNamespace("golden_shovel"));
        itemAlias(idOf("iolite_golden_pickaxe"), ResourceLocation.withDefaultNamespace("golden_pickaxe"));
        itemAlias(idOf("iolite_golden_axe"), ResourceLocation.withDefaultNamespace("golden_axe"));
        itemAlias(idOf("iolite_golden_hoe"), ResourceLocation.withDefaultNamespace("golden_hoe"));
        itemAlias(idOf("iolite_golden_halberd"), idOf("golden_halberd"));
        itemAlias(idOf("iolite_steel_sword"), idOf("steel_sword"));
        itemAlias(idOf("iolite_steel_shovel"), idOf("steel_shovel"));
        itemAlias(idOf("iolite_steel_pickaxe"), idOf("steel_pickaxe"));
        itemAlias(idOf("iolite_steel_axe"), idOf("steel_axe"));
        itemAlias(idOf("iolite_steel_hoe"), idOf("steel_hoe"));
        itemAlias(idOf("iolite_steel_halberd"), idOf("steel_halberd"));
        itemAlias(idOf("iolite_diamond_sword"), ResourceLocation.withDefaultNamespace("diamond_sword"));
        itemAlias(idOf("iolite_diamond_shovel"), ResourceLocation.withDefaultNamespace("diamond_shovel"));
        itemAlias(idOf("iolite_diamond_pickaxe"), ResourceLocation.withDefaultNamespace("diamond_pickaxe"));
        itemAlias(idOf("iolite_diamond_axe"), ResourceLocation.withDefaultNamespace("diamond_axe"));
        itemAlias(idOf("iolite_diamond_hoe"), ResourceLocation.withDefaultNamespace("diamond_hoe"));
        itemAlias(idOf("iolite_diamond_halberd"), idOf("diamond_halberd"));
        itemAlias(idOf("iolite_netherite_sword"), ResourceLocation.withDefaultNamespace("netherite_sword"));
        itemAlias(idOf("iolite_netherite_shovel"), ResourceLocation.withDefaultNamespace("netherite_shovel"));
        itemAlias(idOf("iolite_netherite_pickaxe"), ResourceLocation.withDefaultNamespace("netherite_pickaxe"));
        itemAlias(idOf("iolite_netherite_axe"), ResourceLocation.withDefaultNamespace("netherite_axe"));
        itemAlias(idOf("iolite_netherite_hoe"), ResourceLocation.withDefaultNamespace("netherite_hoe"));
        itemAlias(idOf("iolite_netherite_halberd"), idOf("netherite_halberd"));

    }

    private static void itemAlias(ResourceLocation oldId, ResourceLocation newId) {
        BuiltInRegistries.ITEM.addAlias(oldId, newId);
    }
    
    private static void blockItemAlias(ResourceLocation oldId, ResourceLocation newId) {
        BuiltInRegistries.BLOCK.addAlias(oldId, newId);
        BuiltInRegistries.ITEM.addAlias(oldId, newId);
    }

    private static void blockAlias(ResourceLocation oldId, ResourceLocation newId) {
        BuiltInRegistries.BLOCK.addAlias(oldId, newId);
    }
}
