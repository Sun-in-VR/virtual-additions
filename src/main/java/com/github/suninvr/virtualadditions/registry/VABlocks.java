package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.*;
import com.github.suninvr.virtualadditions.registry.constructors.block.CustomStairsBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class VABlocks {

    public static final BlockSoundGroup ROPE_SOUND_GROUP = new BlockSoundGroup(1.0F, 1.0F, VASoundEvents.BLOCK_ROPE_BREAK, VASoundEvents.BLOCK_ROPE_STEP, VASoundEvents.BLOCK_ROPE_PLACE, VASoundEvents.BLOCK_ROPE_HIT, VASoundEvents.BLOCK_ROPE_FALL);

    public static final Block CLIMBING_ROPE;
    public static final Block CLIMBING_ROPE_ANCHOR;
    public static final Block RAW_STEEL_BLOCK;
    public static final Block STEEL_BLOCK;
    //public static final Block WORKBENCH;
    public static final Block HORNFELS;
    public static final Block COBBLED_HORNFELS;
    public static final Block COBBLED_HORNFELS_STAIRS;
    public static final Block COBBLED_HORNFELS_SLAB;
    public static final Block COBBLED_HORNFELS_WALL;
    public static final Block POLISHED_HORNFELS;
    public static final Block POLISHED_HORNFELS_STAIRS;
    public static final Block POLISHED_HORNFELS_SLAB;
    public static final Block HORNFELS_TILES;
    public static final Block CRACKED_HORNFELS_TILES;
    public static final Block HORNFELS_TILE_STAIRS;
    public static final Block HORNFELS_TILE_SLAB;
    public static final Block BLUESCHIST;
    public static final Block COBBLED_BLUESCHIST;
    public static final Block COBBLED_BLUESCHIST_STAIRS;
    public static final Block COBBLED_BLUESCHIST_SLAB;
    public static final Block COBBLED_BLUESCHIST_WALL;
    public static final Block POLISHED_BLUESCHIST;
    public static final Block POLISHED_BLUESCHIST_STAIRS;
    public static final Block POLISHED_BLUESCHIST_SLAB;
    public static final Block BLUESCHIST_BRICKS;
    public static final Block CRACKED_BLUESCHIST_BRICKS;
    public static final Block BLUESCHIST_BRICK_STAIRS;
    public static final Block BLUESCHIST_BRICK_SLAB;
    public static final Block BLUESCHIST_BRICK_WALL;
    public static final Block SYENITE;
    public static final Block COBBLED_SYENITE;
    public static final Block COBBLED_SYENITE_STAIRS;
    public static final Block COBBLED_SYENITE_SLAB;
    public static final Block COBBLED_SYENITE_WALL;
    public static final Block POLISHED_SYENITE;
    public static final Block POLISHED_SYENITE_STAIRS;
    public static final Block POLISHED_SYENITE_SLAB;
    public static final Block SYENITE_BRICKS;
    public static final Block CRACKED_SYENITE_BRICKS;
    public static final Block SYENITE_BRICK_STAIRS;
    public static final Block SYENITE_BRICK_SLAB;
    public static final Block SYENITE_BRICK_WALL;
    public static final Block RED_GLIMMER_CRYSTAL;
    public static final Block GREEN_GLIMMER_CRYSTAL;
    public static final Block BLUE_GLIMMER_CRYSTAL;
    public static final Block SPOTLIGHT;
    public static final Block SPOTLIGHT_LIGHT;
    public static final Block COTTON;
    public static final Block OAK_HEDGE;
    public static final Block SPRUCE_HEDGE;
    public static final Block BIRCH_HEDGE;
    public static final Block JUNGLE_HEDGE;
    public static final Block ACACIA_HEDGE;
    public static final Block DARK_OAK_HEDGE;
    public static final Block MANGROVE_HEDGE;
    public static final Block AZALEA_HEDGE;
    public static final Block FLOWERING_AZALEA_HEDGE;
    public static final Block HANGING_GLOWSILK;
    public static final Block LUMWASP_NEST;
    public static final Block CARVED_STONE;
    public static final Block SILKBULB;
    public static final Block WHITE_SILKBULB;
    public static final Block LIGHT_GRAY_SILKBULB;
    public static final Block GRAY_SILKBULB;
    public static final Block BLACK_SILKBULB;
    public static final Block BROWN_SILKBULB;
    public static final Block RED_SILKBULB;
    public static final Block ORANGE_SILKBULB;
    public static final Block YELLOW_SILKBULB;
    public static final Block LIME_SILKBULB;
    public static final Block GREEN_SILKBULB;
    public static final Block CYAN_SILKBULB;
    public static final Block LIGHT_BLUE_SILKBULB;
    public static final Block BLUE_SILKBULB;
    public static final Block PURPLE_SILKBULB;
    public static final Block MAGENTA_SILKBULB;
    public static final Block PINK_SILKBULB;
    public static final Block ACID;
    public static final Block IOLITE_ORE;
    public static final Block IOLITE_BLOCK;
    public static final Block WARP_ANCHOR;
    public static final Block WARP_TETHER;
    public static final Block ENTANGLEMENT_DRIVE;


    static {
        CLIMBING_ROPE = register("climbing_rope", new ClimbingRopeBlock(FabricBlockSettings.of(Material.WOOL).sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().hardness(0.5F)));
        CLIMBING_ROPE_ANCHOR = register("climbing_rope_anchor", new ClimbingRopeAnchorBlock(FabricBlockSettings.of(Material.WOOL).sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().hardness(0.5F)));
        RAW_STEEL_BLOCK = register("raw_steel_block", new Block(FabricBlockSettings.of(Material.STONE).mapColor(MapColor.GRAY).requiresTool().strength(5.0F, 300.0F)));
        STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.of(Material.METAL).mapColor(MapColor.GRAY).sounds(BlockSoundGroup.NETHERITE).requiresTool().hardness(5.0F).resistance(300.0F)));
        HORNFELS = register("hornfels", new PillarBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
        COBBLED_HORNFELS = register("cobbled_hornfels", new Block(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_STAIRS = register("cobbled_hornfels_stairs", new CustomStairsBlock(COBBLED_HORNFELS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_SLAB = register("cobbled_hornfels_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_WALL = register("cobbled_hornfels_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        POLISHED_HORNFELS = register("polished_hornfels", new PillarBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DEEPSLATE)));
        POLISHED_HORNFELS_STAIRS = register("polished_hornfels_stairs", new CustomStairsBlock(POLISHED_HORNFELS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_STAIRS).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        POLISHED_HORNFELS_SLAB = register("polished_hornfels_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        HORNFELS_TILES = register("hornfels_tiles", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILES)));
        CRACKED_HORNFELS_TILES = register("cracked_hornfels_tiles", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILES)));
        HORNFELS_TILE_STAIRS = register("hornfels_tile_stairs", new CustomStairsBlock(HORNFELS_TILES.getDefaultState(), AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILE_STAIRS)));
        HORNFELS_TILE_SLAB = register("hornfels_tile_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILE_SLAB)));
        BLUESCHIST = register("blueschist", new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        COBBLED_BLUESCHIST = register("cobbled_blueschist", new Block(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_BLUESCHIST_STAIRS = register("cobbled_blueschist_stairs", new CustomStairsBlock(COBBLED_BLUESCHIST.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_BLUESCHIST_SLAB = register("cobbled_blueschist_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_BLUESCHIST_WALL = register("cobbled_blueschist_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        POLISHED_BLUESCHIST = register("polished_blueschist", new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        POLISHED_BLUESCHIST_STAIRS = register("polished_blueschist_stairs", new CustomStairsBlock(POLISHED_BLUESCHIST.getDefaultState(), FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_STAIRS).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        POLISHED_BLUESCHIST_SLAB = register("polished_blueschist_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        BLUESCHIST_BRICKS = register("blueschist_bricks", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        CRACKED_BLUESCHIST_BRICKS = register("cracked_blueschist_bricks", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        BLUESCHIST_BRICK_STAIRS = register("blueschist_brick_stairs", new CustomStairsBlock(BLUESCHIST_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICK_STAIRS)));
        BLUESCHIST_BRICK_SLAB = register("blueschist_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICK_SLAB)));
        BLUESCHIST_BRICK_WALL = register("blueschist_brick_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICK_WALL)));
        SYENITE = register("syenite", new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).mapColor(MapColor.BROWN)));
        COBBLED_SYENITE = register("cobbled_syenite", new Block(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE).mapColor(MapColor.BROWN)));
        COBBLED_SYENITE_STAIRS = register("cobbled_syenite_stairs", new CustomStairsBlock(COBBLED_SYENITE.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE).mapColor(MapColor.BROWN)));
        COBBLED_SYENITE_SLAB = register("cobbled_syenite_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE).mapColor(MapColor.BROWN)));
        COBBLED_SYENITE_WALL = register("cobbled_syenite_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE).mapColor(MapColor.BROWN)));
        POLISHED_SYENITE = register("polished_syenite", new Block(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.BROWN)));
        POLISHED_SYENITE_STAIRS = register("polished_syenite_stairs", new CustomStairsBlock(POLISHED_SYENITE.getDefaultState(), FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_STAIRS).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        POLISHED_SYENITE_SLAB = register("polished_syenite_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB).mapColor(MapColor.LIGHT_BLUE_GRAY)));
        SYENITE_BRICKS = register("syenite_bricks", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.BROWN)));
        CRACKED_SYENITE_BRICKS = register("cracked_syenite_bricks", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.BROWN)));
        SYENITE_BRICK_STAIRS = register("syenite_brick_stairs", new CustomStairsBlock(SYENITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICK_STAIRS)));
        SYENITE_BRICK_SLAB = register("syenite_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICK_SLAB)));
        SYENITE_BRICK_WALL = register("syenite_brick_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICK_WALL)));
        RED_GLIMMER_CRYSTAL = register("red_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().offsetType(CrystalBlock::getOffsetType).requires(VirtualAdditions.PREVIEW)));
        GREEN_GLIMMER_CRYSTAL = register("green_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().offsetType(CrystalBlock::getOffsetType).requires(VirtualAdditions.PREVIEW)));
        BLUE_GLIMMER_CRYSTAL = register("blue_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().offsetType(CrystalBlock::getOffsetType).requires(VirtualAdditions.PREVIEW)));
        SPOTLIGHT = register("spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).mapColor(MapColor.ORANGE).nonOpaque().luminance((state) -> state.get(SpotlightBlock.POWERED) ? 6 : 0).emissiveLighting((state, world, pos) -> state.get(Properties.POWERED)) .requires(VirtualAdditions.PREVIEW)));
        SPOTLIGHT_LIGHT = register("spotlight_light", new SpotlightLightBlock(FabricBlockSettings.of(Material.AIR).strength(-1.0F, 3600000.8F).dropsNothing().nonOpaque().luminance((state) -> state.get(SpotlightLightBlock.LIT) ? 13 : 0).requires(VirtualAdditions.PREVIEW)));
        COTTON = register("cotton", new CropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
        OAK_HEDGE = register("oak_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
        SPRUCE_HEDGE = register("spruce_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_LEAVES)));
        BIRCH_HEDGE = register("birch_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_LEAVES)));
        JUNGLE_HEDGE = register("jungle_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_LEAVES)));
        ACACIA_HEDGE = register("acacia_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_LEAVES)));
        DARK_OAK_HEDGE = register("dark_oak_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_LEAVES)));
        MANGROVE_HEDGE = register("mangrove_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_LEAVES)));
        AZALEA_HEDGE = register("azalea_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)));
        FLOWERING_AZALEA_HEDGE = register("flowering_azalea_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.FLOWERING_AZALEA_LEAVES)));
        HANGING_GLOWSILK = register("hanging_glowsilk", new HangingGlowsilkBlock(AbstractBlock.Settings.of(Material.PLANT).mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.WART_BLOCK).luminance((state) -> 6).noCollision().nonOpaque().breakInstantly().requires(VirtualAdditions.PREVIEW)));
        LUMWASP_NEST = register("lumwasp_nest", new LumwaspNestBlock(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK).mapColor(MapColor.DARK_AQUA).requires(VirtualAdditions.PREVIEW)));
        CARVED_STONE = register("carved_stone", new Block(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK).mapColor(MapColor.DARK_AQUA).requires(VirtualAdditions.PREVIEW)));
        SILKBULB = register("silkbulb", new Block(AbstractBlock.Settings.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.DARK_AQUA).luminance( (state) -> 15 ).nonOpaque().requires(VirtualAdditions.PREVIEW)));
        WHITE_SILKBULB = register("white_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.WHITE).requires(VirtualAdditions.PREVIEW)));
        LIGHT_GRAY_SILKBULB = register("light_gray_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIGHT_GRAY).requires(VirtualAdditions.PREVIEW)));
        GRAY_SILKBULB = register("gray_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.GRAY).requires(VirtualAdditions.PREVIEW)));
        BLACK_SILKBULB = register("black_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BLACK).requires(VirtualAdditions.PREVIEW)));
        BROWN_SILKBULB = register("brown_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BROWN).requires(VirtualAdditions.PREVIEW)));
        RED_SILKBULB = register("red_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.RED).requires(VirtualAdditions.PREVIEW)));
        ORANGE_SILKBULB = register("orange_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.ORANGE).requires(VirtualAdditions.PREVIEW)));
        YELLOW_SILKBULB = register("yellow_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.YELLOW).requires(VirtualAdditions.PREVIEW)));
        LIME_SILKBULB = register("lime_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIME).requires(VirtualAdditions.PREVIEW)));
        GREEN_SILKBULB = register("green_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.GREEN).requires(VirtualAdditions.PREVIEW)));
        CYAN_SILKBULB = register("cyan_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.CYAN).requires(VirtualAdditions.PREVIEW)));
        LIGHT_BLUE_SILKBULB = register("light_blue_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIGHT_BLUE).requires(VirtualAdditions.PREVIEW)));
        BLUE_SILKBULB = register("blue_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BLUE).requires(VirtualAdditions.PREVIEW)));
        PURPLE_SILKBULB = register("purple_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.PURPLE).requires(VirtualAdditions.PREVIEW)));
        MAGENTA_SILKBULB = register("magenta_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.MAGENTA).requires(VirtualAdditions.PREVIEW)));
        PINK_SILKBULB = register("pink_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.PINK).requires(VirtualAdditions.PREVIEW)));
        ACID = register("acid", new AcidFluidBlock(VAFluids.ACID, AbstractBlock.Settings.of(Material.WATER).mapColor(MapColor.LIME).luminance(((state) -> 6)).noCollision().strength(100.0F).dropsNothing().allowsSpawning(((state, world, pos, type) -> type == VAEntityType.LUMWASP)).requires(VirtualAdditions.PREVIEW)));
        IOLITE_ORE = register("iolite_ore", new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.5F, 4.5F).requiresTool().requires(VirtualAdditions.PREVIEW), UniformIntProvider.create(5, 9)));
        IOLITE_BLOCK = register("iolite_block", new Block(FabricBlockSettings.of(Material.METAL).mapColor(MapColor.MAGENTA).sounds(BlockSoundGroup.METAL).requiresTool().hardness(5.0F).resistance(300.0F).requires(VirtualAdditions.PREVIEW)));
        WARP_ANCHOR = register("warp_anchor", new WarpTetherBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(WarpTetherBlock.POWERED) ).hardness(22.5F).requiresTool().resistance(600.0F).requires(VirtualAdditions.PREVIEW)));
        WARP_TETHER = register("warp_tether", new WarpAnchorBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(WarpAnchorBlock.COOLDOWN) ).hardness(22.5F).requiresTool().resistance(600.0F).requires(VirtualAdditions.PREVIEW)));
        ENTANGLEMENT_DRIVE = register("entanglement_drive", new EntanglementDriveBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> true ).hardness(22.5F).requiresTool().resistance(600.0F).requires(VirtualAdditions.PREVIEW)));
    }
    public static void init(){
        LandPathNodeTypesRegistry.register(ACID, PathNodeType.LAVA, PathNodeType.DANGER_FIRE);
    }

    /**
     * Registers a new block
     *
     * @param id The in-game ID. This will be "modid:id"
     * @param block The block to be registered. Any class that extends the class Block will work.
     * **/
    protected static <T extends Block> Block register(String id, T block) {
        return Registry.register(Registries.BLOCK, VirtualAdditions.idOf(id), block);
    }

}
