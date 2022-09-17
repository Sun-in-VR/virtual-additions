package com.github.suninvr.virtualadditions.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import com.github.suninvr.virtualadditions.VirtualAdditions;
import com.github.suninvr.virtualadditions.block.*;
import com.github.suninvr.virtualadditions.registry.constructors.block.CustomFluidBlock;
import com.github.suninvr.virtualadditions.registry.constructors.block.CustomRootsBlock;
import com.github.suninvr.virtualadditions.registry.constructors.block.CustomStairsBlock;

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
    public static final Block ENTANGLEMENT_DRIVE;
    public static final Block IOLITE_ORE;
    public static final Block IOLITE_ANCHOR;
    public static final Block IOLITE_TETHER;
    public static final Block LUMINOUS_CRYSTAL;
    public static final Block LUMINOUS_CRYSTAL_BLOCK;
    public static final Block SPOTLIGHT;
    public static final Block SPOTLIGHT_LIGHT;
    public static final Block VENOMOUS_BOIL;
    public static final Block TOXIC_NYLIUM;
    public static final Block TOXIC_ROOTS;
    public static final Block TOXIC_HEAP;
    public static final Block VISCOUS_FILM;
    public static final Block VISCOUS_POD;
    public static final Block VISCOUS_MEMBRANE;
    public static final Block ACID;
    //public static final Block ACID_CAULDRON;
    public static final Block ICE_SHEET;
    public static final Block SLIME_TENDRILS;
    public static final Block COTTON;

    static {
        CLIMBING_ROPE = register("climbing_rope", new ClimbingRopeBlock(FabricBlockSettings.of(Material.WOOL).sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().hardness(0.1F)));
        CLIMBING_ROPE_ANCHOR = register("climbing_rope_anchor", new ClimbingRopeAnchorBlock(FabricBlockSettings.of(Material.WOOL).sounds(ROPE_SOUND_GROUP).collidable(false).nonOpaque().hardness(0.1F)));
        RAW_STEEL_BLOCK = register("raw_steel_block", new Block(FabricBlockSettings.of(Material.STONE).mapColor(MapColor.GRAY).requiresTool().strength(5.0F, 300.0F)));
        STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.of(Material.METAL).mapColor(MapColor.GRAY).sounds(BlockSoundGroup.NETHERITE).requiresTool().hardness(5.0F).resistance(300.0F)));
        //WORKBENCH = register("workbench", new WorkbenchBlock(FabricBlockSettings.copyOf(Blocks.SMITHING_TABLE)));
        HORNFELS = register("hornfels", new PillarBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
        COBBLED_HORNFELS = register("cobbled_hornfels", new Block(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_STAIRS = register("cobbled_hornfels_stairs", new CustomStairsBlock(COBBLED_HORNFELS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_SLAB = register("cobbled_hornfels_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        COBBLED_HORNFELS_WALL = register("cobbled_hornfels_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE)));
        POLISHED_HORNFELS = register("polished_hornfels", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DEEPSLATE)));
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
        ENTANGLEMENT_DRIVE = register("entanglement_drive", new EntanglementDriveBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> true ).hardness(22.5F).resistance(600.0F)));
        IOLITE_ORE = register("iolite_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.5F, 4.5F), UniformIntProvider.create(5, 9)));
        IOLITE_ANCHOR = register("iolite_anchor", new IoliteAnchorBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(IoliteAnchorBlock.POWERED) ).hardness(22.5F).resistance(600.0F)));
        IOLITE_TETHER = register("iolite_tether", new IoliteTetherBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().sounds(BlockSoundGroup.NETHERITE).luminance(3).emissiveLighting( (state, world, pos) -> !state.get(IoliteTetherBlock.COOLDOWN) ).hardness(22.5F).resistance(600.0F)));
        LUMINOUS_CRYSTAL = register("luminous_crystal", new CrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE_GRAY).nonOpaque().emissiveLighting(((state, world, pos) -> true))));
        LUMINOUS_CRYSTAL_BLOCK = register("luminous_crystal_block", new TransparentBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.WHITE_GRAY).nonOpaque().luminance(5).emissiveLighting(((state, world, pos) -> true))));
        SPOTLIGHT = register("spotlight", new SpotlightBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).mapColor(MapColor.ORANGE).nonOpaque().luminance((state) -> state.get(SpotlightBlock.POWERED) ? 6 : 0).emissiveLighting((state, world, pos) -> state.get(Properties.POWERED)) ));
        SPOTLIGHT_LIGHT = register("spotlight_light", new SpotlightLightBlock(FabricBlockSettings.of(Material.AIR).strength(-1.0F, 3600000.8F).dropsNothing().nonOpaque().luminance((state) -> state.get(SpotlightLightBlock.LIT) ? 13 : 0)));
        VENOMOUS_BOIL = register("venomous_boil", new VenomousBoilBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).mapColor(MapColor.LIME).sounds(BlockSoundGroup.FUNGUS).collidable(false).nonOpaque().luminance(1).emissiveLighting( ((state, world, pos) -> state.get(VenomousBoilBlock.AGE) == 3) )));
        TOXIC_NYLIUM = register("toxic_nylium", new ToxicNyliumBlock(FabricBlockSettings.of(Material.STONE, MapColor.LIME).sounds(BlockSoundGroup.NYLIUM).requiresTool().strength(0.4F)));
        TOXIC_ROOTS = register("toxic_roots", new CustomRootsBlock(FabricBlockSettings.of(Material.NETHER_SHOOTS, MapColor.LIME).sounds(BlockSoundGroup.ROOTS).noCollision().breakInstantly()));
        TOXIC_HEAP = register("toxic_heap", new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.LIME).sounds(BlockSoundGroup.WART_BLOCK)));
        VISCOUS_FILM = register("viscous_film", new ViscousFilmBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.PALE_YELLOW).nonOpaque().sounds(BlockSoundGroup.FROGSPAWN)));
        VISCOUS_POD = register("viscous_pod", new ViscousPodBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.LIME).nonOpaque().sounds(BlockSoundGroup.FROGSPAWN)));
        VISCOUS_MEMBRANE = register("viscous_membrane", new ViscousMembraneBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.LIME).nonOpaque().sounds(BlockSoundGroup.CORAL).hardness(1.0F)));
        ACID = register("acid", new CustomFluidBlock(VAFluids.ACID, false, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F).dropsNothing().mapColor(MapColor.DARK_GREEN)));
        //ACID_CAULDRON = register("acid_cauldron", new AcidCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON).mapColor(MapColor.GRAY)));
        ICE_SHEET = register("ice_sheet", new IceSheetBlock(FabricBlockSettings.copyOf(Blocks.ICE).nonOpaque()));
        SLIME_TENDRILS = register("slime_tendrils", new SlimeTendrilsBlock(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK).nonOpaque().noCollision()));
        COTTON = register("cotton", new CropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    }

    public static void init(){}

    /**
     * Registers a new block
     *
     * @param id The in-game ID. This will be "modid:id"
     * @param block The block to be registered. Any class that extends the class Block will work.
     * **/
    protected static <T extends Block> Block register(String id, T block) {
        return Registry.register(Registry.BLOCK, VirtualAdditions.idOf(id), block);
    }

}
