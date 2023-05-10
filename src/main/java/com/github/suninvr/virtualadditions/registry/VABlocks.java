package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.*;
import com.github.suninvr.virtualadditions.mixin.FireBlockAccessor;
import com.github.suninvr.virtualadditions.registry.constructors.block.CustomStairsBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.github.suninvr.virtualadditions.registry.RegistryHelper.BlockRegistryHelper.register;

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
    public static final Block CHERRY_HEDGE;
    public static final Block AZALEA_HEDGE;
    public static final Block FLOWERING_AZALEA_HEDGE;
    public static final Block GLOWING_SILK;
    public static final Block FRAYED_SILK;
    public static final Block LUMWASP_NEST;
    public static final Block SILK_BLOCK;
    public static final Block GREENCAP_MUSHROOM;
    public static final Block TALL_GREENCAP_MUSHROOMS;
    public static final Block WEBBED_SILK;
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
        CLIMBING_ROPE = register("climbing_rope", new ClimbingRopeBlock(FabricBlockSettings.of().sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().burnable().hardness(0.5F)));
        CLIMBING_ROPE_ANCHOR = register("climbing_rope_anchor", new ClimbingRopeAnchorBlock(FabricBlockSettings.of().sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().burnable().hardness(0.5F)));

        RAW_STEEL_BLOCK = register("raw_steel_block", new Block(FabricBlockSettings.of().mapColor(MapColor.GRAY).requiresTool().strength(5.0F, 300.0F)));
        STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.of().mapColor(MapColor.GRAY).sounds(BlockSoundGroup.NETHERITE).requiresTool().hardness(5.0F).resistance(300.0F)));

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

        RED_GLIMMER_CRYSTAL = register("red_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().pistonBehavior(PistonBehavior.DESTROY).requires(VirtualAdditions.PREVIEW)));
        GREEN_GLIMMER_CRYSTAL = register("green_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().pistonBehavior(PistonBehavior.DESTROY).requires(VirtualAdditions.PREVIEW)));
        BLUE_GLIMMER_CRYSTAL = register("blue_glimmer_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().dynamicBounds().pistonBehavior(PistonBehavior.DESTROY).requires(VirtualAdditions.PREVIEW)));
        SPOTLIGHT = register("spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).mapColor(MapColor.ORANGE).nonOpaque().luminance((state) -> state.get(SpotlightBlock.POWERED) ? 6 : 0).emissiveLighting((state, world, pos) -> state.get(Properties.POWERED)) .requires(VirtualAdditions.PREVIEW)));
        SPOTLIGHT_LIGHT = register("spotlight_light", new SpotlightLightBlock(FabricBlockSettings.of().strength(-1.0F, 3600000.8F).sounds(BlockSoundGroup.field_44608).replaceable().dropsNothing().nonOpaque().luminance((state) -> state.get(SpotlightLightBlock.LIT) ? 13 : 0).requires(VirtualAdditions.PREVIEW)));

        COTTON = register("cotton", new CropBlock(AbstractBlock.Settings.of().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));

        OAK_HEDGE = register("oak_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
        SPRUCE_HEDGE = register("spruce_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_LEAVES)));
        BIRCH_HEDGE = register("birch_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_LEAVES)));
        JUNGLE_HEDGE = register("jungle_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_LEAVES)));
        ACACIA_HEDGE = register("acacia_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_LEAVES)));
        DARK_OAK_HEDGE = register("dark_oak_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_LEAVES)));
        MANGROVE_HEDGE = register("mangrove_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_LEAVES)));
        CHERRY_HEDGE = register("cherry_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_LEAVES)));
        AZALEA_HEDGE = register("azalea_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)));
        FLOWERING_AZALEA_HEDGE = register("flowering_azalea_hedge", new HedgeBlock(AbstractBlock.Settings.copy(Blocks.FLOWERING_AZALEA_LEAVES)));

        GLOWING_SILK = register("glowing_silk", new GlowingSilkBlock(AbstractBlock.Settings.of().mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.WART_BLOCK).luminance((state) -> 6).noCollision().nonOpaque().breakInstantly().offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY)));
        FRAYED_SILK = register("frayed_silk", new FrayedSilkBlock(AbstractBlock.Settings.of().mapColor(MapColor.DARK_AQUA).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().breakInstantly().replaceable().offset(AbstractBlock.OffsetType.XZ).burnable().pistonBehavior(PistonBehavior.DESTROY)));
        LUMWASP_NEST = register("lumwasp_nest", new LumwaspNestBlock(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK).sounds(BlockSoundGroup.WART_BLOCK).mapColor(MapColor.DARK_AQUA).burnable()));
        SILK_BLOCK = register("silk_block", new SilkBlock(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK).sounds(BlockSoundGroup.WART_BLOCK).mapColor(MapColor.DARK_AQUA).burnable()));
        GREENCAP_MUSHROOM = register("greencap_mushroom", new GreencapMushroomBlock(AbstractBlock.Settings.of().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.NETHER_SPROUTS).offset(AbstractBlock.OffsetType.XZ).luminance((state) -> 2).pistonBehavior(PistonBehavior.DESTROY)));
        TALL_GREENCAP_MUSHROOMS = register("tall_greencap_mushrooms", new TallGreencapMushroomBlock(AbstractBlock.Settings.of().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.NETHER_SPROUTS).offset(AbstractBlock.OffsetType.XZ).luminance((state) -> 5).pistonBehavior(PistonBehavior.DESTROY)));
        WEBBED_SILK = register("webbed_silk", new SilkFluffBlock(AbstractBlock.Settings.of().strength(0.25F).sounds(BlockSoundGroup.POWDER_SNOW).mapColor(MapColor.DARK_AQUA).nonOpaque().solidBlock((state, world, pos) -> false).suffocates(((state, world, pos) -> false)).blockVision((state, world, pos) -> false).burnable().allowsSpawning((state, world, pos, type) -> type == VAEntityType.LUMWASP)));
        SILKBULB = register("silkbulb", new Block(AbstractBlock.Settings.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.DARK_AQUA).luminance( (state) -> 15 ).burnable()));
        WHITE_SILKBULB = register("white_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.WHITE)));
        LIGHT_GRAY_SILKBULB = register("light_gray_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIGHT_GRAY)));
        GRAY_SILKBULB = register("gray_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.GRAY)));
        BLACK_SILKBULB = register("black_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BLACK)));
        BROWN_SILKBULB = register("brown_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BROWN)));
        RED_SILKBULB = register("red_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.RED)));
        ORANGE_SILKBULB = register("orange_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.ORANGE)));
        YELLOW_SILKBULB = register("yellow_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.YELLOW)));
        LIME_SILKBULB = register("lime_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIME)));
        GREEN_SILKBULB = register("green_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.GREEN)));
        CYAN_SILKBULB = register("cyan_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.CYAN)));
        LIGHT_BLUE_SILKBULB = register("light_blue_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.LIGHT_BLUE)));
        BLUE_SILKBULB = register("blue_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.BLUE)));
        PURPLE_SILKBULB = register("purple_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.PURPLE)));
        MAGENTA_SILKBULB = register("magenta_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.MAGENTA)));
        PINK_SILKBULB = register("pink_silkbulb", new Block(AbstractBlock.Settings.copy(SILKBULB).mapColor(MapColor.PINK)));

        ACID = register("acid", new AcidFluidBlock(VAFluids.ACID, AbstractBlock.Settings.of().mapColor(MapColor.LIME).luminance(((state) -> 6)).sounds(BlockSoundGroup.field_44608).replaceable().noCollision().strength(100.0F).dropsNothing().allowsSpawning(((state, world, pos, type) -> type == VAEntityType.LUMWASP)).pistonBehavior(PistonBehavior.DESTROY)));

        IOLITE_ORE = register("iolite_ore", new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.5F, 4.5F).requiresTool(), UniformIntProvider.create(5, 9)));
        IOLITE_BLOCK = register("iolite_block", new Block(FabricBlockSettings.of().mapColor(MapColor.MAGENTA).sounds(BlockSoundGroup.METAL).requiresTool().hardness(5.0F).resistance(300.0F)));
        WARP_ANCHOR = register("warp_anchor", new WarpAnchorBlock(FabricBlockSettings.of().nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(WarpAnchorBlock.POWERED) ).hardness(22.5F).requiresTool().resistance(600.0F)));
        WARP_TETHER = register("warp_tether", new WarpTetherBlock(FabricBlockSettings.of().nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(WarpTetherBlock.COOLDOWN) ).hardness(22.5F).requiresTool().resistance(600.0F)));
        ENTANGLEMENT_DRIVE = register("entanglement_drive", new EntanglementDriveBlock(FabricBlockSettings.of().nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(Properties.POWERED) ).hardness(22.5F).requiresTool().resistance(600.0F)));
    }
    public static void init(){
        LandPathNodeTypesRegistry.register(ACID, PathNodeType.LAVA, PathNodeType.DANGER_FIRE);
        FireBlockAccessor fire = (FireBlockAccessor)Blocks.FIRE;
        fire.virtualAdditions$registerFlammableBlock(CLIMBING_ROPE_ANCHOR, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(CLIMBING_ROPE, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(OAK_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(SPRUCE_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(BIRCH_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(JUNGLE_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(ACACIA_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(DARK_OAK_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(MANGROVE_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(CHERRY_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(AZALEA_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(FLOWERING_AZALEA_HEDGE, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(GLOWING_SILK, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(FRAYED_SILK, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(LUMWASP_NEST, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(SILK_BLOCK, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(WEBBED_SILK, 30, 60);
        fire.virtualAdditions$registerFlammableBlock(SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(WHITE_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(LIGHT_GRAY_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(GRAY_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(BLACK_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(BROWN_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(RED_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(ORANGE_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(YELLOW_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(LIME_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(GREEN_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(CYAN_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(LIGHT_BLUE_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(BLUE_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(PURPLE_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(MAGENTA_SILKBULB, 5, 20);
        fire.virtualAdditions$registerFlammableBlock(PINK_SILKBULB, 5, 20);

    }

}
