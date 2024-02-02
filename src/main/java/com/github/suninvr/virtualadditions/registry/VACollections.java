package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.registry.collection.ColorfulBlockSet;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Items;

import static com.github.suninvr.virtualadditions.registry.VABlocks.*;
import static net.minecraft.block.Blocks.*;

public class VACollections {

    public static final BlockFamily CUT_STEEL;
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

    public static final ColorfulBlockSet WHITE;
    public static final ColorfulBlockSet LIGHT_GRAY;
    public static final ColorfulBlockSet GRAY;
    public static final ColorfulBlockSet BLACK;
    public static final ColorfulBlockSet BROWN;
    public static final ColorfulBlockSet MAROON;
    public static final ColorfulBlockSet RED;
    public static final ColorfulBlockSet ORANGE;
    public static final ColorfulBlockSet TAN;
    public static final ColorfulBlockSet YELLOW;
    public static final ColorfulBlockSet CHARTREUSE;
    public static final ColorfulBlockSet LIME;
    public static final ColorfulBlockSet GREEN;
    public static final ColorfulBlockSet COLD_GREEN;
    public static final ColorfulBlockSet CYAN;
    public static final ColorfulBlockSet LIGHT_BLUE;
    public static final ColorfulBlockSet BLUE;
    public static final ColorfulBlockSet INDIGO;
    public static final ColorfulBlockSet PURPLE;
    public static final ColorfulBlockSet PLUM;
    public static final ColorfulBlockSet MAGENTA;
    public static final ColorfulBlockSet PINK;

    static {
        CUT_STEEL = register(VABlocks.CUT_STEEL).stairs(VABlocks.CUT_STEEL_STAIRS).slab(VABlocks.CUT_STEEL_SLAB).customFence(VABlocks.STEEL_FENCE).build();
        COBBLED_HORNFELS = register(VABlocks.COBBLED_HORNFELS).stairs(VABlocks.COBBLED_HORNFELS_STAIRS).slab(VABlocks.COBBLED_HORNFELS_SLAB).wall(VABlocks.COBBLED_HORNFELS_WALL).build();
        POLISHED_HORNFELS = register(VABlocks.POLISHED_HORNFELS).stairs(VABlocks.POLISHED_HORNFELS_STAIRS).slab(VABlocks.POLISHED_HORNFELS_SLAB).build();
        HORNFELS_TILES = register(VABlocks.HORNFELS_TILES).stairs(VABlocks.HORNFELS_TILE_STAIRS).slab(VABlocks.HORNFELS_TILE_SLAB).cracked(VABlocks.CRACKED_HORNFELS_TILES).chiseled(VABlocks.CHISELED_HORNFELS).build();
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
        
        WHITE = ColorfulBlockSet.Builder.create(Items.WHITE_DYE).wool(WHITE_WOOL).carpet(WHITE_CARPET).terracotta(WHITE_TERRACOTTA).concrete(WHITE_CONCRETE).concretePowder(WHITE_CONCRETE_POWDER).stainedGlass(WHITE_STAINED_GLASS).stainedGlassPane(WHITE_STAINED_GLASS_PANE).candle(WHITE_CANDLE).candleCake(WHITE_CANDLE_CAKE).silkbulb(WHITE_SILKBULB).build();
        LIGHT_GRAY = ColorfulBlockSet.Builder.create(Items.LIGHT_GRAY_DYE).wool(LIGHT_GRAY_WOOL).carpet(LIGHT_GRAY_CARPET).terracotta(LIGHT_GRAY_TERRACOTTA).concrete(LIGHT_GRAY_CONCRETE).concretePowder(LIGHT_GRAY_CONCRETE_POWDER).stainedGlass(LIGHT_GRAY_STAINED_GLASS).stainedGlassPane(LIGHT_GRAY_STAINED_GLASS_PANE).candle(LIGHT_GRAY_CANDLE).candleCake(LIGHT_GRAY_CANDLE_CAKE).silkbulb(LIGHT_GRAY_SILKBULB).build();
        GRAY = ColorfulBlockSet.Builder.create(Items.GRAY_DYE).wool(GRAY_WOOL).carpet(GRAY_CARPET).terracotta(GRAY_TERRACOTTA).concrete(GRAY_CONCRETE).concretePowder(GRAY_CONCRETE_POWDER).stainedGlass(GRAY_STAINED_GLASS).stainedGlassPane(GRAY_STAINED_GLASS_PANE).candle(GRAY_CANDLE).candleCake(GRAY_CANDLE_CAKE).silkbulb(GRAY_SILKBULB).build();
        BLACK = ColorfulBlockSet.Builder.create(Items.BLACK_DYE).wool(BLACK_WOOL).carpet(BLACK_CARPET).terracotta(BLACK_TERRACOTTA).concrete(BLACK_CONCRETE).concretePowder(BLACK_CONCRETE_POWDER).stainedGlass(BLACK_STAINED_GLASS).stainedGlassPane(BLACK_STAINED_GLASS_PANE).candle(BLACK_CANDLE).candleCake(BLACK_CANDLE_CAKE).silkbulb(BLACK_SILKBULB).build();
        BROWN = ColorfulBlockSet.Builder.create(Items.BROWN_DYE).wool(BROWN_WOOL).carpet(BROWN_CARPET).terracotta(BROWN_TERRACOTTA).concrete(BROWN_CONCRETE).concretePowder(BROWN_CONCRETE_POWDER).stainedGlass(BROWN_STAINED_GLASS).stainedGlassPane(BROWN_STAINED_GLASS_PANE).candle(BROWN_CANDLE).candleCake(BROWN_CANDLE_CAKE).silkbulb(BROWN_SILKBULB).build();
        MAROON = ColorfulBlockSet.Builder.create(VAItems.MAROON_DYE).wool(MAROON_WOOL).carpet(MAROON_CARPET).terracotta(MAROON_TERRACOTTA).concrete(MAROON_CONCRETE).concretePowder(MAROON_CONCRETE_POWDER).stainedGlass(MAROON_STAINED_GLASS).stainedGlassPane(MAROON_STAINED_GLASS_PANE).candle(MAROON_CANDLE).candleCake(MAROON_CANDLE_CAKE).silkbulb(MAROON_SILKBULB).build();
        RED = ColorfulBlockSet.Builder.create(Items.RED_DYE).wool(RED_WOOL).carpet(RED_CARPET).terracotta(RED_TERRACOTTA).concrete(RED_CONCRETE).concretePowder(RED_CONCRETE_POWDER).stainedGlass(RED_STAINED_GLASS).stainedGlassPane(RED_STAINED_GLASS_PANE).candle(RED_CANDLE).candleCake(RED_CANDLE_CAKE).silkbulb(RED_SILKBULB).build();
        ORANGE = ColorfulBlockSet.Builder.create(Items.ORANGE_DYE).wool(ORANGE_WOOL).carpet(ORANGE_CARPET).terracotta(ORANGE_TERRACOTTA).concrete(ORANGE_CONCRETE).concretePowder(ORANGE_CONCRETE_POWDER).stainedGlass(ORANGE_STAINED_GLASS).stainedGlassPane(ORANGE_STAINED_GLASS_PANE).candle(ORANGE_CANDLE).candleCake(ORANGE_CANDLE_CAKE).silkbulb(ORANGE_SILKBULB).build();
        TAN = ColorfulBlockSet.Builder.create(VAItems.TAN_DYE).wool(TAN_WOOL).carpet(TAN_CARPET).terracotta(TAN_TERRACOTTA).concrete(TAN_CONCRETE).concretePowder(TAN_CONCRETE_POWDER).stainedGlass(TAN_STAINED_GLASS).stainedGlassPane(TAN_STAINED_GLASS_PANE).candle(TAN_CANDLE).candleCake(TAN_CANDLE_CAKE).silkbulb(TAN_SILKBULB).build();
        YELLOW = ColorfulBlockSet.Builder.create(Items.YELLOW_DYE).wool(YELLOW_WOOL).carpet(YELLOW_CARPET).terracotta(YELLOW_TERRACOTTA).concrete(YELLOW_CONCRETE).concretePowder(YELLOW_CONCRETE_POWDER).stainedGlass(YELLOW_STAINED_GLASS).stainedGlassPane(YELLOW_STAINED_GLASS_PANE).candle(YELLOW_CANDLE).candleCake(YELLOW_CANDLE_CAKE).silkbulb(YELLOW_SILKBULB).build();
        CHARTREUSE = ColorfulBlockSet.Builder.create(VAItems.CHARTREUSE_DYE).wool(CHARTREUSE_WOOL).carpet(CHARTREUSE_CARPET).terracotta(CHARTREUSE_TERRACOTTA).concrete(CHARTREUSE_CONCRETE).concretePowder(CHARTREUSE_CONCRETE_POWDER).stainedGlass(CHARTREUSE_STAINED_GLASS).stainedGlassPane(CHARTREUSE_STAINED_GLASS_PANE).candle(CHARTREUSE_CANDLE).candleCake(CHARTREUSE_CANDLE_CAKE).silkbulb(CHARTREUSE_SILKBULB).build();
        LIME = ColorfulBlockSet.Builder.create(Items.LIME_DYE).wool(LIME_WOOL).carpet(LIME_CARPET).terracotta(LIME_TERRACOTTA).concrete(LIME_CONCRETE).concretePowder(LIME_CONCRETE_POWDER).stainedGlass(LIME_STAINED_GLASS).stainedGlassPane(LIME_STAINED_GLASS_PANE).candle(LIME_CANDLE).candleCake(LIME_CANDLE_CAKE).silkbulb(LIME_SILKBULB).build();
        GREEN = ColorfulBlockSet.Builder.create(Items.GREEN_DYE).wool(GREEN_WOOL).carpet(GREEN_CARPET).terracotta(GREEN_TERRACOTTA).concrete(GREEN_CONCRETE).concretePowder(GREEN_CONCRETE_POWDER).stainedGlass(GREEN_STAINED_GLASS).stainedGlassPane(GREEN_STAINED_GLASS_PANE).candle(GREEN_CANDLE).candleCake(GREEN_CANDLE_CAKE).silkbulb(GREEN_SILKBULB).build();
        COLD_GREEN = ColorfulBlockSet.Builder.create(VAItems.COLD_GREEN_DYE).wool(COLD_GREEN_WOOL).carpet(COLD_GREEN_CARPET).terracotta(COLD_GREEN_TERRACOTTA).concrete(COLD_GREEN_CONCRETE).concretePowder(COLD_GREEN_CONCRETE_POWDER).stainedGlass(COLD_GREEN_STAINED_GLASS).stainedGlassPane(COLD_GREEN_STAINED_GLASS_PANE).candle(COLD_GREEN_CANDLE).candleCake(COLD_GREEN_CANDLE_CAKE).silkbulb(COLD_GREEN_SILKBULB).build();
        CYAN = ColorfulBlockSet.Builder.create(Items.CYAN_DYE).wool(CYAN_WOOL).carpet(CYAN_CARPET).terracotta(CYAN_TERRACOTTA).concrete(CYAN_CONCRETE).concretePowder(CYAN_CONCRETE_POWDER).stainedGlass(CYAN_STAINED_GLASS).stainedGlassPane(CYAN_STAINED_GLASS_PANE).candle(CYAN_CANDLE).candleCake(CYAN_CANDLE_CAKE).silkbulb(CYAN_SILKBULB).build();
        LIGHT_BLUE = ColorfulBlockSet.Builder.create(Items.LIGHT_BLUE_DYE).wool(LIGHT_BLUE_WOOL).carpet(LIGHT_BLUE_CARPET).terracotta(LIGHT_BLUE_TERRACOTTA).concrete(LIGHT_BLUE_CONCRETE).concretePowder(LIGHT_BLUE_CONCRETE_POWDER).stainedGlass(LIGHT_BLUE_STAINED_GLASS).stainedGlassPane(LIGHT_BLUE_STAINED_GLASS_PANE).candle(LIGHT_BLUE_CANDLE).candleCake(LIGHT_BLUE_CANDLE_CAKE).silkbulb(LIGHT_BLUE_SILKBULB).build();
        BLUE = ColorfulBlockSet.Builder.create(Items.BLUE_DYE).wool(BLUE_WOOL).carpet(BLUE_CARPET).terracotta(BLUE_TERRACOTTA).concrete(BLUE_CONCRETE).concretePowder(BLUE_CONCRETE_POWDER).stainedGlass(BLUE_STAINED_GLASS).stainedGlassPane(BLUE_STAINED_GLASS_PANE).candle(BLUE_CANDLE).candleCake(BLUE_CANDLE_CAKE).silkbulb(BLUE_SILKBULB).build();
        INDIGO = ColorfulBlockSet.Builder.create(VAItems.INDIGO_DYE).wool(INDIGO_WOOL).carpet(INDIGO_CARPET).terracotta(INDIGO_TERRACOTTA).concrete(INDIGO_CONCRETE).concretePowder(INDIGO_CONCRETE_POWDER).stainedGlass(INDIGO_STAINED_GLASS).stainedGlassPane(INDIGO_STAINED_GLASS_PANE).candle(INDIGO_CANDLE).candleCake(INDIGO_CANDLE_CAKE).silkbulb(INDIGO_SILKBULB).build();
        PURPLE = ColorfulBlockSet.Builder.create(Items.PURPLE_DYE).wool(PURPLE_WOOL).carpet(PURPLE_CARPET).terracotta(PURPLE_TERRACOTTA).concrete(PURPLE_CONCRETE).concretePowder(PURPLE_CONCRETE_POWDER).stainedGlass(PURPLE_STAINED_GLASS).stainedGlassPane(PURPLE_STAINED_GLASS_PANE).candle(PURPLE_CANDLE).candleCake(PURPLE_CANDLE_CAKE).silkbulb(PURPLE_SILKBULB).build();
        PLUM = ColorfulBlockSet.Builder.create(VAItems.PLUM_DYE).wool(PLUM_WOOL).carpet(PLUM_CARPET).terracotta(PLUM_TERRACOTTA).concrete(PLUM_CONCRETE).concretePowder(PLUM_CONCRETE_POWDER).stainedGlass(PLUM_STAINED_GLASS).stainedGlassPane(PLUM_STAINED_GLASS_PANE).candle(PLUM_CANDLE).candleCake(PLUM_CANDLE_CAKE).silkbulb(PLUM_SILKBULB).build();
        MAGENTA = ColorfulBlockSet.Builder.create(Items.MAGENTA_DYE).wool(MAGENTA_WOOL).carpet(MAGENTA_CARPET).terracotta(MAGENTA_TERRACOTTA).concrete(MAGENTA_CONCRETE).concretePowder(MAGENTA_CONCRETE_POWDER).stainedGlass(MAGENTA_STAINED_GLASS).stainedGlassPane(MAGENTA_STAINED_GLASS_PANE).candle(MAGENTA_CANDLE).candleCake(MAGENTA_CANDLE_CAKE).silkbulb(MAGENTA_SILKBULB).build();
        PINK = ColorfulBlockSet.Builder.create(Items.PINK_DYE).wool(PINK_WOOL).carpet(PINK_CARPET).terracotta(PINK_TERRACOTTA).concrete(PINK_CONCRETE).concretePowder(PINK_CONCRETE_POWDER).stainedGlass(PINK_STAINED_GLASS).stainedGlassPane(PINK_STAINED_GLASS_PANE).candle(PINK_CANDLE).candleCake(PINK_CANDLE_CAKE).silkbulb(PINK_SILKBULB).build();
    }

    private static BlockFamily.Builder register(Block baseBlock) {
        return BlockFamilies.register(baseBlock);
    }

    public static void init(){}
}
