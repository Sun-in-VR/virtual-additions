package com.github.suninvr.virtualadditions.registry;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;

public class VABlockFamilies {

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
    }

    private static BlockFamily.Builder register(Block baseBlock) {
        return BlockFamilies.register(baseBlock);
    }

    public static void init(){}
}
