package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VACollections;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static com.github.suninvr.virtualadditions.VirtualAdditions.idOf;

@SuppressWarnings("SameParameterValue")
public final class VABlockTagProvider {
    public static final VABlockTagProvider INSTANCE = new VABlockTagProvider();

    public FabricDataGenerator.Pack.RegistryDependentFactory<?> base() {
        return BaseProvider::new;
    }

    public FabricDataGenerator.Pack.RegistryDependentFactory<?> preview() {
        return PreviewProvider::new;
    }

    static class BaseProvider extends Provider {

        public BaseProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).addOptionalTag(HEDGES);
            getOrCreateTagBuilder(BlockTags.FLOWERS).add(VABlocks.CHERRY_HEDGE, VABlocks.FLOWERING_AZALEA_HEDGE);
            getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(VABlocks.STEEL_BLOCK, VABlocks.IOLITE_BLOCK);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(VABlocks.CLIMBING_ROPE, VABlocks.CLIMBING_ROPE_ANCHOR);
            getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(VABlocks.WEBBED_SILK);
            getOrCreateTagBuilder(BlockTags.CROPS).add(VABlocks.CORN_CROP, VABlocks.COTTON);
            getOrCreateTagBuilder(BlockTags.DEEPSLATE_ORE_REPLACEABLES).add(VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);
            getOrCreateTagBuilder(BlockTags.DOORS).add(VABlocks.STEEL_DOOR);
            getOrCreateTagBuilder(BlockTags.FENCES).add(VABlocks.STEEL_FENCE);
            getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(VABlocks.CORN_CROP, VABlocks.COTTON);
            getOrCreateTagBuilder(BlockTags.REPLACEABLE).add(VABlocks.ACID, VABlocks.FRAYED_SILK);
            getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE).add(VABlocks.SILK_BLOCK, VABlocks.WEBBED_SILK);
            getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(VABlocks.STEEL_TRAPDOOR);
            getOrCreateTagBuilder(BlockTags.REPLACEABLE).add(VABlocks.SPOTLIGHT_LIGHT);

            configureColorfulBlockSet(VACollections.CHARTREUSE);
            configureColorfulBlockSet(VACollections.MAROON);
            configureColorfulBlockSet(VACollections.INDIGO);
            configureColorfulBlockSet(VACollections.PLUM);
            configureColorfulBlockSet(VACollections.VIRIDIAN);
            configureColorfulBlockSet(VACollections.TAN);
            configureColorfulBlockSet(VACollections.SINOPIA);
            configureColorfulBlockSet(VACollections.LILAC);

            getOrCreateTagBuilder(ORES).add(VABlocks.IOLITE_ORE);

            getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
                            VABlocks.SILK_BLOCK,
                            VABlocks.WEBBED_SILK,
                            VABlocks.LUMWASP_NEST)
                    .addOptionalTag(SILKBULBS)
                    .addOptionalTag(HEDGES);

            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(VABlocks.CLIMBING_ROPE_ANCHOR)
                    .add(VABlocks.SPOTLIGHT)
                    .add(VABlocks.RED_GLIMMER_CRYSTAL)
                    .add(VABlocks.GREEN_GLIMMER_CRYSTAL)
                    .add(VABlocks.BLUE_GLIMMER_CRYSTAL);

            configureMinable(BlockTags.PICKAXE_MINEABLE, 1,
                    VABlocks.STEEL_BLOCK,
                    VABlocks.RAW_STEEL_BLOCK,
                    VABlocks.STEEL_TRAPDOOR,
                    VABlocks.STEEL_DOOR,
                    VABlocks.WARP_ANCHOR,
                    VABlocks.WARP_TETHER,
                    VABlocks.ENTANGLEMENT_DRIVE,
                    VABlocks.REDSTONE_BRIDGE
            );

            configureMinable(BlockTags.PICKAXE_MINEABLE, 3,
                    VABlocks.IOLITE_ORE,
                    VABlocks.IOLITE_BLOCK
            );

            configureMinable(BlockTags.AXE_MINEABLE, 0,
                    VABlocks.COLORING_STATION
                    );

            configureOverworldStone(VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);
            configureFamily(BlockTags.PICKAXE_MINEABLE, 1, VACollections.CUT_STEEL);
            configureFamily(BlockTags.PICKAXE_MINEABLE, 0,
                    VACollections.CUT_STEEL,
                    VACollections.COBBLED_HORNFELS,
                    VACollections.COBBLED_BLUESCHIST,
                    VACollections.COBBLED_SYENITE,
                    VACollections.POLISHED_HORNFELS,
                    VACollections.POLISHED_BLUESCHIST,
                    VACollections.POLISHED_SYENITE,
                    VACollections.HORNFELS_TILES,
                    VACollections.BLUESCHIST_BRICKS,
                    VACollections.SYENITE_BRICKS
            );

            getOrCreateTagBuilder(VABlockTags.ACID_UNBREAKABLE).add(
                            Blocks.BARRIER,
                            Blocks.BEDROCK,
                            Blocks.COMMAND_BLOCK,
                            Blocks.END_PORTAL,
                            Blocks.END_PORTAL_FRAME,
                            Blocks.AIR,
                            Blocks.REINFORCED_DEEPSLATE)
                    .addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL);

            getOrCreateTagBuilder(VABlockTags.CLIMBING_ROPES).add(
                    VABlocks.CLIMBING_ROPE_ANCHOR,
                    VABlocks.CLIMBING_ROPE
            );

            getOrCreateTagBuilder(VABlockTags.HEDGES).add(
                    VABlocks.OAK_HEDGE,
                    VABlocks.SPRUCE_HEDGE,
                    VABlocks.BIRCH_HEDGE,
                    VABlocks.JUNGLE_HEDGE,
                    VABlocks.ACACIA_HEDGE,
                    VABlocks.DARK_OAK_HEDGE,
                    VABlocks.MANGROVE_HEDGE,
                    VABlocks.CHERRY_HEDGE,
                    VABlocks.AZALEA_HEDGE,
                    VABlocks.FLOWERING_AZALEA_HEDGE
            );

            getOrCreateTagBuilder(VABlockTags.LUMWASP_NEST_REPLACEABLE).add(
                            VABlocks.SILK_BLOCK,
                            VABlocks.WEBBED_SILK,
                            VABlocks.ACID_BLOCK)
                    .addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

            getOrCreateTagBuilder(VABlockTags.SCULK_GILD_EFFECTIVE).add(
                            Blocks.COBWEB,
                            Blocks.MAGMA_BLOCK,
                            Blocks.AMETHYST_BLOCK,
                            Blocks.SNOW_BLOCK,
                            Blocks.COBBLESTONE,
                            Blocks.MOSSY_COBBLESTONE,
                            Blocks.COBBLED_DEEPSLATE,
                            VABlocks.COBBLED_HORNFELS,
                            VABlocks.COBBLED_BLUESCHIST,
                            VABlocks.COBBLED_SYENITE,
                            Blocks.INFESTED_STONE,
                            Blocks.INFESTED_COBBLESTONE,
                            Blocks.INFESTED_DEEPSLATE)
                    .addOptionalTag(VABlockTags.SCULK_GILD_STRONGLY_EFFECTIVE)
                    .addOptionalTag(BlockTags.SCULK_REPLACEABLE);

            getOrCreateTagBuilder(VABlockTags.SCULK_GILD_STRONGLY_EFFECTIVE).add(
                            Blocks.ICE,
                            Blocks.PACKED_ICE,
                            Blocks.BLUE_ICE,
                            Blocks.MANGROVE_ROOTS,
                            Blocks.BROWN_MUSHROOM_BLOCK,
                            Blocks.RED_MUSHROOM_BLOCK,
                            Blocks.MUSHROOM_STEM,
                            Blocks.BONE_BLOCK,
                            Blocks.DEAD_TUBE_CORAL_BLOCK,
                            Blocks.DEAD_BRAIN_CORAL_BLOCK,
                            Blocks.DEAD_BUBBLE_CORAL_BLOCK,
                            Blocks.DEAD_FIRE_CORAL_BLOCK,
                            Blocks.DEAD_HORN_CORAL_BLOCK
                    )
                    .addOptionalTag(ORES)
                    .addOptionalTag(BlockTags.LOGS)
                    .addOptionalTag(BlockTags.LEAVES)
                    .addOptionalTag(BlockTags.WART_BLOCKS)
                    .addOptionalTag(BlockTags.CORAL_BLOCKS);

            getOrCreateTagBuilder(VABlockTags.SILKBULBS).add(
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

            getOrCreateTagBuilder(VABlockTags.SPOTLIGHT_PERMEABLE).add(
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
                    .addOptionalTag(BlockTags.LEAVES)
                    .addOptionalTag(VABlockTags.HEDGES)
                    .addOptionalTag(BlockTags.REPLACEABLE);
        }
    }

    static class PreviewProvider extends Provider {
        public PreviewProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(VABlocks.AEROBLOOM_FENCE);
            getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).add(VABlocks.AEROBLOOM_LOG);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(VABlocks.AEROBLOOM_LOG).add(VABlocks.AEROBLOOM_WOOD).add(VABlocks.STRIPPED_AEROBLOOM_LOG).add(VABlocks.STRIPPED_AEROBLOOM_WOOD);
            getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(VABlocks.AEROBLOOM_HANGING_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(VABlocks.AEROBLOOM_WALL_HANGING_SIGN);
            getOrCreateTagBuilder(BlockTags.LEAVES).add(VABlocks.AEROBLOOM_LEAVES);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(VABlocks.AEROBLOOM_SAPLING);
            getOrCreateTagBuilder(BlockTags.DIRT).add(VABlocks.GRASSY_FLOATROCK, VABlocks.SPRINGSOIL);
            getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS).add(VABlocks.FLOATROCK_GOLD_ORE);
            getOrCreateTagBuilder(BlockTags.COAL_ORES).add(VABlocks.FLOATROCK_COAL_ORE);
            getOrCreateTagBuilder(BlockTags.COPPER_ORES).add(VABlocks.FLOATROCK_COPPER_ORE);
            getOrCreateTagBuilder(BlockTags.IRON_ORES).add(VABlocks.FLOATROCK_IRON_ORE);
            getOrCreateTagBuilder(BlockTags.GOLD_ORES).add(VABlocks.FLOATROCK_GOLD_ORE);
            getOrCreateTagBuilder(BlockTags.REDSTONE_ORES).add(VABlocks.FLOATROCK_REDSTONE_ORE);
            getOrCreateTagBuilder(BlockTags.EMERALD_ORES).add(VABlocks.FLOATROCK_EMERALD_ORE);
            getOrCreateTagBuilder(BlockTags.LAPIS_ORES).add(VABlocks.FLOATROCK_LAPIS_ORE);
            getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(VABlocks.FLOATROCK_DIAMOND_ORE);

            getOrCreateTagBuilder(VABlockTags.FLOATROCK_ORE_REPLACEABLES).add(VABlocks.FLOATROCK);
            getOrCreateTagBuilder(VABlockTags.SKYLANDS_CARVER_REPLACEABLES).add(VABlocks.FLOATROCK).add(VABlocks.GRASSY_FLOATROCK);
            getOrCreateTagBuilder(VABlockTags.NO_FOLIAGE_WORLDGEN).add(VABlocks.SPRINGSOIL);

            getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                    VABlocks.SPRINGSOIL
            );

            getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(VABlocks.AEROBLOOM_LEAVES);

            configureMinable(BlockTags.PICKAXE_MINEABLE, 0, VABlocks.GRASSY_FLOATROCK, VABlocks.FLOATROCK_COAL_ORE);
            configureMinable(BlockTags.PICKAXE_MINEABLE, 1, VABlocks.FLOATROCK_IRON_ORE, VABlocks.FLOATROCK_COPPER_ORE, VABlocks.FLOATROCK_LAPIS_ORE);
            configureMinable(BlockTags.PICKAXE_MINEABLE, 2, VABlocks.FLOATROCK_DIAMOND_ORE, VABlocks.FLOATROCK_REDSTONE_ORE, VABlocks.FLOATROCK_EMERALD_ORE, VABlocks.FLOATROCK_GOLD_ORE);

            configureOverworldStone(VABlocks.FLOATROCK);
            configureFamily(BlockTags.PICKAXE_MINEABLE, 0, VACollections.FLOATROCK, VACollections.POLISHED_FLOATROCK, VACollections.FLOATROCK_BRICKS);
            configureWoodenFamily(BlockTags.AXE_MINEABLE, 0, VACollections.AEROBLOOM);

            getOrCreateTagBuilder(VABlockTags.HEDGES).add(
                    VABlocks.AEROBLOOM_HEDGE
            );

            getOrCreateTagBuilder(VABlockTags.CRYSTALS).add(
                    VABlocks.RED_GLIMMER_CRYSTAL,
                    VABlocks.GREEN_GLIMMER_CRYSTAL,
                    VABlocks.BLUE_GLIMMER_CRYSTAL
            );
        }
    }

    private abstract static class Provider extends FabricTagProvider.BlockTagProvider {
        protected static final TagKey<Block> SILKBULBS = TagKey.of(RegistryKeys.BLOCK, idOf("silkbulbs"));
        protected static final TagKey<Block> HEDGES = TagKey.of(RegistryKeys.BLOCK, idOf("hedges"));
        protected static final TagKey<Block> ORES = TagKey.of(RegistryKeys.BLOCK, new Identifier("c:ores"));

        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        protected void configureOverworldStone(Block... blocks) {
            for (Block block : blocks) {
                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
                getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(block);
                getOrCreateTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(block);
            }
        }

        protected void configureMinable(TagKey<Block> minable, int level, Block... blocks) {
            for (Block block : blocks) {
                getOrCreateTagBuilder(minable).add(block);
                switch (level) {
                    case 1 -> getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
                    case 2 -> getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
                    case 3 -> getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
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
                    case STAIRS -> getOrCreateTagBuilder(BlockTags.STAIRS).add(block);
                    case SLAB -> getOrCreateTagBuilder(BlockTags.SLABS).add(block);
                    case WALL -> getOrCreateTagBuilder(BlockTags.WALLS).add(block);
                    case FENCE -> getOrCreateTagBuilder(BlockTags.FENCES).add(block);
                    case FENCE_GATE -> getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(block);
                    case DOOR -> getOrCreateTagBuilder(BlockTags.DOORS).add(block);
                    case TRAPDOOR -> getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(block);
                    case BUTTON -> getOrCreateTagBuilder(BlockTags.BUTTONS).add(block);
                    case PRESSURE_PLATE -> getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(block);
                }
            });
        }

        protected void configureWoodenFamily(BlockFamily family) {
            family.getVariants().forEach((variant, block) -> {
                switch (variant) {
                    case STAIRS -> getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(block);
                    case SLAB -> getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(block);
                    case WALL -> getOrCreateTagBuilder(BlockTags.WALLS).add(block);
                    case FENCE -> getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(block);
                    case FENCE_GATE -> getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(block);
                    case DOOR -> getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(block);
                    case TRAPDOOR -> getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(block);
                    case BUTTON -> getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(block);
                    case PRESSURE_PLATE -> getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(block);
                    case SIGN -> getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(block);
                    case WALL_SIGN -> getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(block);
                }
            });
        }

        protected void configureColorfulBlockSet(ColorfulBlockSet s) {
            s.ifWool(block -> getOrCreateTagBuilder(BlockTags.WOOL).add(block));
            s.ifCarpet(block -> getOrCreateTagBuilder(BlockTags.WOOL_CARPETS).add(block));
            s.ifTerracotta(block -> {
                getOrCreateTagBuilder(BlockTags.TERRACOTTA).add(block);
                configureMinable(BlockTags.PICKAXE_MINEABLE, 0, block);
            });
            s.ifConcrete(block -> configureMinable(BlockTags.PICKAXE_MINEABLE, 0, block));
            s.ifConcretePowder(block -> {
                getOrCreateTagBuilder(BlockTags.CONCRETE_POWDER).add(block);
                configureMinable(BlockTags.SHOVEL_MINEABLE, 0, block);
            });
            s.ifStainedGlass(block -> getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(block));
            s.ifCandle(block -> getOrCreateTagBuilder(BlockTags.CANDLES).add(block));
            s.ifCandleCake(block -> getOrCreateTagBuilder(BlockTags.CANDLE_CAKES).add(block));
            s.ifSilkbulb(block -> getOrCreateTagBuilder(VABlockTags.SILKBULBS).add(block));
            s.ifBed(block -> getOrCreateTagBuilder(BlockTags.BEDS).add(block));
            s.ifShulkerBox(block -> getOrCreateTagBuilder(BlockTags.SHULKER_BOXES).add(block));
        }
    }
}
