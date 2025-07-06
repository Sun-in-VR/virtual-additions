package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

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

        public BaseProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {


            addTo(BlockTags.SWORD_EFFICIENT).addOptionalTag(HEDGES.id());
            addTo(BlockTags.FLOWERS, VABlocks.CHERRY_HEDGE, VABlocks.FLOWERING_AZALEA_HEDGE, VABlocks.BLUE_PETALS, VABlocks.SPRING_LOTUS, VABlocks.SMALL_SPRING_LOTUS, VABlocks.SOUL_SPROUT);
            addTo(BlockTags.BEE_ATTRACTIVE, VABlocks.CHERRY_HEDGE, VABlocks.FLOWERING_AZALEA_HEDGE, VABlocks.BLUE_PETALS, VABlocks.SPRING_LOTUS, VABlocks.SMALL_SPRING_LOTUS, VABlocks.SOUL_SPROUT);
            addTo(BlockTags.FROG_PREFER_JUMP_TO, VABlocks.SPRING_LOTUS);
            addTo(BlockTags.INSIDE_STEP_SOUND_BLOCKS, VABlocks.BLUE_PETALS);
            addTo(BlockTags.BEACON_BASE_BLOCKS, VABlocks.STEEL_BLOCK, VABlocks.EXPOSED_STEEL_BLOCK, VABlocks.WEATHERED_STEEL_BLOCK, VABlocks.OXIDIZED_STEEL_BLOCK, VABlocks.WAXED_STEEL_BLOCK, VABlocks.WAXED_EXPOSED_STEEL_BLOCK, VABlocks.WAXED_WEATHERED_STEEL_BLOCK, VABlocks.WAXED_OXIDIZED_STEEL_BLOCK, VABlocks.IOLITE_BLOCK);
            addTo(BlockTags.CLIMBABLE, VABlocks.CLIMBING_ROPE, VABlocks.CLIMBING_ROPE_ANCHOR, VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR);
            addTo(BlockTags.COMBINATION_STEP_SOUND_BLOCKS, VABlocks.NECROTIC_ROOTS);
            addTo(BlockTags.CROPS, VABlocks.CORN_CROP, VABlocks.COTTON, VABlocks.TOMATO, VABlocks.CABBAGE, VABlocks.WISBERRY);
            addTo(BlockTags.DEEPSLATE_ORE_REPLACEABLES, VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);
            addTo(BlockTags.DOORS, VABlocks.STEEL_DOOR, VABlocks.EXPOSED_STEEL_DOOR, VABlocks.WEATHERED_STEEL_DOOR, VABlocks.OXIDIZED_STEEL_DOOR, VABlocks.WAXED_STEEL_DOOR, VABlocks.WAXED_EXPOSED_STEEL_DOOR, VABlocks.WAXED_WEATHERED_STEEL_DOOR);
            addTo(BlockTags.TRAPDOORS, VABlocks.STEEL_TRAPDOOR, VABlocks.EXPOSED_STEEL_TRAPDOOR, VABlocks.WEATHERED_STEEL_TRAPDOOR, VABlocks.OXIDIZED_STEEL_TRAPDOOR, VABlocks.WAXED_STEEL_TRAPDOOR, VABlocks.WAXED_EXPOSED_STEEL_TRAPDOOR, VABlocks.WAXED_WEATHERED_STEEL_TRAPDOOR);
            addTo(BlockTags.FENCES, VABlocks.STEEL_FENCE);
            addTo(BlockTags.MAINTAINS_FARMLAND, VABlocks.CORN_CROP, VABlocks.COTTON, VABlocks.TOMATO, VABlocks.CABBAGE, VABlocks.WISBERRY);
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

            addTo(BlockTags.HOE_MINEABLE,
                            VABlocks.SILK_BLOCK,
                            VABlocks.WEBBED_SILK,
                            VABlocks.LUMWASP_NEST,
                            VABlocks.SPRING_LOTUS
                    )
                    .addOptionalTag(SILKBULBS.id())
                    .addOptionalTag(HEDGES.id());

            addTo(BlockTags.PICKAXE_MINEABLE,
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

            configureMinable(BlockTags.PICKAXE_MINEABLE, 1,
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

            configureMinable(BlockTags.PICKAXE_MINEABLE, 3,
                    VABlocks.IOLITE_ORE,
                    VABlocks.IOLITE_BLOCK
            );

            configureMinable(BlockTags.AXE_MINEABLE, 0,
                    VABlocks.COLORING_STATION
                    );

            configureMinable(BlockTags.SHOVEL_MINEABLE, 0,
                    VABlocks.SPECTRAL_SAND
                    );

            configureOverworldStone(VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);



            configureFamily(BlockTags.PICKAXE_MINEABLE, 1,
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
            
            configureFamily(BlockTags.PICKAXE_MINEABLE, 0,
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

            addTo(VABlockTags.INCORRECT_FOR_STEEL_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL.id());

            addTo(VABlockTags.ACID_UNBREAKABLE, 
                            Blocks.BARRIER,
                            Blocks.BEDROCK,
                            Blocks.COMMAND_BLOCK,
                            Blocks.END_PORTAL,
                            Blocks.END_PORTAL_FRAME,
                            Blocks.AIR,
                            Blocks.REINFORCED_DEEPSLATE)
                    .addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL.id());

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
                    .addOptionalTag(BlockTags.BASE_STONE_OVERWORLD.id());

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
                    .addOptionalTag(BlockTags.LEAVES.id())
                    .addOptionalTag(VABlockTags.HEDGES.id())
                    .addOptionalTag(BlockTags.REPLACEABLE.id());

            addTo(VABlockTags.SPECTRE_SPAWNABLE_ON, 
                    Blocks.GRASS_BLOCK,
                    Blocks.PODZOL,
                    Blocks.MYCELIUM,
                    VABlocks.SPECTRAL_SAND
            ).addOptionalTag(BlockTags.NYLIUM.id());

            addTo(BlockTags.HOE_MINEABLE, VABlocks.SOULBLOOM_LEAVES, VABlocks.WITHERED_LEAVES);

            configureOverworldStone(VABlocks.PORPHYRY);
            configureFamily(BlockTags.PICKAXE_MINEABLE, 0, VACollections.PORPHYRY, VACollections.POLISHED_PORPHYRY, VACollections.PORPHYRY_BRICKS);
            configureWoodenFamily(BlockTags.AXE_MINEABLE, 0, VACollections.SOULBLOOM);
            configureWoodenFamily(BlockTags.AXE_MINEABLE, 0, VACollections.WITHERED);

            addTo(BlockTags.WOODEN_FENCES, VABlocks.SOULBLOOM_FENCE);
            addTo(BlockTags.OVERWORLD_NATURAL_LOGS, VABlocks.SOULBLOOM_LOG);
            addTo(BlockTags.LOGS_THAT_BURN, VABlocks.SOULBLOOM_LOG, VABlocks.SOULBLOOM_WOOD, VABlocks.STRIPPED_SOULBLOOM_LOG, VABlocks.STRIPPED_SOULBLOOM_WOOD);
            addTo(BlockTags.CEILING_HANGING_SIGNS, VABlocks.SOULBLOOM_HANGING_SIGN);
            addTo(BlockTags.WALL_HANGING_SIGNS, VABlocks.SOULBLOOM_WALL_HANGING_SIGN);
            addTo(BlockTags.LEAVES, VABlocks.SOULBLOOM_LEAVES);
            addTo(BlockTags.SAPLINGS, VABlocks.SOULBLOOM_SAPLING);

            addTo(BlockTags.WOODEN_FENCES, VABlocks.WITHERED_FENCE);
            addTo(BlockTags.LOGS, VABlocks.WITHERED_LOG, VABlocks.WITHERED_WOOD, VABlocks.STRIPPED_WITHERED_LOG, VABlocks.STRIPPED_WITHERED_WOOD);
            addTo(BlockTags.CEILING_HANGING_SIGNS, VABlocks.WITHERED_HANGING_SIGN);
            addTo(BlockTags.WALL_HANGING_SIGNS, VABlocks.WITHERED_WALL_HANGING_SIGN);
            addTo(BlockTags.LEAVES, VABlocks.WITHERED_LEAVES);
            addTo(BlockTags.SAPLINGS, VABlocks.WITHERED_SAPLING);
        }
    }

    static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
        }
    }

    private abstract static class Provider extends FabricTagProvider.BlockTagProvider {
        protected static final TagKey<Block> SILKBULBS = TagKey.of(RegistryKeys.BLOCK, idOf("silkbulbs"));
        protected static final TagKey<Block> HEDGES = TagKey.of(RegistryKeys.BLOCK, idOf("hedges"));
        protected static final TagKey<Block> ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c:ores"));

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }
        
        protected TagBuilder addTo(TagKey<Block> tag, Block... blocks) {
            TagBuilder builder = getTagBuilder(tag);
            for (Block block : blocks) {
                builder.add(Registries.BLOCK.getId(block));
            }
            return builder;
        }

        protected void configureOverworldStone(Block... blocks) {
            for (Block block : blocks) {
                getTagBuilder(BlockTags.PICKAXE_MINEABLE).add(Registries.BLOCK.getId(block));
                getTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(Registries.BLOCK.getId(block));
                getTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(Registries.BLOCK.getId(block));
            }
        }

        protected void configureMinable(TagKey<Block> minable, int level, Block... blocks) {
            for (Block block : blocks) {
                getTagBuilder(minable).add(Registries.BLOCK.getId(block));
                switch (level) {
                    case 1 -> getTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(Registries.BLOCK.getId(block));
                    case 2 -> getTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(Registries.BLOCK.getId(block));
                    case 3 -> getTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(Registries.BLOCK.getId(block));
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
                    case STAIRS -> getTagBuilder(BlockTags.STAIRS).add(Registries.BLOCK.getId(block));
                    case SLAB -> getTagBuilder(BlockTags.SLABS).add(Registries.BLOCK.getId(block));
                    case WALL -> getTagBuilder(BlockTags.WALLS).add(Registries.BLOCK.getId(block));
                    case FENCE, CUSTOM_FENCE -> getTagBuilder(BlockTags.FENCES).add(Registries.BLOCK.getId(block));
                    case FENCE_GATE, CUSTOM_FENCE_GATE -> getTagBuilder(BlockTags.FENCE_GATES).add(Registries.BLOCK.getId(block));
                    case DOOR -> getTagBuilder(BlockTags.DOORS).add(Registries.BLOCK.getId(block));
                    case TRAPDOOR -> getTagBuilder(BlockTags.TRAPDOORS).add(Registries.BLOCK.getId(block));
                    case BUTTON -> getTagBuilder(BlockTags.BUTTONS).add(Registries.BLOCK.getId(block));
                    case PRESSURE_PLATE -> getTagBuilder(BlockTags.PRESSURE_PLATES).add(Registries.BLOCK.getId(block));
                }
            });
        }
        
        @SafeVarargs
        protected final void configureFamily(BlockFamily family, TagKey<Block>... blockTags) {
            for (TagKey<Block> tag : blockTags) {
                TagBuilder builder = getTagBuilder(tag);
                builder.add(Registries.BLOCK.getId(family.getBaseBlock()));
                family.getVariants().forEach((variant, block) -> builder.add(Registries.BLOCK.getId(block)));
            }
        }

        protected void configureWoodenFamily(BlockFamily family) {
            family.getVariants().forEach((variant, block) -> {
                switch (variant) {
                    case STAIRS -> getTagBuilder(BlockTags.WOODEN_STAIRS).add(Registries.BLOCK.getId(block));
                    case SLAB -> getTagBuilder(BlockTags.WOODEN_SLABS).add(Registries.BLOCK.getId(block));
                    case WALL -> getTagBuilder(BlockTags.WALLS).add(Registries.BLOCK.getId(block));
                    case FENCE -> getTagBuilder(BlockTags.WOODEN_FENCES).add(Registries.BLOCK.getId(block));
                    case FENCE_GATE -> getTagBuilder(BlockTags.FENCE_GATES).add(Registries.BLOCK.getId(block));
                    case DOOR -> getTagBuilder(BlockTags.WOODEN_DOORS).add(Registries.BLOCK.getId(block));
                    case TRAPDOOR -> getTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(Registries.BLOCK.getId(block));
                    case BUTTON -> getTagBuilder(BlockTags.WOODEN_BUTTONS).add(Registries.BLOCK.getId(block));
                    case PRESSURE_PLATE -> getTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(Registries.BLOCK.getId(block));
                    case SIGN -> getTagBuilder(BlockTags.STANDING_SIGNS).add(Registries.BLOCK.getId(block));
                    case WALL_SIGN -> getTagBuilder(BlockTags.WALL_SIGNS).add(Registries.BLOCK.getId(block));
                }
            });
        }

        protected void configureColorfulBlockSet(ColorfulBlockSet s) {
            s.ifWool(block -> getTagBuilder(BlockTags.WOOL).add(Registries.BLOCK.getId(block)));
            s.ifCarpet(block -> getTagBuilder(BlockTags.WOOL_CARPETS).add(Registries.BLOCK.getId(block)));
            s.ifTerracotta(block -> {
                getTagBuilder(BlockTags.TERRACOTTA).add(Registries.BLOCK.getId(block));
                configureMinable(BlockTags.PICKAXE_MINEABLE, 0, block);
            });
            s.ifGlazedTerracotta(block -> configureMinable(BlockTags.PICKAXE_MINEABLE, 0, block));
            s.ifConcrete(block -> configureMinable(BlockTags.PICKAXE_MINEABLE, 0, block));
            s.ifConcretePowder(block -> {
                getTagBuilder(BlockTags.CONCRETE_POWDER).add(Registries.BLOCK.getId(block));
                configureMinable(BlockTags.SHOVEL_MINEABLE, 0, block);
            });
            s.ifStainedGlass(block -> getTagBuilder(BlockTags.IMPERMEABLE).add(Registries.BLOCK.getId(block)));
            s.ifCandle(block -> getTagBuilder(BlockTags.CANDLES).add(Registries.BLOCK.getId(block)));
            s.ifCandleCake(block -> getTagBuilder(BlockTags.CANDLE_CAKES).add(Registries.BLOCK.getId(block)));
            s.ifSilkbulb(block -> getTagBuilder(VABlockTags.SILKBULBS).add(Registries.BLOCK.getId(block)));
            s.ifBed(block -> getTagBuilder(BlockTags.BEDS).add(Registries.BLOCK.getId(block)));
            s.ifShulkerBox(block -> getTagBuilder(BlockTags.SHULKER_BOXES).add(Registries.BLOCK.getId(block)));
            s.ifBanner(block -> getTagBuilder(BlockTags.BANNERS).add(Registries.BLOCK.getId(block)));
            s.ifWallBanner(block -> getTagBuilder(BlockTags.BANNERS).add(Registries.BLOCK.getId(block)));
        }
    }
}
