package com.github.suninvr.virtualadditions.datagen;

import com.github.suninvr.virtualadditions.registry.VABlockFamilies;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
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
class VABlockTagProvider extends FabricTagProvider.BlockTagProvider {
    private static final TagKey<Block> SILKBULBS = TagKey.of(RegistryKeys.BLOCK, idOf("silkbulbs"));
    private static final TagKey<Block> HEDGES = TagKey.of(RegistryKeys.BLOCK, idOf("hedges"));
    private static final TagKey<Block> ORES = TagKey.of(RegistryKeys.BLOCK, new Identifier("c:ores"));

    public VABlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
                        VABlocks.SILK_BLOCK,
                        VABlocks.WEBBED_SILK,
                        VABlocks.LUMWASP_NEST)
                .addOptionalTag(SILKBULBS)
                .addOptionalTag(HEDGES);

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

        configureOverworldStone(VABlocks.HORNFELS, VABlocks.BLUESCHIST, VABlocks.SYENITE);
        configureFamily(BlockTags.PICKAXE_MINEABLE, 1, VABlockFamilies.CUT_STEEL);
        configureFamily(BlockTags.PICKAXE_MINEABLE, 0,
                VABlockFamilies.CUT_STEEL,
                VABlockFamilies.COBBLED_HORNFELS,
                VABlockFamilies.COBBLED_BLUESCHIST,
                VABlockFamilies.COBBLED_SYENITE,
                VABlockFamilies.POLISHED_HORNFELS,
                VABlockFamilies.POLISHED_BLUESCHIST,
                VABlockFamilies.POLISHED_SYENITE,
                VABlockFamilies.HORNFELS_TILES,
                VABlockFamilies.BLUESCHIST_BRICKS,
                VABlockFamilies.SYENITE_BRICKS
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
                        VABlocks.WEBBED_SILK)
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
    }

    private void configureOverworldStone(Block... blocks) {
        for (Block block : blocks) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
            getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(block);
            getOrCreateTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(block);
        }
    }

    private void configureMinable(TagKey<Block> minable, int level, Block... blocks) {
        for (Block block : blocks) {
            getOrCreateTagBuilder(minable).add(block);
            switch (level) {
                case 1 -> getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
                case 2 -> getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
                case 3 -> getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
            }
        }
    }

    private void configureFamily(TagKey<Block> minable, int level, BlockFamily... families) {
        for (BlockFamily family : families) {
            configureMinable(minable, level, family.getBaseBlock());
            configureMinable(minable, level, family.getVariants().values().toArray(new Block[]{}));
            configureFamily(family);
        }
    }

    private void configureFamily(BlockFamily family) {
        family.getVariants().forEach((variant, block) -> {
            switch (variant) {
                case STAIRS -> getOrCreateTagBuilder(BlockTags.STAIRS).add(block);
                case SLAB -> getOrCreateTagBuilder(BlockTags.SLABS).add(block);
                case WALL -> getOrCreateTagBuilder(BlockTags.WALLS).add(block);
                case FENCE -> getOrCreateTagBuilder(BlockTags.FENCES).add(block);
                case DOOR -> getOrCreateTagBuilder(BlockTags.DOORS).add(block);
                case TRAPDOOR -> getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(block);
            }
        });
    }
}
