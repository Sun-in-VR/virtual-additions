package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.function.Supplier;

import static com.github.suninvr.virtualadditions.registry.VABlocks.*;
import static net.minecraft.block.Blocks.*;

public class VACollections {

    public static final BlockFamily CUT_STEEL;
    public static final BlockFamily EXPOSED_CUT_STEEL;
    public static final BlockFamily WEATHERED_CUT_STEEL;
    public static final BlockFamily OXIDIZED_CUT_STEEL;
    public static final BlockFamily WAXED_CUT_STEEL;
    public static final BlockFamily WAXED_EXPOSED_CUT_STEEL;
    public static final BlockFamily WAXED_WEATHERED_CUT_STEEL;
    public static final BlockFamily WAXED_OXIDIZED_CUT_STEEL;
    public static final BlockFamily COBBLED_HORNFELS;
    public static final BlockFamily POLISHED_HORNFELS;
    public static final BlockFamily HORNFELS_TILES;
    public static final BlockFamily COBBLED_BLUESCHIST;
    public static final BlockFamily POLISHED_BLUESCHIST;
    public static final BlockFamily BLUESCHIST_BRICKS;
    public static final BlockFamily COBBLED_SYENITE;
    public static final BlockFamily POLISHED_SYENITE;
    public static final BlockFamily SYENITE_BRICKS;
    public static final BlockFamily AEROBLOOM;
    public static final BlockFamily FLOATROCK;
    public static final BlockFamily FLOATROCK_BRICKS;
    public static final BlockFamily POLISHED_FLOATROCK;

    public static final ArrayList<ColorfulBlockSet> COLORFUL_BLOCK_SETS = new ArrayList<>();
    public static final ColorfulBlockSet WHITE;
    public static final ColorfulBlockSet LIGHT_GRAY;
    public static final ColorfulBlockSet GRAY;
    public static final ColorfulBlockSet BLACK;
    public static final ColorfulBlockSet TAN;
    public static final ColorfulBlockSet BROWN;
    public static final ColorfulBlockSet LILAC;
    public static final ColorfulBlockSet MAROON;
    public static final ColorfulBlockSet RED;
    public static final ColorfulBlockSet SINOPIA;
    public static final ColorfulBlockSet ORANGE;
    public static final ColorfulBlockSet YELLOW;
    public static final ColorfulBlockSet CHARTREUSE;
    public static final ColorfulBlockSet LIME;
    public static final ColorfulBlockSet GREEN;
    public static final ColorfulBlockSet VIRIDIAN;
    public static final ColorfulBlockSet CYAN;
    public static final ColorfulBlockSet LIGHT_BLUE;
    public static final ColorfulBlockSet BLUE;
    public static final ColorfulBlockSet INDIGO;
    public static final ColorfulBlockSet PURPLE;
    public static final ColorfulBlockSet PLUM;
    public static final ColorfulBlockSet MAGENTA;
    public static final ColorfulBlockSet PINK;

    public static final Supplier<ImmutableBiMap<Block, Block>> CLIMBING_ROPE_OXIDIZATION_INCREASES;
    public static final Supplier<ImmutableBiMap<Block, Block>> CLIMBING_ROPE_OXIDIZATION_DECREASES;
    public static final Supplier<ImmutableBiMap<Block, Block>> UNWAXED_TO_WAXED_CLIMBING_ROPES;
    public static final Supplier<ImmutableBiMap<Block, Block>> WAXED_TO_UNWAXED_CLIMBING_ROPES;

    static {
        CUT_STEEL = register(VABlocks.CUT_STEEL).stairs(VABlocks.CUT_STEEL_STAIRS).slab(VABlocks.CUT_STEEL_SLAB).customFence(VABlocks.STEEL_FENCE).chiseled(CHISELED_STEEL).build();
        EXPOSED_CUT_STEEL = register(VABlocks.EXPOSED_CUT_STEEL).stairs(VABlocks.EXPOSED_CUT_STEEL_STAIRS).slab(VABlocks.EXPOSED_CUT_STEEL_SLAB).chiseled(EXPOSED_CHISELED_STEEL).customFence(VABlocks.EXPOSED_STEEL_FENCE).build();
        WEATHERED_CUT_STEEL = register(VABlocks.WEATHERED_CUT_STEEL).stairs(VABlocks.WEATHERED_CUT_STEEL_STAIRS).slab(VABlocks.WEATHERED_CUT_STEEL_SLAB).chiseled(WEATHERED_CHISELED_STEEL).customFence(VABlocks.WEATHERED_STEEL_FENCE).build();
        OXIDIZED_CUT_STEEL = register(VABlocks.OXIDIZED_CUT_STEEL).stairs(VABlocks.OXIDIZED_CUT_STEEL_STAIRS).slab(VABlocks.OXIDIZED_CUT_STEEL_SLAB).chiseled(OXIDIZED_CHISELED_STEEL).customFence(VABlocks.OXIDIZED_STEEL_FENCE).build();
        WAXED_CUT_STEEL = register(VABlocks.WAXED_CUT_STEEL).stairs(VABlocks.WAXED_CUT_STEEL_STAIRS).slab(VABlocks.WAXED_CUT_STEEL_SLAB).chiseled(WAXED_CHISELED_STEEL).customFence(VABlocks.WAXED_STEEL_FENCE).build();
        WAXED_EXPOSED_CUT_STEEL = register(VABlocks.WAXED_EXPOSED_CUT_STEEL).stairs(VABlocks.WAXED_EXPOSED_CUT_STEEL_STAIRS).slab(VABlocks.WAXED_EXPOSED_CUT_STEEL_SLAB).chiseled(WAXED_EXPOSED_CHISELED_STEEL).customFence(VABlocks.WAXED_EXPOSED_STEEL_FENCE).build();
        WAXED_WEATHERED_CUT_STEEL = register(VABlocks.WAXED_WEATHERED_CUT_STEEL).stairs(VABlocks.WAXED_WEATHERED_CUT_STEEL_STAIRS).slab(VABlocks.WAXED_WEATHERED_CUT_STEEL_SLAB).chiseled(WAXED_WEATHERED_CHISELED_STEEL).customFence(VABlocks.WAXED_WEATHERED_STEEL_FENCE).build();
        WAXED_OXIDIZED_CUT_STEEL = register(VABlocks.WAXED_OXIDIZED_CUT_STEEL).stairs(VABlocks.WAXED_OXIDIZED_CUT_STEEL_STAIRS).slab(VABlocks.WAXED_OXIDIZED_CUT_STEEL_SLAB).chiseled(WAXED_OXIDIZED_CHISELED_STEEL).customFence(VABlocks.WAXED_OXIDIZED_STEEL_FENCE).build();
        COBBLED_HORNFELS = register(VABlocks.COBBLED_HORNFELS).stairs(VABlocks.COBBLED_HORNFELS_STAIRS).slab(VABlocks.COBBLED_HORNFELS_SLAB).wall(VABlocks.COBBLED_HORNFELS_WALL).build();
        POLISHED_HORNFELS = register(VABlocks.POLISHED_HORNFELS).stairs(VABlocks.POLISHED_HORNFELS_STAIRS).slab(VABlocks.POLISHED_HORNFELS_SLAB).chiseled(CHISELED_HORNFELS).build();
        HORNFELS_TILES = register(VABlocks.HORNFELS_TILES).stairs(VABlocks.HORNFELS_TILE_STAIRS).slab(VABlocks.HORNFELS_TILE_SLAB).cracked(VABlocks.CRACKED_HORNFELS_TILES).chiseled(CHISELED_HORNFELS_TILES).build();
        COBBLED_BLUESCHIST = register(VABlocks.COBBLED_BLUESCHIST).stairs(VABlocks.COBBLED_BLUESCHIST_STAIRS).slab(VABlocks.COBBLED_BLUESCHIST_SLAB).wall(VABlocks.COBBLED_BLUESCHIST_WALL).build();
        POLISHED_BLUESCHIST = register(VABlocks.POLISHED_BLUESCHIST).stairs(VABlocks.POLISHED_BLUESCHIST_STAIRS).slab(VABlocks.POLISHED_BLUESCHIST_SLAB).build();
        BLUESCHIST_BRICKS = register(VABlocks.BLUESCHIST_BRICKS).stairs(VABlocks.BLUESCHIST_BRICK_STAIRS).slab(VABlocks.BLUESCHIST_BRICK_SLAB).wall(VABlocks.BLUESCHIST_BRICK_WALL).cracked(VABlocks.CRACKED_BLUESCHIST_BRICKS).chiseled(VABlocks.CHISELED_BLUESCHIST).build();
        COBBLED_SYENITE = register(VABlocks.COBBLED_SYENITE).stairs(VABlocks.COBBLED_SYENITE_STAIRS).slab(VABlocks.COBBLED_SYENITE_SLAB).wall(VABlocks.COBBLED_SYENITE_WALL).build();
        POLISHED_SYENITE = register(VABlocks.POLISHED_SYENITE).stairs(VABlocks.POLISHED_SYENITE_STAIRS).slab(VABlocks.POLISHED_SYENITE_SLAB).build();
        SYENITE_BRICKS = register(VABlocks.SYENITE_BRICKS).stairs(VABlocks.SYENITE_BRICK_STAIRS).slab(VABlocks.SYENITE_BRICK_SLAB).wall(VABlocks.SYENITE_BRICK_WALL).cracked(VABlocks.CRACKED_SYENITE_BRICKS).chiseled(VABlocks.CHISELED_SYENITE).build();
        AEROBLOOM = register(VABlocks.AEROBLOOM_PLANKS).button(VABlocks.AEROBLOOM_BUTTON).fence(VABlocks.AEROBLOOM_FENCE).fenceGate(VABlocks.AEROBLOOM_FENCE_GATE).sign(VABlocks.AEROBLOOM_SIGN, VABlocks.AEROBLOOM_WALL_SIGN).pressurePlate(VABlocks.AEROBLOOM_PRESSURE_PLATE).slab(VABlocks.AEROBLOOM_SLAB).stairs(VABlocks.AEROBLOOM_STAIRS).door(VABlocks.AEROBLOOM_DOOR).trapdoor(VABlocks.AEROBLOOM_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();
        FLOATROCK = register(VABlocks.FLOATROCK).stairs(VABlocks.FLOATROCK_STAIRS).slab(VABlocks.FLOATROCK_SLAB).wall(VABlocks.FLOATROCK_WALL).build();
        FLOATROCK_BRICKS = register(VABlocks.FLOATROCK_BRICKS).stairs(VABlocks.FLOATROCK_BRICK_STAIRS).slab(VABlocks.FLOATROCK_BRICK_SLAB).wall(VABlocks.FLOATROCK_BRICK_WALL).build();
        POLISHED_FLOATROCK = register(VABlocks.POLISHED_FLOATROCK).stairs(VABlocks.POLISHED_FLOATROCK_STAIRS).slab(VABlocks.POLISHED_FLOATROCK_SLAB).wall(VABlocks.POLISHED_FLOATROCK_WALL).build();
        
        WHITE = ColorfulBlockSet.Builder.create(Items.WHITE_DYE).wool(WHITE_WOOL).carpet(WHITE_CARPET).terracotta(WHITE_TERRACOTTA).concrete(WHITE_CONCRETE).concretePowder(WHITE_CONCRETE_POWDER).stainedGlass(WHITE_STAINED_GLASS).stainedGlassPane(WHITE_STAINED_GLASS_PANE).candle(WHITE_CANDLE).candleCake(WHITE_CANDLE_CAKE).silkbulb(WHITE_SILKBULB).bed(WHITE_BED).shulkerBox(WHITE_SHULKER_BOX).banner(WHITE_BANNER).wallBanner(WHITE_WALL_BANNER).glazedTerracotta(WHITE_GLAZED_TERRACOTTA).build();
        LIGHT_GRAY = ColorfulBlockSet.Builder.create(Items.LIGHT_GRAY_DYE).wool(LIGHT_GRAY_WOOL).carpet(LIGHT_GRAY_CARPET).terracotta(LIGHT_GRAY_TERRACOTTA).concrete(LIGHT_GRAY_CONCRETE).concretePowder(LIGHT_GRAY_CONCRETE_POWDER).stainedGlass(LIGHT_GRAY_STAINED_GLASS).stainedGlassPane(LIGHT_GRAY_STAINED_GLASS_PANE).candle(LIGHT_GRAY_CANDLE).candleCake(LIGHT_GRAY_CANDLE_CAKE).silkbulb(LIGHT_GRAY_SILKBULB).bed(LIGHT_GRAY_BED).shulkerBox(LIGHT_GRAY_SHULKER_BOX).banner(LIGHT_GRAY_BANNER).wallBanner(LIGHT_GRAY_WALL_BANNER).glazedTerracotta(LIGHT_GRAY_GLAZED_TERRACOTTA).build();
        GRAY = ColorfulBlockSet.Builder.create(Items.GRAY_DYE).wool(GRAY_WOOL).carpet(GRAY_CARPET).terracotta(GRAY_TERRACOTTA).concrete(GRAY_CONCRETE).concretePowder(GRAY_CONCRETE_POWDER).stainedGlass(GRAY_STAINED_GLASS).stainedGlassPane(GRAY_STAINED_GLASS_PANE).candle(GRAY_CANDLE).candleCake(GRAY_CANDLE_CAKE).silkbulb(GRAY_SILKBULB).bed(GRAY_BED).shulkerBox(GRAY_SHULKER_BOX).banner(GRAY_BANNER).wallBanner(GRAY_WALL_BANNER).glazedTerracotta(GRAY_GLAZED_TERRACOTTA).build();
        BLACK = ColorfulBlockSet.Builder.create(Items.BLACK_DYE).wool(BLACK_WOOL).carpet(BLACK_CARPET).terracotta(BLACK_TERRACOTTA).concrete(BLACK_CONCRETE).concretePowder(BLACK_CONCRETE_POWDER).stainedGlass(BLACK_STAINED_GLASS).stainedGlassPane(BLACK_STAINED_GLASS_PANE).candle(BLACK_CANDLE).candleCake(BLACK_CANDLE_CAKE).silkbulb(BLACK_SILKBULB).bed(BLACK_BED).shulkerBox(BLACK_SHULKER_BOX).banner(BLACK_BANNER).wallBanner(BLACK_WALL_BANNER).glazedTerracotta(BLACK_GLAZED_TERRACOTTA).build();
        BROWN = ColorfulBlockSet.Builder.create(Items.BROWN_DYE).wool(BROWN_WOOL).carpet(BROWN_CARPET).terracotta(BROWN_TERRACOTTA).concrete(BROWN_CONCRETE).concretePowder(BROWN_CONCRETE_POWDER).stainedGlass(BROWN_STAINED_GLASS).stainedGlassPane(BROWN_STAINED_GLASS_PANE).candle(BROWN_CANDLE).candleCake(BROWN_CANDLE_CAKE).silkbulb(BROWN_SILKBULB).bed(BROWN_BED).shulkerBox(BROWN_SHULKER_BOX).banner(BROWN_BANNER).wallBanner(BROWN_WALL_BANNER).glazedTerracotta(BROWN_GLAZED_TERRACOTTA).build();
        LILAC = ColorfulBlockSet.Builder.create(VAItems.LILAC_DYE).wool(LILAC_WOOL).carpet(LILAC_CARPET).terracotta(LILAC_TERRACOTTA).concrete(LILAC_CONCRETE).concretePowder(LILAC_CONCRETE_POWDER).stainedGlass(LILAC_STAINED_GLASS).stainedGlassPane(LILAC_STAINED_GLASS_PANE).candle(LILAC_CANDLE).candleCake(LILAC_CANDLE_CAKE).silkbulb(LILAC_SILKBULB).bed(LILAC_BED).shulkerBox(LILAC_SHULKER_BOX).banner(LILAC_BANNER).wallBanner(LILAC_WALL_BANNER).glazedTerracotta(LILAC_GLAZED_TERRACOTTA).build();
        MAROON = ColorfulBlockSet.Builder.create(VAItems.MAROON_DYE).wool(MAROON_WOOL).carpet(MAROON_CARPET).terracotta(MAROON_TERRACOTTA).concrete(MAROON_CONCRETE).concretePowder(MAROON_CONCRETE_POWDER).stainedGlass(MAROON_STAINED_GLASS).stainedGlassPane(MAROON_STAINED_GLASS_PANE).candle(MAROON_CANDLE).candleCake(MAROON_CANDLE_CAKE).silkbulb(MAROON_SILKBULB).bed(MAROON_BED).shulkerBox(MAROON_SHULKER_BOX).banner(MAROON_BANNER).wallBanner(MAROON_WALL_BANNER).glazedTerracotta(MAROON_GLAZED_TERRACOTTA).build();
        RED = ColorfulBlockSet.Builder.create(Items.RED_DYE).wool(RED_WOOL).carpet(RED_CARPET).terracotta(RED_TERRACOTTA).concrete(RED_CONCRETE).concretePowder(RED_CONCRETE_POWDER).stainedGlass(RED_STAINED_GLASS).stainedGlassPane(RED_STAINED_GLASS_PANE).candle(RED_CANDLE).candleCake(RED_CANDLE_CAKE).silkbulb(RED_SILKBULB).bed(RED_BED).shulkerBox(RED_SHULKER_BOX).banner(RED_BANNER).wallBanner(RED_WALL_BANNER).glazedTerracotta(RED_GLAZED_TERRACOTTA).build();
        SINOPIA = ColorfulBlockSet.Builder.create(VAItems.SINOPIA_DYE).wool(SINOPIA_WOOL).carpet(SINOPIA_CARPET).terracotta(SINOPIA_TERRACOTTA).concrete(SINOPIA_CONCRETE).concretePowder(SINOPIA_CONCRETE_POWDER).stainedGlass(SINOPIA_STAINED_GLASS).stainedGlassPane(SINOPIA_STAINED_GLASS_PANE).candle(SINOPIA_CANDLE).candleCake(SINOPIA_CANDLE_CAKE).silkbulb(SINOPIA_SILKBULB).bed(SINOPIA_BED).shulkerBox(SINOPIA_SHULKER_BOX).banner(SINOPIA_BANNER).wallBanner(SINOPIA_WALL_BANNER).glazedTerracotta(SINOPIA_GLAZED_TERRACOTTA).build();
        ORANGE = ColorfulBlockSet.Builder.create(Items.ORANGE_DYE).wool(ORANGE_WOOL).carpet(ORANGE_CARPET).terracotta(ORANGE_TERRACOTTA).concrete(ORANGE_CONCRETE).concretePowder(ORANGE_CONCRETE_POWDER).stainedGlass(ORANGE_STAINED_GLASS).stainedGlassPane(ORANGE_STAINED_GLASS_PANE).candle(ORANGE_CANDLE).candleCake(ORANGE_CANDLE_CAKE).silkbulb(ORANGE_SILKBULB).bed(ORANGE_BED).shulkerBox(ORANGE_SHULKER_BOX).banner(ORANGE_BANNER).wallBanner(ORANGE_WALL_BANNER).glazedTerracotta(ORANGE_GLAZED_TERRACOTTA).build();
        TAN = ColorfulBlockSet.Builder.create(VAItems.TAN_DYE).wool(TAN_WOOL).carpet(TAN_CARPET).terracotta(TAN_TERRACOTTA).concrete(TAN_CONCRETE).concretePowder(TAN_CONCRETE_POWDER).stainedGlass(TAN_STAINED_GLASS).stainedGlassPane(TAN_STAINED_GLASS_PANE).candle(TAN_CANDLE).candleCake(TAN_CANDLE_CAKE).silkbulb(TAN_SILKBULB).bed(TAN_BED).shulkerBox(TAN_SHULKER_BOX).banner(TAN_BANNER).wallBanner(TAN_WALL_BANNER).glazedTerracotta(TAN_GLAZED_TERRACOTTA).build();
        YELLOW = ColorfulBlockSet.Builder.create(Items.YELLOW_DYE).wool(YELLOW_WOOL).carpet(YELLOW_CARPET).terracotta(YELLOW_TERRACOTTA).concrete(YELLOW_CONCRETE).concretePowder(YELLOW_CONCRETE_POWDER).stainedGlass(YELLOW_STAINED_GLASS).stainedGlassPane(YELLOW_STAINED_GLASS_PANE).candle(YELLOW_CANDLE).candleCake(YELLOW_CANDLE_CAKE).silkbulb(YELLOW_SILKBULB).bed(YELLOW_BED).shulkerBox(YELLOW_SHULKER_BOX).banner(YELLOW_BANNER).wallBanner(YELLOW_WALL_BANNER).glazedTerracotta(YELLOW_GLAZED_TERRACOTTA).build();
        CHARTREUSE = ColorfulBlockSet.Builder.create(VAItems.CHARTREUSE_DYE).wool(CHARTREUSE_WOOL).carpet(CHARTREUSE_CARPET).terracotta(CHARTREUSE_TERRACOTTA).concrete(CHARTREUSE_CONCRETE).concretePowder(CHARTREUSE_CONCRETE_POWDER).stainedGlass(CHARTREUSE_STAINED_GLASS).stainedGlassPane(CHARTREUSE_STAINED_GLASS_PANE).candle(CHARTREUSE_CANDLE).candleCake(CHARTREUSE_CANDLE_CAKE).silkbulb(CHARTREUSE_SILKBULB).bed(CHARTREUSE_BED).shulkerBox(CHARTREUSE_SHULKER_BOX).banner(CHARTREUSE_BANNER).wallBanner(CHARTREUSE_WALL_BANNER).glazedTerracotta(CHARTREUSE_GLAZED_TERRACOTTA).build();
        LIME = ColorfulBlockSet.Builder.create(Items.LIME_DYE).wool(LIME_WOOL).carpet(LIME_CARPET).terracotta(LIME_TERRACOTTA).concrete(LIME_CONCRETE).concretePowder(LIME_CONCRETE_POWDER).stainedGlass(LIME_STAINED_GLASS).stainedGlassPane(LIME_STAINED_GLASS_PANE).candle(LIME_CANDLE).candleCake(LIME_CANDLE_CAKE).silkbulb(LIME_SILKBULB).bed(LIME_BED).shulkerBox(LIME_SHULKER_BOX).banner(LIME_BANNER).wallBanner(LIME_WALL_BANNER).glazedTerracotta(LIME_GLAZED_TERRACOTTA).build();
        GREEN = ColorfulBlockSet.Builder.create(Items.GREEN_DYE).wool(GREEN_WOOL).carpet(GREEN_CARPET).terracotta(GREEN_TERRACOTTA).concrete(GREEN_CONCRETE).concretePowder(GREEN_CONCRETE_POWDER).stainedGlass(GREEN_STAINED_GLASS).stainedGlassPane(GREEN_STAINED_GLASS_PANE).candle(GREEN_CANDLE).candleCake(GREEN_CANDLE_CAKE).silkbulb(GREEN_SILKBULB).bed(GREEN_BED).shulkerBox(GREEN_SHULKER_BOX).banner(GREEN_BANNER).wallBanner(GREEN_WALL_BANNER).glazedTerracotta(GREEN_GLAZED_TERRACOTTA).build();
        VIRIDIAN = ColorfulBlockSet.Builder.create(VAItems.VIRIDIAN_DYE).wool(VIRIDIAN_WOOL).carpet(VIRIDIAN_CARPET).terracotta(VIRIDIAN_TERRACOTTA).concrete(VIRIDIAN_CONCRETE).concretePowder(VIRIDIAN_CONCRETE_POWDER).stainedGlass(VIRIDIAN_STAINED_GLASS).stainedGlassPane(VIRIDIAN_STAINED_GLASS_PANE).candle(VIRIDIAN_CANDLE).candleCake(VIRIDIAN_CANDLE_CAKE).silkbulb(VIRIDIAN_SILKBULB).bed(VIRIDIAN_BED).shulkerBox(VIRIDIAN_SHULKER_BOX).banner(VIRIDIAN_BANNER).wallBanner(VIRIDIAN_WALL_BANNER).glazedTerracotta(VIRIDIAN_GLAZED_TERRACOTTA).build();
        CYAN = ColorfulBlockSet.Builder.create(Items.CYAN_DYE).wool(CYAN_WOOL).carpet(CYAN_CARPET).terracotta(CYAN_TERRACOTTA).concrete(CYAN_CONCRETE).concretePowder(CYAN_CONCRETE_POWDER).stainedGlass(CYAN_STAINED_GLASS).stainedGlassPane(CYAN_STAINED_GLASS_PANE).candle(CYAN_CANDLE).candleCake(CYAN_CANDLE_CAKE).silkbulb(CYAN_SILKBULB).bed(CYAN_BED).shulkerBox(CYAN_SHULKER_BOX).banner(CYAN_BANNER).wallBanner(CYAN_WALL_BANNER).glazedTerracotta(CYAN_GLAZED_TERRACOTTA).build();
        LIGHT_BLUE = ColorfulBlockSet.Builder.create(Items.LIGHT_BLUE_DYE).wool(LIGHT_BLUE_WOOL).carpet(LIGHT_BLUE_CARPET).terracotta(LIGHT_BLUE_TERRACOTTA).concrete(LIGHT_BLUE_CONCRETE).concretePowder(LIGHT_BLUE_CONCRETE_POWDER).stainedGlass(LIGHT_BLUE_STAINED_GLASS).stainedGlassPane(LIGHT_BLUE_STAINED_GLASS_PANE).candle(LIGHT_BLUE_CANDLE).candleCake(LIGHT_BLUE_CANDLE_CAKE).silkbulb(LIGHT_BLUE_SILKBULB).bed(LIGHT_BLUE_BED).shulkerBox(LIGHT_BLUE_SHULKER_BOX).banner(LIGHT_BLUE_BANNER).wallBanner(LIGHT_BLUE_WALL_BANNER).glazedTerracotta(LIGHT_BLUE_GLAZED_TERRACOTTA).build();
        BLUE = ColorfulBlockSet.Builder.create(Items.BLUE_DYE).wool(BLUE_WOOL).carpet(BLUE_CARPET).terracotta(BLUE_TERRACOTTA).concrete(BLUE_CONCRETE).concretePowder(BLUE_CONCRETE_POWDER).stainedGlass(BLUE_STAINED_GLASS).stainedGlassPane(BLUE_STAINED_GLASS_PANE).candle(BLUE_CANDLE).candleCake(BLUE_CANDLE_CAKE).silkbulb(BLUE_SILKBULB).bed(BLUE_BED).shulkerBox(BLUE_SHULKER_BOX).banner(BLUE_BANNER).wallBanner(BLUE_WALL_BANNER).glazedTerracotta(BLUE_GLAZED_TERRACOTTA).build();
        INDIGO = ColorfulBlockSet.Builder.create(VAItems.INDIGO_DYE).wool(INDIGO_WOOL).carpet(INDIGO_CARPET).terracotta(INDIGO_TERRACOTTA).concrete(INDIGO_CONCRETE).concretePowder(INDIGO_CONCRETE_POWDER).stainedGlass(INDIGO_STAINED_GLASS).stainedGlassPane(INDIGO_STAINED_GLASS_PANE).candle(INDIGO_CANDLE).candleCake(INDIGO_CANDLE_CAKE).silkbulb(INDIGO_SILKBULB).bed(INDIGO_BED).shulkerBox(INDIGO_SHULKER_BOX).banner(INDIGO_BANNER).wallBanner(INDIGO_WALL_BANNER).glazedTerracotta(INDIGO_GLAZED_TERRACOTTA).build();
        PURPLE = ColorfulBlockSet.Builder.create(Items.PURPLE_DYE).wool(PURPLE_WOOL).carpet(PURPLE_CARPET).terracotta(PURPLE_TERRACOTTA).concrete(PURPLE_CONCRETE).concretePowder(PURPLE_CONCRETE_POWDER).stainedGlass(PURPLE_STAINED_GLASS).stainedGlassPane(PURPLE_STAINED_GLASS_PANE).candle(PURPLE_CANDLE).candleCake(PURPLE_CANDLE_CAKE).silkbulb(PURPLE_SILKBULB).bed(PURPLE_BED).shulkerBox(PURPLE_SHULKER_BOX).banner(PURPLE_BANNER).wallBanner(PURPLE_WALL_BANNER).glazedTerracotta(PURPLE_GLAZED_TERRACOTTA).build();
        PLUM = ColorfulBlockSet.Builder.create(VAItems.PLUM_DYE).wool(PLUM_WOOL).carpet(PLUM_CARPET).terracotta(PLUM_TERRACOTTA).concrete(PLUM_CONCRETE).concretePowder(PLUM_CONCRETE_POWDER).stainedGlass(PLUM_STAINED_GLASS).stainedGlassPane(PLUM_STAINED_GLASS_PANE).candle(PLUM_CANDLE).candleCake(PLUM_CANDLE_CAKE).silkbulb(PLUM_SILKBULB).bed(PLUM_BED).shulkerBox(PLUM_SHULKER_BOX).banner(PLUM_BANNER).wallBanner(PLUM_WALL_BANNER).glazedTerracotta(PLUM_GLAZED_TERRACOTTA).build();
        MAGENTA = ColorfulBlockSet.Builder.create(Items.MAGENTA_DYE).wool(MAGENTA_WOOL).carpet(MAGENTA_CARPET).terracotta(MAGENTA_TERRACOTTA).concrete(MAGENTA_CONCRETE).concretePowder(MAGENTA_CONCRETE_POWDER).stainedGlass(MAGENTA_STAINED_GLASS).stainedGlassPane(MAGENTA_STAINED_GLASS_PANE).candle(MAGENTA_CANDLE).candleCake(MAGENTA_CANDLE_CAKE).silkbulb(MAGENTA_SILKBULB).bed(MAGENTA_BED).shulkerBox(MAGENTA_SHULKER_BOX).banner(MAGENTA_BANNER).wallBanner(MAGENTA_WALL_BANNER).glazedTerracotta(MAGENTA_GLAZED_TERRACOTTA).build();
        PINK = ColorfulBlockSet.Builder.create(Items.PINK_DYE).wool(PINK_WOOL).carpet(PINK_CARPET).terracotta(PINK_TERRACOTTA).concrete(PINK_CONCRETE).concretePowder(PINK_CONCRETE_POWDER).stainedGlass(PINK_STAINED_GLASS).stainedGlassPane(PINK_STAINED_GLASS_PANE).candle(PINK_CANDLE).candleCake(PINK_CANDLE_CAKE).silkbulb(PINK_SILKBULB).bed(PINK_BED).shulkerBox(PINK_SHULKER_BOX).banner(PINK_BANNER).wallBanner(PINK_WALL_BANNER).glazedTerracotta(PINK_GLAZED_TERRACOTTA).build();

        ImmutableBiMap.Builder oxidizationBuilder = ImmutableBiMap.builder().put(VABlocks.CLIMBING_ROPE_ANCHOR, VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR).put(VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR).put(VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR);
        CLIMBING_ROPE_OXIDIZATION_INCREASES = Suppliers.memoize( () -> oxidizationBuilder.build());
        CLIMBING_ROPE_OXIDIZATION_DECREASES = Suppliers.memoize( () -> CLIMBING_ROPE_OXIDIZATION_INCREASES.get().inverse() );
        ImmutableBiMap.Builder waxedBuilder = ImmutableBiMap.builder().put(VABlocks.CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_CLIMBING_ROPE_ANCHOR).put(VABlocks.EXPOSED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_EXPOSED_CLIMBING_ROPE_ANCHOR).put(VABlocks.WEATHERED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_WEATHERED_CLIMBING_ROPE_ANCHOR).put(VABlocks.OXIDIZED_CLIMBING_ROPE_ANCHOR, VABlocks.WAXED_OXIDIZED_CLIMBING_ROPE_ANCHOR);
        UNWAXED_TO_WAXED_CLIMBING_ROPES = Suppliers.memoize( () -> waxedBuilder.build());
        WAXED_TO_UNWAXED_CLIMBING_ROPES = Suppliers.memoize( () -> UNWAXED_TO_WAXED_CLIMBING_ROPES.get().inverse() );
    }

    private static BlockFamily.Builder register(Block baseBlock) {
        return BlockFamilies.register(baseBlock);
    }

    public static void init(){}
}
