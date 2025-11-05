package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public final class VABlockTagProvider {

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }

    public static FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    static class BaseProvider extends Provider {

        public BaseProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {


            addTo(BlockTags.SWORD_EFFICIENT).addOptionalTag(HEDGES.location());
            addTo(BlockTags.FLOWERS, VABlocks.CHERRY_HEDGE, VABlocks.FLOWERING_AZALEA_HEDGE, VABlocks.BLUE_PETALS, VABlocks.SPRING_LOTUS, VABlocks.SMALL_SPRING_LOTUS, VABlocks.SOUL_SPROUT);
            addTo(BlockTags.BEE_ATTRACTIVE, VABlocks.CHERRY_HEDGE, VABlocks.FLOWERING_AZALEA_HEDGE, VABlocks.BLUE_PETALS, VABlocks.SPRING_LOTUS, VABlocks.SMALL_SPRING_LOTUS, VABlocks.SOUL_SPROUT);
            addTo(BlockTags.FROG_PREFER_JUMP_TO, VABlocks.SPRING_LOTUS);
            addTo(BlockTags.INSIDE_STEP_SOUND_BLOCKS, VABlocks.BLUE_PETALS);
            addTo(BlockTags.BEACON_BASE_BLOCKS, VABlocks.STEEL_BLOCK, VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, VABlocks.IOLITE_BLOCK);
            addTo(BlockTags.CLIMBABLE, VABlocks.CLIMBING_ROPE, VABlocks.CLIMBING_ROPE_ANCHOR, VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR);
            addTo(BlockTags.COMBINATION_STEP_SOUND_BLOCKS, VABlocks.NECROTIC_ROOTS);
            addTo(BlockTags.CROPS, VABlocks.CORN_CROP, VABlocks.COTTON, VABlocks.TOMATO, VABlocks.CABBAGE, VABlocks.WISDOM_BERRY);
            addTo(BlockTags.DEEPSLATE_ORE_REPLACEABLES, VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);
            addTo(BlockTags.DOORS, VABlocks.STEEL_DOOR, VABlocks.EXPOSED_STEEL_DOOR, VABlocks.WEATHERED_STEEL_DOOR, VABlocks.OXIDIZED_STEEL_DOOR, VABlocks.WAXED_STEEL_DOOR, VABlocks.WAXED_EXPOSED_STEEL_DOOR, VABlocks.WAXED_WEATHERED_STEEL_DOOR);
            addTo(BlockTags.TRAPDOORS, VABlocks.STEEL_TRAPDOOR, VABlocks.EXPOSED_STEEL_TRAPDOOR, VABlocks.WEATHERED_STEEL_TRAPDOOR, VABlocks.OXIDIZED_STEEL_TRAPDOOR, VABlocks.WAXED_STEEL_TRAPDOOR, VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR);
            addTo(BlockTags.FENCES, VABlocks.STEEL_FENCE);
            addTo(BlockTags.MAINTAINS_FARMLAND, VABlocks.CORN_CROP, VABlocks.COTTON, VABlocks.TOMATO, VABlocks.CABBAGE, VABlocks.WISDOM_BERRY);
            addTo(BlockTags.REPLACEABLE, VABlocks.ACID, VABlocks.FRAYED_SILK, VABlocks.NECROTIC_ROOTS);
            addTo(BlockTags.REPLACEABLE_BY_TREES, VABlocks.NECROTIC_ROOTS, VABlocks.BLUE_PETALS);
            addTo(BlockTags.REPLACEABLE_BY_MUSHROOMS, VABlocks.NECROTIC_ROOTS);
            addTo(BlockTags.SCULK_REPLACEABLE, VABlocks.SILK_BLOCK, VABlocks.WEBBED_SILK, VABlocks.ROCK_SALT_BLOCK);
            addTo(BlockTags.REPLACEABLE, VABlocks.SPOTLIGHT_LIGHT);
            addTo(BlockTags.FLOWER_POTS, VABlocks.POTTED_SOULBLOOM_SAPLING, VABlocks.POTTED_WITHERED_SAPLING, VABlocks.POTTED_GREENCAP_MUSHROOM);
            addTo(BlockTags.NYLIUM, VABlocks.NECROTIC_NYLIUM);
            addTo(BlockTags.MUSHROOM_GROW_BLOCK, VABlocks.NECROTIC_NYLIUM);
            addTo(BlockTags.ENDERMAN_HOLDABLE, VABlocks.NECROTIC_NYLIUM, VABlocks.NECROTIC_ROOTS);
            addTo(BlockTags.FIRE, VABlocks.SPECTRAL_FIRE);
            addTo(BlockTags.WALL_POST_OVERRIDE, VABlocks.SPECTRAL_TORCH);
            addTo(BlockTags.CAMEL_SAND_STEP_SOUND_BLOCKS, VABlocks.SPECTRAL_SAND);

            configureColorfulBlockSet(VACollections.CHARTREUSE);
            configureColorfulBlockSet(VACollections.MAROON);
            configureColorfulBlockSet(VACollections.INDIGO);
            configureColorfulBlockSet(VACollections.PLUM);
            configureColorfulBlockSet(VACollections.VIRIDIAN);
            configureColorfulBlockSet(VACollections.TAN);
            configureColorfulBlockSet(VACollections.SINOPIA);
            configureColorfulBlockSet(VACollections.LILAC);

            addTo(ORES,VABlocks.IOLITE_ORE, VABlocks.ROCK_SALT_ORE, VABlocks.DEEPSLATE_ROCK_SALT_ORE);
            addTo(VABlockTags.ROCK_SALT_ORES, VABlocks.ROCK_SALT_ORE, VABlocks.DEEPSLATE_ROCK_SALT_ORE);
            addTo(BlockTags.OVERWORLD_CARVER_REPLACEABLES, VABlocks.ROCK_SALT_ORE, VABlocks.DEEPSLATE_ROCK_SALT_ORE);

            addTo(BlockTags.MINEABLE_WITH_HOE,
                            VABlocks.SILK_BLOCK,
                            VABlocks.WEBBED_SILK,
                            VABlocks.LUMWASP_NEST,
                            VABlocks.SPRING_LOTUS
                    )
                    .addOptionalTag(SILKBULBS.location())
                    .addOptionalTag(HEDGES.location());

            addTo(BlockTags.MINEABLE_WITH_PICKAXE,
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.SPOTLIGHT,
                    VABlocks.ROCK_SALT_BLOCK,
                    VABlocks.ROCK_SALT_CRYSTAL,
                    VABlocks.CHISELED_ROCK_SALT_BRICKS,
                    VABlocks.NECROTIC_NYLIUM,
                    VABlocks.BONE_LITTER,
                    VABlocks.BONE_PILE,
                    VABlocks.SPECTRAL_LANTERN
            );

            configureMinable(BlockTags.MINEABLE_WITH_PICKAXE, 1,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.EXPOSED_STEEL_BLOCK,
                    VABlocks.WEATHERED_STEEL_BLOCK,
                    VABlocks.OXIDIZED_STEEL_BLOCK,
                    VABlocks.WAXED_STEEL_BLOCK,
                    VABlocks.WAXED_EXPOSED_STEEL_BLOCK,
                    VABlocks.WAXED_WEATHERED_STEEL_BLOCK,
                    VABlocks.WAXED_OXIDIZED_STEEL_BLOCK,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.STEEL_DOOR,
                    VABlocks.EXPOSED_STEEL_DOOR,
                    VABlocks.WEATHERED_STEEL_DOOR,
                    VABlocks.OXIDIZED_STEEL_DOOR,
                    VABlocks.WAXED_STEEL_DOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_DOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_DOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_DOOR,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.ENTANGLEMENT_DRIVE,
                    VABlocks.REDSTONE_BRIDGE,
                    VABlocks.CAGELIGHT,
                    VABlocks.STEEL_GRATE,
                    VABlocks.EXPOSED_STEEL_GRATE,
                    VABlocks.WEATHERED_STEEL_GRATE,
                    VABlocks.OXIDIZED_STEEL_GRATE,
                    VABlocks.WAXED_STEEL_GRATE,
                    VABlocks.WAXED_EXPOSED_STEEL_GRATE,
                    VABlocks.WAXED_WEATHERED_STEEL_GRATE,
                    VABlocks.WAXED_OXIDIZED_STEEL_GRATE,
                    VABlocks.ROCK_SALT_ORE,
                    VABlocks.DEEPSLATE_ROCK_SALT_ORE
            );

            configureMinable(BlockTags.MINEABLE_WITH_PICKAXE, 3,
                    VABlocks.IOLITE_ORE,
                    VABlocks.IOLITE_BLOCK
            );

            configureMinable(BlockTags.MINEABLE_WITH_AXE, 0,
                    VABlocks.COLORING_STATION
                    );

            configureMinable(BlockTags.MINEABLE_WITH_SHOVEL, 0,
                    VABlocks.SPECTRAL_SAND
                    );

            configureOverworldStone(VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);



            configureFamily(BlockTags.MINEABLE_WITH_PICKAXE, 1,
                    VACollections.CUT_STEEL,
                    VACollections.EXPOSED_CUT_STEEL,
                    VACollections.WEATHERED_CUT_STEEL,
                    VACollections.OXIDIZED_CUT_STEEL,
                    VACollections.WAXED_CUT_STEEL,
                    VACollections.WAXED_EXPOSED_CUT_STEEL,
                    VACollections.WAXED_WEATHERED_CUT_STEEL,
                    VACollections.WAXED_OXIDIZED_CUT_STEEL
            );
            
            configureFamily(VACollections.CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.EXPOSED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.WEATHERED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.OXIDIZED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.WAXED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.WAXED_EXPOSED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.WAXED_WEATHERED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            configureFamily(VACollections.WAXED_OXIDIZED_CUT_STEEL, VABlockTags.USES_STEEL_SCRAPE_PARTICLES);
            addTo(VABlockTags.USES_STEEL_SCRAPE_PARTICLES, 
                    VABlocks.STEEL_BLOCK,
                    VABlocks.EXPOSED_STEEL_BLOCK,
                    VABlocks.WEATHERED_STEEL_BLOCK,
                    VABlocks.OXIDIZED_STEEL_BLOCK,
                    VABlocks.WAXED_STEEL_BLOCK,
                    VABlocks.WAXED_EXPOSED_STEEL_BLOCK,
                    VABlocks.WAXED_WEATHERED_STEEL_BLOCK,
                    VABlocks.WAXED_OXIDIZED_STEEL_BLOCK,
                    VABlocks.STEEL_GRATE,
                    VABlocks.EXPOSED_STEEL_GRATE,
                    VABlocks.WEATHERED_STEEL_GRATE,
                    VABlocks.OXIDIZED_STEEL_GRATE,
                    VABlocks.WAXED_STEEL_GRATE,
                    VABlocks.WAXED_EXPOSED_STEEL_GRATE,
                    VABlocks.WAXED_WEATHERED_STEEL_GRATE,
                    VABlocks.WAXED_OXIDIZED_STEEL_GRATE,
                    VABlocks.STEEL_DOOR,
                    VABlocks.EXPOSED_STEEL_DOOR,
                    VABlocks.WEATHERED_STEEL_DOOR,
                    VABlocks.OXIDIZED_STEEL_DOOR,
                    VABlocks.WAXED_STEEL_DOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_DOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_DOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_DOOR,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.OXIDIZED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR,
                    VABlocks.WAXED_OXIDIZED_STEEL_TRAPDOOR
            );
            
            configureFamily(BlockTags.MINEABLE_WITH_PICKAXE, 0,
                    VACollections.COBBLED_HORNFELS,
                    VACollections.COBBLED_BLUESCHIST,
                    VACollections.COBBLED_SYENITE,
                    VACollections.POLISHED_HORNFELS,
                    VACollections.POLISHED_BLUESCHIST,
                    VACollections.POLISHED_SYENITE,
                    VACollections.HORNFELS_TILES,
                    VACollections.BLUESCHIST_BRICKS,
                    VACollections.SYENITE_BRICKS,
                    VACollections.ROCK_SALT_BRICKS
            );

            addTo(VABlockTags.INCORRECT_FOR_STEEL_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL.location());

            addTo(VABlockTags.ACID_UNBREAKABLE, 
                            Blocks.BARRIER,
                            Blocks.BEDROCK,
                            Blocks.COMMAND_BLOCK,
                            Blocks.END_PORTAL,
                            Blocks.END_PORTAL_FRAME,
                            Blocks.AIR,
                            Blocks.REINFORCED_DEEPSLATE)
                    .addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL.location());

            addTo(VABlockTags.CLIMBING_ROPES, 
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR,
                    VABlocks.CLIMBING_ROPE
            );

            addTo(VABlockTags.CRYSTALS, 
                    VABlocks.ROCK_SALT_CRYSTAL
            );

            addTo(VABlockTags.HEDGES, 
                    VABlocks.OAK_HEDGE,
                    VABlocks.SPRUCE_HEDGE,
                    VABlocks.BIRCH_HEDGE,
                    VABlocks.JUNGLE_HEDGE,
                    VABlocks.ACACIA_HEDGE,
                    VABlocks.DARK_OAK_HEDGE,
                    VABlocks.PALE_OAK_HEDGE,
                    VABlocks.MANGROVE_HEDGE,
                    VABlocks.CHERRY_HEDGE,
                    VABlocks.AZALEA_HEDGE,
                    VABlocks.FLOWERING_AZALEA_HEDGE,
                    VABlocks.SOULBLOOM_HEDGE,
                    VABlocks.WITHERED_HEDGE
            );

            addTo(VABlockTags.LUMWASP_NEST_REPLACEABLE, 
                            VABlocks.SILK_BLOCK,
                            VABlocks.WEBBED_SILK,
                            VABlocks.ACID_BLOCK)
                    .addOptionalTag(BlockTags.BASE_STONE_OVERWORLD.location());

            addTo(VABlockTags.SILKBULBS, 
                    VABlocks.SILKBULB,
                    VABlocks.WHITE_SILKBULB,
                    VABlocks.LIGHT_GRAY_SILKBULB,
                    VABlocks.GRAY_SILKBULB,
                    VABlocks.BLACK_SILKBULB,
                    VABlocks.BROWN_SILKBULB,
                    VABlocks.RED_SILKBULB,
                    VABlocks.ORANGE_SILKBULB,
                    VABlocks.YELLOW_SILKBULB,
                    VABlocks.LIME_SILKBULB,
                    VABlocks.GREEN_SILKBULB,
                    VABlocks.CYAN_SILKBULB,
                    VABlocks.LIGHT_BLUE_SILKBULB,
                    VABlocks.BLUE_SILKBULB,
                    VABlocks.PURPLE_SILKBULB,
                    VABlocks.MAGENTA_SILKBULB,
                    VABlocks.PINK_SILKBULB
            );

            addTo(VABlockTags.SPOTLIGHT_PERMEABLE, 
                            Blocks.GLASS,
                            Blocks.TINTED_GLASS,
                            Blocks.WHITE_STAINED_GLASS,
                            Blocks.LIGHT_GRAY_STAINED_GLASS,
                            Blocks.GRAY_STAINED_GLASS,
                            Blocks.BLACK_STAINED_GLASS,
                            VABlocks.TAN_STAINED_GLASS,
                            Blocks.BROWN_STAINED_GLASS,
                            VABlocks.MAROON_STAINED_GLASS,
                            Blocks.RED_STAINED_GLASS,
                            VABlocks.SINOPIA_STAINED_GLASS,
                            Blocks.ORANGE_STAINED_GLASS,
                            Blocks.YELLOW_STAINED_GLASS,
                            VABlocks.CHARTREUSE_STAINED_GLASS,
                            Blocks.LIME_STAINED_GLASS,
                            Blocks.GREEN_STAINED_GLASS,
                            VABlocks.VIRIDIAN_STAINED_GLASS,
                            Blocks.CYAN_STAINED_GLASS,
                            Blocks.LIGHT_BLUE_STAINED_GLASS,
                            Blocks.BLUE_STAINED_GLASS,
                            VABlocks.INDIGO_STAINED_GLASS,
                            Blocks.PURPLE_STAINED_GLASS,
                            VABlocks.PLUM_STAINED_GLASS,
                            Blocks.MAGENTA_STAINED_GLASS,
                            Blocks.PINK_STAINED_GLASS,
                            VABlocks.LILAC_STAINED_GLASS,
                            Blocks.GLASS_PANE,
                            Blocks.WHITE_STAINED_GLASS_PANE,
                            Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
                            Blocks.GRAY_STAINED_GLASS_PANE,
                            Blocks.BLACK_STAINED_GLASS_PANE,
                            VABlocks.TAN_STAINED_GLASS_PANE,
                            Blocks.BROWN_STAINED_GLASS_PANE,
                            VABlocks.MAROON_STAINED_GLASS_PANE,
                            Blocks.RED_STAINED_GLASS_PANE,
                            VABlocks.SINOPIA_STAINED_GLASS_PANE,
                            Blocks.ORANGE_STAINED_GLASS_PANE,
                            Blocks.YELLOW_STAINED_GLASS_PANE,
                            VABlocks.CHARTREUSE_STAINED_GLASS_PANE,
                            Blocks.LIME_STAINED_GLASS_PANE,
                            Blocks.GREEN_STAINED_GLASS_PANE,
                            VABlocks.VIRIDIAN_STAINED_GLASS_PANE,
                            Blocks.CYAN_STAINED_GLASS_PANE,
                            Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
                            Blocks.BLUE_STAINED_GLASS_PANE,
                            VABlocks.INDIGO_STAINED_GLASS_PANE,
                            Blocks.PURPLE_STAINED_GLASS_PANE,
                            VABlocks.PLUM_STAINED_GLASS_PANE,
                            Blocks.MAGENTA_STAINED_GLASS_PANE,
                            Blocks.PINK_STAINED_GLASS_PANE,
                            VABlocks.LILAC_STAINED_GLASS_PANE
                    )
                    .addOptionalTag(BlockTags.LEAVES.location())
                    .addOptionalTag(VABlockTags.HEDGES.location())
                    .addOptionalTag(BlockTags.REPLACEABLE.location());

            addTo(VABlockTags.SPECTRE_SPAWNABLE_ON, 
                    Blocks.GRASS_BLOCK,
                    Blocks.PODZOL,
                    Blocks.MYCELIUM,
                    VABlocks.SPECTRAL_SAND
            ).addOptionalTag(BlockTags.NYLIUM.location());

            addTo(VABlockTags.HALBERD_SWING_BREAKABLES,
                    Blocks.SHORT_GRASS,
                    Blocks.TALL_GRASS,
                    Blocks.FERN,
                    Blocks.LARGE_FERN,
                    Blocks.SHORT_DRY_GRASS,
                    Blocks.TALL_DRY_GRASS,
                    Blocks.BUSH,
                    Blocks.FIREFLY_BUSH,
                    Blocks.DEAD_BUSH,
                    Blocks.SWEET_BERRY_BUSH,
                    Blocks.CACTUS_FLOWER,
                    Blocks.SUGAR_CANE,
                    VABlocks.FRAYED_SILK,
                    Blocks.NETHER_SPROUTS,
                    Blocks.CRIMSON_ROOTS,
                    Blocks.WARPED_ROOTS,
                    VABlocks.NECROTIC_ROOTS,
                    Blocks.BROWN_MUSHROOM,
                    Blocks.RED_MUSHROOM,
                    VABlocks.GREENCAP_MUSHROOM,
                    VABlocks.TALL_GREENCAP_MUSHROOMS,
                    Blocks.CRIMSON_FUNGUS,
                    Blocks.WARPED_FUNGUS,
                    Blocks.VINE,
                    Blocks.CAVE_VINES,
                    Blocks.CAVE_VINES_PLANT,
                    Blocks.WEEPING_VINES,
                    Blocks.WEEPING_VINES_PLANT,
                    Blocks.TWISTING_VINES,
                    Blocks.TWISTING_VINES_PLANT,
                    Blocks.PALE_HANGING_MOSS,
                    VABlocks.GLOWING_SILK,
                    Blocks.SEAGRASS,
                    Blocks.TALL_SEAGRASS,
                    Blocks.KELP,
                    Blocks.KELP_PLANT,
                    Blocks.SMALL_DRIPLEAF,
                    Blocks.LILAC,
                    Blocks.ROSE_BUSH,
                    Blocks.PEONY,
                    Blocks.PITCHER_PLANT,
                    Blocks.DECORATED_POT,
                    Blocks.COBWEB,
                    Blocks.BAMBOO,
                    Blocks.MANGROVE_ROOTS
                    )
                    .addOptionalTag(BlockTags.SMALL_FLOWERS.location())
                    .addOptionalTag(BlockTags.LEAVES.location());

            addTo(BlockTags.MINEABLE_WITH_HOE, VABlocks.SOULBLOOM_LEAVES, VABlocks.WITHERED_LEAVES);

            configureOverworldStone(VABlocks.PORPHYRY);
            configureFamily(BlockTags.MINEABLE_WITH_PICKAXE, 0, VACollections.PORPHYRY, VACollections.POLISHED_PORPHYRY, VACollections.PORPHYRY_BRICKS);
            configureWoodenFamily(BlockTags.MINEABLE_WITH_AXE, 0, VACollections.SOULBLOOM);
            configureWoodenFamily(BlockTags.MINEABLE_WITH_AXE, 0, VACollections.WITHERED);

            addTo(BlockTags.WOODEN_FENCES, VABlocks.SOULBLOOM_FENCE);
            addTo(BlockTags.OVERWORLD_NATURAL_LOGS, VABlocks.SOULBLOOM_LOG);
            addTo(BlockTags.LOGS_THAT_BURN, VABlocks.SOULBLOOM_LOG, VABlocks.SOULBLOOM_WOOD, VABlocks.STRIPPED_SOULBLOOM_LOG, VABlocks.STRIPPED_SOULBLOOM_WOOD);
            addTo(BlockTags.CEILING_HANGING_SIGNS, VABlocks.SOULBLOOM_HANGING_SIGN);
            addTo(BlockTags.WALL_HANGING_SIGNS, VABlocks.SOULBLOOM_WALL_HANGING_SIGN);
            addTo(BlockTags.WOODEN_SHELVES, VABlocks.SOULBLOOM_SHELF);
            addTo(BlockTags.LEAVES, VABlocks.SOULBLOOM_LEAVES);
            addTo(BlockTags.SAPLINGS, VABlocks.SOULBLOOM_SAPLING);

            addTo(BlockTags.WOODEN_FENCES, VABlocks.WITHERED_FENCE);
            addTo(BlockTags.LOGS, VABlocks.WITHERED_LOG, VABlocks.WITHERED_WOOD, VABlocks.STRIPPED_WITHERED_LOG, VABlocks.STRIPPED_WITHERED_WOOD);
            addTo(BlockTags.CEILING_HANGING_SIGNS, VABlocks.WITHERED_HANGING_SIGN);
            addTo(BlockTags.WALL_HANGING_SIGNS, VABlocks.WITHERED_WALL_HANGING_SIGN);
            addTo(BlockTags.WOODEN_SHELVES, VABlocks.WITHERED_SHELF);
            addTo(BlockTags.LEAVES, VABlocks.WITHERED_LEAVES);
            addTo(BlockTags.SAPLINGS, VABlocks.WITHERED_SAPLING);
        }
    }

    static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
        }
    }

    private abstract static class Provider extends FabricTagProvider.BlockTagProvider {
        protected static final TagKey<Block> SILKBULBS = TagKey.create(Registries.BLOCK, idOf("silkbulbs"));
        protected static final TagKey<Block> HEDGES = TagKey.create(Registries.BLOCK, idOf("hedges"));
        protected static final TagKey<Block> ORES = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:ores"));

        public Provider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }
        
        protected TagBuilder addTo(TagKey<Block> tag, Block... blocks) {
            TagBuilder builder = getOrCreateRawBuilder(tag);
            for (Block block : blocks) {
                builder.addElement(BuiltInRegistries.BLOCK.getKey(block));
            }
            return builder;
        }

        protected void configureOverworldStone(Block... blocks) {
            for (Block block : blocks) {
                getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addElement(BuiltInRegistries.BLOCK.getKey(block));
                getOrCreateRawBuilder(BlockTags.BASE_STONE_OVERWORLD).addElement(BuiltInRegistries.BLOCK.getKey(block));
                getOrCreateRawBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).addElement(BuiltInRegistries.BLOCK.getKey(block));
            }
        }

        protected void configureMinable(TagKey<Block> minable, int level, Block... blocks) {
            for (Block block : blocks) {
                getOrCreateRawBuilder(minable).addElement(BuiltInRegistries.BLOCK.getKey(block));
                switch (level) {
                    case 1 -> getOrCreateRawBuilder(BlockTags.NEEDS_STONE_TOOL).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case 2 -> getOrCreateRawBuilder(BlockTags.NEEDS_IRON_TOOL).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case 3 -> getOrCreateRawBuilder(BlockTags.NEEDS_DIAMOND_TOOL).addElement(BuiltInRegistries.BLOCK.getKey(block));
                }
            }
        }

        protected void configureFamily(TagKey<Block> minable, int level, BlockFamily... families) {
            for (BlockFamily family : families) {
                configureMinable(minable, level, family.getBaseBlock());
                configureMinable(minable, level, family.getVariants().values().toArray(new Block[]{}));
                configureFamily(family);
            }
        }

        protected void configureWoodenFamily(TagKey<Block> minable, int level, BlockFamily... families) {
            for (BlockFamily family : families) {
                configureMinable(minable, level, family.getBaseBlock());
                configureMinable(minable, level, family.getVariants().values().toArray(new Block[]{}));
                configureWoodenFamily(family);
            }
        }

        protected void configureFamily(BlockFamily family) {
            family.getVariants().forEach((variant, block) -> {
                switch (variant) {
                    case STAIRS -> getOrCreateRawBuilder(BlockTags.STAIRS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case SLAB -> getOrCreateRawBuilder(BlockTags.SLABS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case WALL -> getOrCreateRawBuilder(BlockTags.WALLS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case FENCE, CUSTOM_FENCE -> getOrCreateRawBuilder(BlockTags.FENCES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case FENCE_GATE, CUSTOM_FENCE_GATE -> getOrCreateRawBuilder(BlockTags.FENCE_GATES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case DOOR -> getOrCreateRawBuilder(BlockTags.DOORS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case TRAPDOOR -> getOrCreateRawBuilder(BlockTags.TRAPDOORS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case BUTTON -> getOrCreateRawBuilder(BlockTags.BUTTONS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case PRESSURE_PLATE -> getOrCreateRawBuilder(BlockTags.PRESSURE_PLATES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                }
            });
        }
        
        @SafeVarargs
        protected final void configureFamily(BlockFamily family, TagKey<Block>... blockTags) {
            for (TagKey<Block> tag : blockTags) {
                TagBuilder builder = getOrCreateRawBuilder(tag);
                builder.addElement(BuiltInRegistries.BLOCK.getKey(family.getBaseBlock()));
                family.getVariants().forEach((variant, block) -> builder.addElement(BuiltInRegistries.BLOCK.getKey(block)));
            }
        }

        protected void configureWoodenFamily(BlockFamily family) {
            family.getVariants().forEach((variant, block) -> {
                switch (variant) {
                    case STAIRS -> getOrCreateRawBuilder(BlockTags.WOODEN_STAIRS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case SLAB -> getOrCreateRawBuilder(BlockTags.WOODEN_SLABS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case WALL -> getOrCreateRawBuilder(BlockTags.WALLS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case FENCE -> getOrCreateRawBuilder(BlockTags.WOODEN_FENCES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case FENCE_GATE -> getOrCreateRawBuilder(BlockTags.FENCE_GATES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case DOOR -> getOrCreateRawBuilder(BlockTags.WOODEN_DOORS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case TRAPDOOR -> getOrCreateRawBuilder(BlockTags.WOODEN_TRAPDOORS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case BUTTON -> getOrCreateRawBuilder(BlockTags.WOODEN_BUTTONS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case PRESSURE_PLATE -> getOrCreateRawBuilder(BlockTags.WOODEN_PRESSURE_PLATES).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case SIGN -> getOrCreateRawBuilder(BlockTags.STANDING_SIGNS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                    case WALL_SIGN -> getOrCreateRawBuilder(BlockTags.WALL_SIGNS).addElement(BuiltInRegistries.BLOCK.getKey(block));
                }
            });
        }

        protected void configureColorfulBlockSet(ColorfulBlockSet s) {
            s.ifWool(block -> getOrCreateRawBuilder(BlockTags.WOOL).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifCarpet(block -> getOrCreateRawBuilder(BlockTags.WOOL_CARPETS).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifTerracotta(block -> {
                getOrCreateRawBuilder(BlockTags.TERRACOTTA).addElement(BuiltInRegistries.BLOCK.getKey(block));
                configureMinable(BlockTags.MINEABLE_WITH_PICKAXE, 0, block);
            });
            s.ifGlazedTerracotta(block -> configureMinable(BlockTags.MINEABLE_WITH_PICKAXE, 0, block));
            s.ifConcrete(block -> configureMinable(BlockTags.MINEABLE_WITH_PICKAXE, 0, block));
            s.ifConcretePowder(block -> {
                getOrCreateRawBuilder(BlockTags.CONCRETE_POWDER).addElement(BuiltInRegistries.BLOCK.getKey(block));
                configureMinable(BlockTags.MINEABLE_WITH_SHOVEL, 0, block);
            });
            s.ifStainedGlass(block -> getOrCreateRawBuilder(BlockTags.IMPERMEABLE).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifCandle(block -> getOrCreateRawBuilder(BlockTags.CANDLES).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifCandleCake(block -> getOrCreateRawBuilder(BlockTags.CANDLE_CAKES).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifSilkbulb(block -> getOrCreateRawBuilder(VABlockTags.SILKBULBS).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifBed(block -> getOrCreateRawBuilder(BlockTags.BEDS).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifShulkerBox(block -> getOrCreateRawBuilder(BlockTags.SHULKER_BOXES).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifBanner(block -> getOrCreateRawBuilder(BlockTags.BANNERS).addElement(BuiltInRegistries.BLOCK.getKey(block)));
            s.ifWallBanner(block -> getOrCreateRawBuilder(BlockTags.BANNERS).addElement(BuiltInRegistries.BLOCK.getKey(block)));
        }
    }
}
